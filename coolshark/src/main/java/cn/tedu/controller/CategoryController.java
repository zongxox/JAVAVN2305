package cn.tedu.controller;

import cn.tedu.cache.CacheConstants;
import cn.tedu.entity.Category;
import cn.tedu.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryMapper categoryMapper;

    //新增一條商品分類
    @PostMapping("insert")
    //@CacheEvict清空緩存,value屬性為添加到的緩存名稱,allEntries = ture 表示清空所有
    @CacheEvict(value = CacheConstants.CACHE2,allEntries = true)
    public void insert(@RequestBody Category category){
        categoryMapper.insert(category);
    }


    //查詢所有分類
    //@Cacheable添加緩存,value屬性為添加到的緩存名稱,sync為異步線程池同步進行
    @Cacheable(value = CacheConstants.CACHE2,sync = true)
    @GetMapping("selectlist")
    public List<Category> selectList(){
        //第一次查有事正常
        //第二次查沒有的話就代表添加到緩存裡面去
        System.out.println("=====獲取數據=====");
        return categoryMapper.selectList();
    }


    //基於id查詢分類
    @GetMapping("selectbyid/{id}")
    public Category selectById(@PathVariable int id){
        Category category = categoryMapper.selectById(id);
        return category;
    }
    //基於id刪除一條分類
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable int id){
        categoryMapper.deleteById(id);
    }

}
