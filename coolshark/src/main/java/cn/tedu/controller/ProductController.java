package cn.tedu.controller;

import cn.tedu.annotation.ClearCache;
import cn.tedu.annotation.RequiredCache;
import cn.tedu.annotation.RequiredLog;
import cn.tedu.entity.Product;
import cn.tedu.mapper.ProductMapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("product")
//@Api：是添加在控制器類上的注解，通過此註解的tags屬性可以修改原本顯示控制器類名稱的位置的文本,
//通常建議在配置的tags值上添加序號，例如：“1.管理員模塊”、“2.商品模塊”，則框架會根據值進行排序
@Api(tags = "3.商品處裡類")//Api文檔顯示在分類標題
@ApiSupport(author = "zong")//指定作者
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    //新增一條商品模塊紀錄
    //@ApiOperation是添加在控制器類中方法上的註解，用於配置此方法處理的請求在API文檔中,顯示的文本名稱
    //Api文檔顯示在分類中的發布商品
    @ApiOperation("發布商品")
    //@ApiOperationSupport是添加在控制器類中方法上的註解，用於配置此方法排序,排第7的意思
    //Api文檔顯示在分類中的發布商品
    @ApiOperationSupport(order = 7)
    @ClearCache//自定義接口註解,清空緩存
    @PostMapping("insert")
    //@ApiParam("商品")只能在方法屬性上添加,是用在接口文檔中用於指定屬性的信息
    public void insert(@RequestBody @ApiParam("商品") Product product) {
        product.setCreated(new Date());
        productMapper.insert(product);
    }

    //查詢前端頁面中所有商品列表
    @Transactional(readOnly = true)//指讀,什麼情況會用到指讀?只有查詢的時候才會用到turn指讀,增刪改都是fales
    @RequiredLog(operation = "查詢首頁商品")//添加紀錄用戶行為日誌註解
    @RequiredCache//自定義接口註解,添加緩存
    @ApiOperation("查詢所有商品")
    @ApiOperationSupport(order = 1)
    @GetMapping("list/index")
    public List<Product> doSelectIndex() {
        System.out.println(Thread.currentThread().getName()+"-->ProductController.doSelectIndex");
        return productMapper.selectIndex();
    }

    //查詢後端頁面中所有商品列表榜信息
    @ApiOperation("查詢後台商品")
    @ApiOperationSupport(order = 6)
    @GetMapping("list/admin")
    public List<Product> doSelectAdmin() {
        return productMapper.selectAdmin();
    }


    //查詢排行前六條,只需要查詢標題和銷量
    //substring(0,3)擷取標題年三個字
    @ApiOperation("查询排行榜商品")
    @ApiOperationSupport(order = 2)
    @GetMapping("list/top")
    public List<Product> doSelectTop() {
        List<Product> list = productMapper.selectTop();
        for (Product p : list) {
            if (p.getTitle().length() > 3) {
                String title = p.getTitle().substring(0, 3) + "...";
                p.setTitle(title);
            }
        }
        return list;
    }


     //基於關鍵字查詢某個商品
     @ApiOperation("基於關鍵字查詢商品")
     @ApiOperationSupport(order = 4)
     @GetMapping("selectByWd/{keyWord}")
     public List<Product> doSselectByWd(@PathVariable String keyWord) {
         return productMapper.selectByWd(keyWord);
    }



    //通過id查詢去查詢某個商品,就是點擊圖片進入到商品詳情頁面然後
    //查看商品詳情信息
    @ApiOperation("基於id查詢商品")
    @ApiOperationSupport(order = 3)
    @GetMapping("select/{id}")
    public Product doSelectById(@PathVariable Integer id){
        productMapper.updateViewCount(id);
        return productMapper.selectById(id);
    }

    //分類id查詢相關商品
    @ApiOperation("基於分類查詢商品")
    @ApiOperationSupport(order = 5)
    @GetMapping("selectByCid/{cid}")
    public List<Product> doSelectByCid(@PathVariable Integer cid){
        return productMapper.selectByCid(cid);
    }





    //刪除
    //添加註解@Transactional就代表開啟事務
    @Transactional(
            timeout = 30,//默認是-1表示沒有超時,設定值的時候超過值的時間並且沒有執行完,則回滾
            readOnly = false,//指讀,什麼情況會用到指讀?只有查詢的時候才會用到turn指讀,增刪改都是fales
            isolation = Isolation.READ_COMMITTED,//事務隔離級別
            rollbackFor = Throwable.class,//rollbackFor是回滾,Throwable.class最大的一個錯誤,只要出現問題,都會回滾
            propagation = Propagation.REQUIRED//事務的傳播特性
    )
    @ApiOperation("删除商品")
    @ApiOperationSupport(order = 8)
    @ApiImplicitParam(name = "id", value = "商品id", example = "1",
            required = true, dataType = "int")
    @DeleteMapping("delete/{id}")
    public void deleteById(@PathVariable Integer id){
        //通過id查詢到商品的圖片名
       String url = productMapper.selectUrlById(id);

        //圖片的完整路徑以及刪除圖片
        String filePath = "d:/file/"+url;
        new File(filePath).delete();


        //刪除數據庫中圖片名
        productMapper.deleteById(id);
    }

}
