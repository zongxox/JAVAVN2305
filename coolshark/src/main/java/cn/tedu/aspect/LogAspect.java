package cn.tedu.aspect;

import cn.tedu.annotation.RequiredLog;
import cn.tedu.entity.Log;
import cn.tedu.entity.User;
import cn.tedu.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

//@Aspect 註解描述的類型為一個切面類型(AOP 中的横切面類型),這樣的切面類型中通常會定義兩部分
//内容：
//1切入點:切入擴展功能的點(例如業務對象中的一個或多個方法)
//2通知：在切點對應的方法執行時，要織入的擴展功能。
//定義兩個通知,添加兩個額外功能
//1.查詢出來的數據去天加緩存
//2.另外一個就是要去清空緩存,什麼時候會去清空緩存?緩存數據跟數據庫數據不同時

@Order(1)
@Slf4j//日誌
@Aspect
@Component//把類交給SpringBoot容器管理
public class LogAspect {

    //@annotation表達式應用於方法級別，實現細力度的切入點表達式定義
    @Around("@annotation(cn.tedu.annotation.RequiredLog)")
    public Object doLogAround(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("日誌切面");
        int status = 1;//初始狀態
        String error = null;//初始錯誤信息
        long time = 0L;//耗時時間
        long t1 = System.currentTimeMillis();//開始時間
        try {
            Object result = jp.proceed();//調用目標方法
            long t2 = System.currentTimeMillis();
            time = t2 - t1;//得到耗時結果
            return result;
        } catch (Throwable e) {
            long t3 = System.currentTimeMillis();
            time = t3 - t1;//出現問題的耗時時間
            status = 0;//0表示出錯了
            error = e.getMessage();//獲取錯誤信息
            throw e;//拋出異常
        }finally {//finally不論上面有沒有執行,都會執行finally
                  //jp是切入點,time時間,status狀態,error錯誤,傳到數據庫
            saveLog(jp,time,status,error);
        }
    }


    private void saveLog(ProceedingJoinPoint jp, long time, int status, String error) throws NoSuchMethodException, JsonProcessingException {
        //反射
        //1.獲取用戶的行為日誌
        //1.1獲取ip地址,這個方法就是存儲了用戶的訪問信息,ip之類的,RequestContextHolder.getRequestAttributes();
        //通過這個對象就能夠去獲取到每個用戶請求的基本信息
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        //拿到用戶的請求
        HttpServletRequest request = requestAttributes.getRequest();

        //通過用戶的請求request打點調用getRemoteAddr();方法獲取ip地址
        String ip = request.getRemoteAddr();

        //1.2獲取登入用戶
        String username = null;
        //通過請求request打點調用getSession().getAttribute("user");獲取用戶信息
        User user = (User) request.getSession().getAttribute("user");

        //判斷用戶不等於空,不等於空的話,通過user打點調用user.getUsername();方法獲取用戶名
        if (user != null){
            username = user.getUsername();
        }

        //1.3獲取操作名,獲取方法跟註解@interface RequiredLog
        //1.3.獲取類的字節碼對象
        //jp.getTarget()就是獲取到類(要執行的類),就是添加@RequiredLog註解後執行這個註解裡面的方法
        //.getClass();就是這個類的字節碼,就是添加@RequiredLog註解後執行這個註解裡面的字節碼
        Class<?> targetCls = jp.getTarget().getClass();

        //1.3.2獲取方法簽名對象
        //jp.getSignature();或取到的是簽名
        //這個是方法簽名對象
        MethodSignature ms = (MethodSignature) jp.getSignature();

        //1.3.3獲取目標方法對象,這裡會用到反射
        //通過字節碼對象打點調用getDeclaredMethod(,);,取得聲明方法
        //.getDeclaredMethod不管是私有的還是什麼方法,只要是方法,都能去獲取到
        //參數1,通過簽名對象打點調用,獲取方法名
        //參數2,通過簽名對象打點調用,獲取參數列表類型
        //這樣要被執行的方法就拿到了
        Method targetMethod = targetCls.getDeclaredMethod(ms.getName(), ms.getParameterTypes());



        //1.3.4獲取方法上的註解
        //獲取接口註解@interface RequiredLog
        RequiredLog requiredLog = targetMethod.getAnnotation(RequiredLog.class);

        //1.3.5獲取操作名
        //獲取接口註解的方法,操作名
        String operation = requiredLog.operation();

        //1.4獲取目標方法名(類全名+方法名),要獲取的是數據庫的log表字段method的數據包名+類名+方法名
        //targetCls.getName();獲取類全名,targetMethod.getName獲取方法名
        String method = targetCls.getName() + "." + targetMethod.getName();

        //1.5獲取參數
        //writeValueAsString(jp.getArgs()) 把這個值重新輸出,輸出成String類型
        String params = new ObjectMapper().writeValueAsString(jp.getArgs());





        //2.封裝進用戶行為日誌當中
        Log userLog = new Log();//用戶對象
        userLog.setIp(ip);
        userLog.setUsername(username);
        userLog.setCreatedTime(new Date());
        userLog.setOperation(operation);
        userLog.setMethod(method);
        userLog.setParams(params);
        userLog.setStatus(status);
        userLog.setError(error);
        userLog.setTime(time);

        //紀錄用戶行為日誌
        log.info("user log is {}",userLog);
        //保存到數據庫當中
        logService.insert(userLog);


    }
    @Autowired
    private LogService logService;


}
