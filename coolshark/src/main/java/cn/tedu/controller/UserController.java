package cn.tedu.controller;

import cn.tedu.entity.User;
import cn.tedu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    //完成登錄,用戶名存不存在,比較密碼是否正確
    //如果用戶不存在返回2,登入成功返回1,密碼錯誤返回3
    //在參數列表中聲明HttpSession,即可得到當前用戶端所對應的session
    @PostMapping("login")
    public int login(@RequestBody User user, HttpSession session){
        //查詢用戶名
        User u = userMapper.selectByUsername(user.getUsername());

        if (u == null) return 2;//判斷用戶存不存在,2代表不存在

        //比較用戶密碼是否等於用戶密碼.equals對比字符串
        if (u.getPassword().equals(user.getPassword())){
            //把登入信息保存到會話對象中session就是會話對象
            //setAttribute("user",u);保存到session當中,就能夠把登入狀態保存住了
            //參數k:變量名  參數v:從數據庫取出來的當中用戶信息
            session.setAttribute("user",u);

            return 1;//1表示密碼正確
        }
        return 3;//3表示密碼不正確
    }

    //session.getAttribute("user");查看到底有沒有保存成功
    //強轉User返回值
    @GetMapping("currentUser")
    public User currentUser(HttpSession session){
        //獲取登入成功時保存在session中的用戶信息
        //通過k來取值
        return (User) session.getAttribute("user");
    }

    //將登入成功時保存的用戶信息移除
    @GetMapping("logout")
    public void logout(HttpSession session){
        //通過k來移除
        session.removeAttribute("user");
    }




}
