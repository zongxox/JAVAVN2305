package cn.tedu.controller;


import cn.tedu.entity.Emp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


//@ResponseBody註解也可以添加在類上面,但前提是這個類裡面的方法是返回數據
//而不是返回頁面html,否則他會自動尋找html的文件返回頁面路徑

@RestController //@Controller + @ResponseBody = @RestController
public class ParameterController {

    @RequestMapping("param1")
    public String param1(String name) {
        return "姓名為:" + name;
    }
    @RequestMapping("param2")
    public String param2(String name, int age) {
        return "姓名為:" + name + "年齡為:" + age;
    }

    @RequestMapping("param3")
    public String param3(Emp emp) {
        return emp.toString();
    }
    @RequestMapping("param4")
    public String param4(Integer[] ids) {
        return Arrays.toString(ids);
    }

}
