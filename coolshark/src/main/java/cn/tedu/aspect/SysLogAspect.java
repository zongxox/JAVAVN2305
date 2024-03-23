package cn.tedu.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
//創建切面類對象

//@Aspect 註解描述的類型為一個切面類型(AOP 中的横切面類型),這樣的切面類型中通常會定義兩部分
//内容：
//1切入點:切入擴展功能的點(例如業務對象中的一個或多個方法)
//2通知：在切點對應的方法執行時，要織入的擴展功能。
@Aspect
@Slf4j //日誌
@Component//把類交給SpringBoot容器管理
public class SysLogAspect {

       //@Pointcut 注解一般用于描述方法，在方法上定義切入點。
       //bean(productController) 這就是一個切入點的表達式
       //只要去執行productController當中的任何一個方法,都會去給你添加額外功能
        @Pointcut("bean(productController))")
        public void logPointCut(){}//這個方法內部不寫任何內容


        //通知,在通知方法中寫額外增強代碼
        //@Around("")再類上添加註解
        //要去添加額外功能,給誰添加呢?就是上面的@Pointcut("bean(productController))")
        //只要執行上面方法就會執行切入點表達式,所以要把方法名logPointCut()寫入@Around()參數中
        //ProceedingJoinPoint是連接點對象,需要被攔截到的方法,其實就會在這裡面存儲那麼多的方法所有方法信息
        @Around("logPointCut()")
        public Object around(ProceedingJoinPoint jp) throws Throwable {
            //添加額外代碼
            //紀錄方法執行時的開始時間,就是獲取當前的執行時間
            long t1 = System.currentTimeMillis();//.currentTimeMillis方法系統目前時間
            try{
                //jp是連接點,調用目標方法
                //只要執行這個方法,那這個當前方法調用的目標方法,就是你當前所執行的增刪改查
                Object result = jp.proceed();//調用本切面中其他通知或下一個切面的通知或目標方法

                //紀錄方法執行結束的時間以及總長
                long t2 = System.currentTimeMillis();
                log.info("method execute time{}",(t2-t1));
                return result;
            } catch (Throwable e) {
                log.info("error is {}",e.getMessage());
                throw e;
            }
        }

}
