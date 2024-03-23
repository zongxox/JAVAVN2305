package cn.tedu.cache;
//第二步：創建枚舉類(通過枚舉定義一些固定實例)，定義緩存相關信息
public enum CacheEnum {
    //緩存1
    CACHE1(CacheConstants.CACHE1, CacheConstants.EXPIRES_5_MIN),

    //緩存2
    CACHE2(CacheConstants.CACHE2, CacheConstants.EXPIRES_10_MIN);

    //緩存名稱
    private final String name;

    //過期時間
    private final int expires;

    //構造方法
    CacheEnum(String name, int expires) {
        this.name = name;
        this.expires = expires;
    }

    public String getName() {
        return name;
    }

    public int getExpires() {
        return expires;
    }
}