package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

//@ServletComponentScan此註解的作用,是在當前類所在的包及子包下掃描過濾器
@ServletComponentScan
@SpringBootApplication
@EnableAsync//在主啟動類添加@EnableAsync這個註解,作用啟動異步,Spring容器啟動時會創建線程池
public class CoolsharkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoolsharkApplication.class, args);
    }

}
