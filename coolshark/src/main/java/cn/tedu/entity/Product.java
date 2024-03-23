package cn.tedu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
//@ApiModel(value = "描述這是什麼類",description = "用於描述商品信息的類")
//@ApiModel用於再類上面添加註解，表示對類進行說明，用於參數用實體類接收
@ApiModel(value = "商品類",description = "用於描述商品信息的類")
@Data
public class Product {
    //@ApiModelProperty在實體類的屬性上面添加註解,value描述這個Integer id屬性作用,就是商品id,position排序用
    @ApiModelProperty(value = "商品id",position = 1)
    private Integer id;

    //商品標題
    @ApiModelProperty(value = "商品標題",position = 2)
    private String title;

    //商品url
    @ApiModelProperty(value = "商品圖片",position = 3)
    private String url;

    //商品價格
    @ApiModelProperty(value = "商品價格",position = 4)
    private Double price;

    //商品原價
    @ApiModelProperty(value = "商品原價",position = 5)
    private Double oldPrice;

    //商品瀏覽量
    @ApiModelProperty(value = "商品瀏覽量",position = 6)
    private Integer viewCount;

    //商品銷量
    @ApiModelProperty(value = "商品銷量",position = 7)
    private Integer saleCount;

    //商品發布時間
    @ApiModelProperty(value = "商品發布時間",position = 8)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")//時間格式化 GMT+8 格林威治時間
    private Date created;

    //商品分類id
    @ApiModelProperty(value = "商品分類",position = 9)
    private Integer categoryId;


}
