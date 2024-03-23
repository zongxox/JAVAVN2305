package cn.tedu.mapper;

import cn.tedu.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {


    //新增一條商品分類
    @Insert("INSERT INTO category VALUES (null, #{name})")
    void insert(Category category);

    //查詢所有分類
    @Select("SELECT id,name FROM category")
    List<Category> selectList();

    //基於id查詢分類
    @Select("SELECT id,name FROM category WHERE id = #{id}")
    Category selectById(int id);



    //基於id刪除一條分類
    @Delete("DELETE FROM category WHERE id = #{id}")
    void deleteById(int id);
}
