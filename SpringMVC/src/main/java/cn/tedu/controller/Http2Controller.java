package cn.tedu.controller;

import cn.tedu.dao.HttpDao;
import cn.tedu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Http2Controller {
    @Autowired
    private HttpDao httpDao;

    @GetMapping("user")
    public String getUserAll(){
        List<User> list = httpDao.getUserAll();
        return list.toString();
    }




    //@PathVariable註解:表示獲取請求路經url中參數的值,並要求必須同名
    @GetMapping("user/{id}")
    public User getUser(@PathVariable int id){
        User user = httpDao.getUser(id);
        return user;
    }



    @DeleteMapping("user/{id}")
    public String deleteUser(@PathVariable int id){
        httpDao.deleteUser(id);
        return "刪除成功";
    }



    //@PostMapping("url路徑名")
    //@RequestBody如果不添加這個註解,就會無法在前台接收新增數據(值)
    //新增後會變成數據空值
    @PostMapping("postUser1")
    public String postUser(@RequestBody User user){
        httpDao.postUser(user);
        return "新增成功";
    }


    //@PutMapping("路徑名")
    //@RequestBody如果不添加這個註解,就會無法在前台接收修改數據(值)
    //會顯示修改成功,但實際上並沒有修改
    @PutMapping("putUpdate1")
    public String putUpdate(@RequestBody User user){
        httpDao.putUpdate(user);
        return "修改成功";
    }
}
