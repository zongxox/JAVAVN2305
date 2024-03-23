package cn.tedu.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Aspect 註解描述的類型為一個切面類型(AOP 中的横切面類型),這樣的切面類型中通常會定義兩部分
//内容：
//1切入點:切入擴展功能的點(例如業務對象中的一個或多個方法)
//2通知：在切點對應的方法執行時，要織入的擴展功能。
//定義兩個通知,添加兩個額外功能
//1.查詢出來的數據去天加緩存
//2.另外一個就是要去清空緩存,什麼時候會去清空緩存?緩存數據跟數據庫數據不同時
//@Order(2)
@Aspect
@Component//把類交給SpringBoot容器管理
public class CacheAspect {
    //緩存
    private Map<Object,Object> cache = new ConcurrentHashMap<>();

    //添加緩存表達式
    @Pointcut("@annotation(cn.tedu.annotation.RequiredCache)")
    public void doCache(){}

    //添加清空緩存表達式
    @Pointcut("@annotation(cn.tedu.annotation.ClearCache)")
    public void doClear(){}


    //清空緩存用doAfterReturning
    //注意,這裡選用返回通知,因為只有業務代碼執行成功才能執行此通知
    @AfterReturning("doClear()")
    public void doAfterReturning() {
        cache.clear();//清空.clear緩存cache
    }

    //添加緩存,環繞通知
    //環繞通知ProceedingJoinPoint專有的參數
    @Around("doCache()")
    public Object daAround(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("緩存切面");
        System.out.println("Get data from Cache");//從快取中獲取數據
        Object result = cache.get("dataKey");
        if (result != null) return result;
        result = jp.proceed();//執行目標方法
        System.out.println("Put data to Cache");//將資料放入快取
        cache.put("dataKey", result);
        return result;
    }
}
