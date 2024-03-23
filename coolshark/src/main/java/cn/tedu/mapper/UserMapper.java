package cn.tedu.mapper;

import cn.tedu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {


    //完成登錄,用戶名存不存在,比較密碼是否正確
    @Select("SELECT id,username,password,nickname FROM user WHERE username = #{username}")
    User selectByUsername(String username);




}
