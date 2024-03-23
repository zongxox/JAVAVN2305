package cn.tedu.controller;

import cn.tedu.dao.UserDao;
import cn.tedu.entity.JsonResult;
import cn.tedu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/user/register")
    public JsonResult doRegister(@RequestBody User user){
        user.setStatus(1);
        user.setCreatedTime(new Date());
        user.setModifiedTime(new Date());
        userDao.insert(user);
        return new JsonResult("註冊成功");
    }

    //查詢註冊時間比createdTime晚的用戶信息
    @GetMapping("user/list/{createdTime}")
    public JsonResult doList (@PathVariable String createdTime){
        List<User> list = userDao.list(createdTime);
            return new JsonResult(list);
    }

    //更新用戶信息
    @PutMapping("/user/update")
    public JsonResult update(@RequestBody User user){
        user.setModifiedTime(new Date());
        userDao.update(user);
        return new JsonResult("新增成功");
    }
}
