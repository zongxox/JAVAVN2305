package cn.tedu.cache;

//第一步：定義長量信息
public class CacheConstants {

    //默認過期時間(配置類中我使用的時間單位是秒，所以這裡如 3*60 為3分鐘)
    public static final int DEFAULT_EXPIRES = 3 * 60; //默認的過期時間,3*60秒 為3分鐘
    public static final int EXPIRES_5_MIN = 5 * 60; //不是默認的,5*60秒 為5分鐘
    public static final int EXPIRES_10_MIN = 10 * 60;//不是默認的,10*60秒 為10分鐘
    public static final String CACHE1 = "CACHE1";//緩存1
    public static final String CACHE2 = "CACHE2";//緩存2
}
