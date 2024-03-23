package cn.tedu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component//把類交給SpringBoot容器管理
//@Aspect 註解描述的類型為一個切面類型(AOP 中的横切面類型),這樣的切面類型中通常會定義兩部分
//内容：
//1切入點:切入擴展功能的點(例如業務對象中的一個或多個方法)
//2通知：在切點對應的方法執行時，要織入的擴展功能。
@Aspect
public class SysTimeAspect {
    //定義切入點
    @Pointcut("bean(productController)")
    public void doTime(){}

    //前置通知
    //JoinPoint,切入點對象,換句話說就是被攔截到的方法,連接點
    @Before("doTime()")
    public void doBefore(JoinPoint jp){
        System.out.println("doBefore()");
    }


    //後置通知
    @After("doTime()")
    public void doAfter(){
        System.out.println("doAfter()");
    }

    //返回通知,核心業務代碼正常結束時執行
    @AfterReturning("doTime()")
    public void doAfterReturning(){
        System.out.println("doAfterReturning()");
    }

    //異常通知,核心業務代碼出現異常時執行
    @AfterThrowing("doTime()")
    public void doAfterThrowing(){
        System.out.println("doAfterThrowing()");
    }

    //環繞通知,專屬於環繞通知的對象ProceedingJoinPoint
    @Around("doTime()")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("doAround.before");//調用前置通知
        Object obj = jp.proceed();
        System.out.println("doAround.after");//調用後置通知
        return obj;
    }
}
