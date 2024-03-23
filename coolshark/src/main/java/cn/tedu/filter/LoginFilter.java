package cn.tedu.filter;

import cn.tedu.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//filterName = "" 註解的名字
//urlPatterns{""}作用需要對什麼路徑進行攔截
///product/insert對這個路徑進行攔截,所以當我們去做添加操作的時候,就會去驗證我們是否是登入的狀態
//如果登入了就可以做,如果沒有登入就會跳轉登入頁面
@WebFilter(filterName = "loginFilter",urlPatterns = {""})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("過濾器執行了");

        //1.獲取HttpServletRequest跟HttpServletResponse對象
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //2.問題什麼情況下才是登入成功的狀態?答:Session當中是否有User的值,有值代表已經登入,沒有值代表沒有登入
        //獲取服務端的HttpSession對象
        HttpSession session = request.getSession();

        //3.從Session對象中獲取登入對象
        User user = (User) session.getAttribute("user");

        //4.判斷用戶是不是空的
        if (user == null){//如果用戶為空,說明用戶未登入
            //response.sendRedirect("login.html");
            //這個是重定向,就是跳轉到另外一個頁面當中,跳轉到哪個頁面?就是登入頁面,有頁面的話就用上面的重定向
            //沒有的話直接打印未登入返回就可以了
            System.out.println("未登入");
            return;
        }
        //5.假如用戶登入了,則執行下一步操作,就是允許訪問資源
        //filterChain就是過濾器的鏈,多重過濾
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
