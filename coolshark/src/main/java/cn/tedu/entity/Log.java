package cn.tedu.entity;

//基於此對象封裝用戶行為日誌?
//誰在什麼時間執行了什麼操作,訪問了什麼,傳遞了什麼參數,訪問時長是多少,狀態是什麼?

import lombok.Data;

import java.util.Date;

@Data
public class Log {
    private Long id;
    private String ip;
    private String username;
    private Date createdTime;
    private String operation;
    private String method;
    private String params;
    private Long time;
    private Integer status;
    private String error;
}