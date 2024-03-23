package cn.tedu.controller;

import cn.tedu.entity.Banner;
import cn.tedu.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
//在類上加上這個註解@RequestMapping("banner")就是定義Get,Post,put,Delete的Mapping的前綴
//就可以不用再方法的註解上添加url的banner前綴路徑名


@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerMapper bannerMapper;

    //添加輪播圖數據
    @PostMapping("insert")
    public void insert(@RequestBody Banner banner){
        bannerMapper.insert(banner);
    }
    //查詢所有輪播圖
    @GetMapping("list")
    public List<Banner> list(){
        return bannerMapper.list();
    }

    //刪除倫播圖
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable int id){
        //D盤的圖片,得到刪除倫播圖的圖片名
        String url = bannerMapper.selectUrlById(id);

        //D盤的路徑,得到文件完整路徑
        String filePath = "D:/file/"+url;

        //刪除D盤file文件夾的圖片
        //UploadController刪除方法
        new File(filePath).delete();

        //刪除數據庫圖片
        bannerMapper.deleteById(id);
    }

}
