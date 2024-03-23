package cn.tedu.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//第三步：定義緩存配置類,配置關於緩存相關信息
//去將我們創建出來的緩存,全部都添加到緩存管理器當中
//那這個時候我們才能夠去調用這些緩存
//你如果說不將這些緩存,不放在緩存管理器當中,就沒辦法去調用
@Configuration//指明當前類是一個配置類, 就是來替代之前Spring的配置文件
@EnableCaching//開啟緩存註解
public class CacheConfig {

    //緩存管理器
    @Bean
    public CacheManager cacheManager(){
        //這個數組是用來裝Caffeine(咖啡因)緩存的
        List<CaffeineCache> list = new ArrayList<>();

        //SpringBoot提供的管理器
        SimpleCacheManager cacheManager = new SimpleCacheManager();


        //Caffeine配置說明：
        //initialCapacity=[integer]: 初始的緩存空間大小
        //maximumSize=[long]: 緩存的最大條數
        //expireAfterWrite=[duration]: 最後一次寫入後經過固定時間過期
        // refreshAfterWrite=[duration]:

        //循環添加枚舉類(CacheEnum類)中的,自訂義的緩存
        //.values()值的意思
        //newBuilder創建的意思
        for (CacheEnum cacheEnum : CacheEnum.values()) {
            list.add(new CaffeineCache(cacheEnum.getName(),
                    Caffeine.newBuilder()//導gitHub的包->import com.github.benmanes.caffeine.cache.Caffeine;
                            .initialCapacity(50)//初始容量
                            .maximumSize(1000)//緩存的最大條數
                            //.expireAfterWrite寫完之後,多久過期
                            //cacheEnum.getExpires()通過枚舉類打點調用獲取時間
                            //寫完之後,5分鐘之後和10分鐘之後,就會過期
                            //TimeUnit.SECONDS 時間單位的意思
                            .expireAfterWrite(cacheEnum.getExpires(), TimeUnit.SECONDS)
                            .build()));
        }
        cacheManager.setCaches(list);
        return cacheManager;
    }
}
