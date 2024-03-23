package cn.tedu.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
//Spring MVC中攔截器
//@author Administrator
public class TimeInterceptor implements HandlerInterceptor {
    //implements HandlerInterceptor<---實現攔截器,並重寫方法
    //preHandle在控制層目標方法執行之前執行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("preHandler()");
        Calendar c = Calendar.getInstance();//獲取java中的日歷對象
        c.set(Calendar.HOUR_OF_DAY, 6);//設置小時,6點
        c.set(Calendar.MINUTE, 0);//設置分鐘
        c.set(Calendar.SECOND, 0);//設置秒
        long start = c.getTimeInMillis();//獲取到開始毫秒值
        c.set(Calendar.HOUR_OF_DAY,11);//11點,6點到11點之間可以訪問,超過這時間就不能訪問
        long end = c.getTimeInMillis();//獲取到結束毫秒值
        long cTime = System.currentTimeMillis();//獲取當前時間
        if(cTime<start||cTime>end)//當前時間cTime小於開始時間start,或者當前時間cTime大於開始時間start
            throw new RuntimeException("不再訪問時間之內");
        return true;
    }
}
