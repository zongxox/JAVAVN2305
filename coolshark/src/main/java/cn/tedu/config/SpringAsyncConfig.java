package cn.tedu.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

//異步線程:如果對列滿了,線程滿了,怎麼拒絕呢? 答:創建配置類
@Slf4j//日誌
@Configuration//指明當前類是一個配置類, 就是來替代之前Spring的配置文件
//添加完配置文件屬性後要再加上此@ConfigurationProperties註解,上面報錯不用理他
//參數是:右邊配置文件裡面的縮進路徑()
@ConfigurationProperties("spring.async.task")
@Setter//要記得用SET註解才能取到配置文件的設定的值
public class SpringAsyncConfig implements AsyncConfigurer{

    //異步線程池配置屬性,跟配置文件一樣
    private int corePoolSize = 5;
    private int maxPoolSize = 100;
    private int keepAliveSeconds = 60;
    private int queueCapacity= 128;
    private String threadNamePrefix = "tast===>";

    //實現AsyncConfigurer方法
    @Override
    public Executor getAsyncExecutor() {
        //對於@Async註解默認會基於 ThreadPoolTaskExecutor 對象獲取获取工作線程，然後調用由
        //@Async描述的方法，讓方法運行於另一个工作線
        //可以把這個理解為線程池,再用executor線程池幫她做配置
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);

        //自訂義拒絕處理策略,executor打點調用setRejectedExecutionHandler拒絕處理策略方法
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                log.error("對列已滿並且已無線程可用");
            }
        });
        executor.initialize();//initialize初始化
        return executor;
    }

    //當執行有異常時
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                log.error("任務執行時出現了{}",ex.getMessage());
            }
        };
    }
}
