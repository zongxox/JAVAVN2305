package cn.tedu.mapper;

import cn.tedu.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    //新增一條商品模塊紀錄
    @Insert("INSERT INTO product VALUES(null,#{title},#{url},#{price},#{oldPrice},0,#{saleCount},#{created},#{categoryId})")
    void insert(Product product);

    //查詢前端頁面中所有商品列表,列表字段查詢id,標題,價格,未打折價格,銷量,圖片
    @Select("SELECT id,title,price,old_price,sale_count,url FROM product")
    List<Product> selectIndex();

    //查詢後端頁面中所有商品列表榜信息,查詢id,標題,價格,銷量,圖片
    @Select("SELECT id,title,price,sale_count,url FROM product")
    List<Product> selectAdmin();

    //查詢排行前六條,只需要查詢標題和銷量,LIMIT 取前六條
    @Select("SELECT id,title,sale_count FROM product ORDER BY sale_count desc LIMIT 0,6")
    List<Product> selectTop();


    //基於關鍵字查詢某個商品
    //concat('%',#{keyWord},'%')是mysql字符串的拼接,是搭配於佔位符
    @Select("SELECT id,title,price,old_price,sale_count,url FROM product WHERE title like concat('%',#{keyWord},'%')")
    List<Product> selectByWd(String keyWord);


    //通過id查詢去查詢某個商品,id,標題,圖片,價格,原價,銷量,瀏覽量,發布時間
    @Select("SELECT id,title,url,price,old_price,sale_count,view_count,created FROM product WHERE id = #{id}")
    Product selectById(Integer id);

    //修改瀏覽量view_count + 1瀏覽量+1的意思
    @Update("UPDATE product SET view_count = view_count + 1 WHERE id = #{id}")
    void updateViewCount(Integer id);

    //分類id查詢相關商品
    @Select("SELECT id,title,price,old_price,sale_count,url FROM product WHERE category_id = #{cid}")
    List<Product> selectByCid(Integer cid);

    //先查詢
    @Select("SELECT url FROM product WHERE id = #{id}")
    String selectUrlById(int id);
    //後刪除
    @Delete("DELETE FROM product WHERE id = #{id}")
    void deleteById(Integer id);


}
