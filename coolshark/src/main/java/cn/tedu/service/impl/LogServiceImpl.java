package cn.tedu.service.impl;

import cn.tedu.mapper.LogMapper;
import cn.tedu.service.LogService;
import cn.tedu.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//Service的實現類
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper mapper;//注入接口對象


    @Async//添加異步註解,開啟多線程,就會同時執行
    @Transactional(propagation = Propagation.REQUIRED)//事務的傳播特性
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insert(Log log) {
        //Thread.currentThread()獲取當前線程,getName()獲取當前線程名字,在拼接字符串
        //這個線程在執行這個LogServiceImpl類的insert方法-->LogServiceImpl.insert
        System.out.println(Thread.currentThread().getName()+"-->LogServiceImpl.insert");
        try {
            Thread.sleep(5000);//Thread.sleep線程停5秒的意思
        } catch (Exception e) {}//模擬耗時5秒操作

        mapper.insert(log);
    }
}
