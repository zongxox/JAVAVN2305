package cn.tedu.controller;


import cn.tedu.entity.JsonResult;
import org.slf4j.Logger;//導入這兩包
import org.slf4j.LoggerFactory;//導入這兩包
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局異常處理類,註解描述的類為全局異常處理類,啟動時會交給Spring管理
@RestControllerAdvice
public class GlobalExceptionHandler {
    //日誌對象
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //@ExceptionHandler注解描述的方法為異常處理方法,注解中定義的異常類型為方法可以處理的異常類型
    @ExceptionHandler(RuntimeException.class)//參數:定義我們能夠處理的異常,運行時異常
    public JsonResult doHandleRuntimeException(RuntimeException e){//參數:運行時異常,並賦值給變量 e
        e.printStackTrace();//打印異常
        log.error("exception msg is {}",e.getMessage());
        //log.error錯誤日誌,exception msg is {}<--佔位符,獲取異常e.getMessage,並賦予佔位符
        return new JsonResult(e);//返回給JsonResult異常構造器,再去找上面的構造方法,並將狀態碼改為0,並將報錯信息展示
    }//可以定義多個異常處理方法

}
