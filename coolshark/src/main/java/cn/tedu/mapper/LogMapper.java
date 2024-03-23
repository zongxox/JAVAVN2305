package cn.tedu.mapper;

import cn.tedu.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    @Insert("insert into log(ip,username,created_time,operation,method,params,time,status,error) values(#{ip},#{username},#{createdTime},#{operation},#{method},#{params},#{time},#{status},#{error})")
    int insert(Log log);
}
