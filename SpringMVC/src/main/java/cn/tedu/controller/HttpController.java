package cn.tedu.controller;

import cn.tedu.dao.HttpDao;
import cn.tedu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class HttpController {
    @Autowired
    private HttpDao dao;

    //value表示訪問路徑 localhost:8080/getUser
    //method是請求方式,這就是一個GET請求RequestMethod.GET
    @RequestMapping(value = "getUserAll" ,method = RequestMethod.GET)
    public String getUserAll(){
        List<User> list = dao.getUserAll();
        return list.toString();
    }

    @RequestMapping(value = "getUserById",method =RequestMethod.GET )
    public User getUser(int id){
        User user = dao.getUser(id);
        return user;
    }

    //RequestMethod.POST,POST請求方式,方法參數要加上
    //@RequestBody註解,如果不加上,POST就沒辦法再請求實體裡面取到值
    //@RequestBody post從post中從請求實體中取值
    @RequestMapping(value = "postUser",method = RequestMethod.POST)
    public String poseUser(@RequestBody User user){
        dao.postUser(user);
        return "新增成功";
    }

    //修改要用RequestMethod.PUT
    @RequestMapping(value = "putUpdate",method = RequestMethod.PUT)
    public String putUpdate(@RequestBody User user){
        dao.putUpdate(user);
        return "修改成功";
    }

    //刪除用戶信息
    //刪除要用RequestMethod.DELETE
    @RequestMapping(value = "deleteUser",method = RequestMethod.DELETE)
    public String deleteUser(int id){
        dao.deleteUser(id);
        return "刪除成功";
    }

}
