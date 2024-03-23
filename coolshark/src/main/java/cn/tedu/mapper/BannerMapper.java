package cn.tedu.mapper;

import cn.tedu.entity.Banner;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BannerMapper {
    //添加輪播圖數據
    @Insert("INSERT INTO banner VALUES (null,#{url})")
    void insert(Banner banner);

    //查詢所有輪播圖
    @Select("SELECT * FROM banner")
    List<Banner> list();



    ////D盤的圖片,得到刪除倫播圖的圖片名
    @Select("SELECT url FROM banner WHERE id = #{id}")
    String selectUrlById(int id);

    //刪除數據庫圖片
    @Delete("DELETE FROM banner WHERE id = #{id}")
    void deleteById(int id);


}
