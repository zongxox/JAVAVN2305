package cn.tedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//表示此類為Controller層,並創建對象,交給spring管理
@Controller
public class HelloController {
    //如果再類上面添加註解@RequestMapping("a")
    //那訪問路徑會變成訪問頁面路徑:localhost:8080/a/hello

    //訪問頁面路徑:localhost:8080/hello
    //方法方式@RequestMapping測試
    @RequestMapping("hello")//定義網址路徑,訪問頁面路徑:localhost:8080/hello
    public String doHelloUI(){
        return "hello";  //hello為頁面的名字
    }

    @RequestMapping("doSaHello")
    //@ResponseBody註解:告訴SpringMVC框架返回值不是view(頁面html),而是數據
    //不加上此註解會默認去尋找templates資料夾下面的html頁面
    @ResponseBody
    public String doSaHello(){
        return "hello SpringMVC!!";
    }

}
