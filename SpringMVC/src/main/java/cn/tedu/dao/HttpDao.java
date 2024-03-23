package cn.tedu.dao;

import cn.tedu.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HttpDao {

    @Select("SELECT * FROM user")
    List<User> getUserAll();



    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUser(int id);


    @Insert("INSERT INTO user VALUES (null,#{username},#{password},#{nickname})")
    int postUser(User user);


    @Update("UPDATE User SET username=#{username},password=#{password},nickname=#{nickname} WHERE id = #{id}")
    int putUpdate(User user);



    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(int id);



}
