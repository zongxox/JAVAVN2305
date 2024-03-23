package cn.tedu.dao;

import cn.tedu.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserDao {


    //添加新的用戶信息
    @Insert("INSERT INTO tb_users VALUES (null,#{username}, #{nickname}, #{password}, #{mobile}, #{status},#{createdTime},#{modifiedTime})")
    void insert(User user);

    //查詢註冊時間比createdTime晚的用戶信息
    @Select("SELECT * FROM tb_users WHERE created_time > #{createdTime}")
    List<User> list(String createdTime);


    //更新用戶信息
    @Update("UPDATE tb_users SET username=#{username},password=#{password},nickname=#{nickname},mobile=#{mobile},status=#{status},modified_time=#{modifiedTime} WHERE id = #{id}")
    void update (User user);

}
