package cn.tedu.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

//文件上傳
@RestController
public class UploadController {
    //存儲圖片路徑
    private String dirPath = "D:/file";

    //MultipartFile是SpringBoot框架提供的,用來接收上傳圖片文件對象
    @PostMapping("/upload")
    public String upload(MultipartFile picFile) throws IOException {

        //picFile.getOriginalFilename方法用於獲取得到原始的文件名,再進行修改文件名
        String filename = picFile.getOriginalFilename();

        //得到後綴名,b.jpg點之後的jpg
        String suffix = filename.substring(filename.lastIndexOf("."));

        //修改文件名,自動修改文件名
        filename = UUID.randomUUID()+suffix;
        System.out.println(filename);

        //得到文件的完整路徑
        File dirFile = new File(dirPath);

        //判斷文件夾存不存在,exists方法存在的意思,不存在的話創建文件夾
        //mkdirs方法,創建多級文件夾
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }

        //將文件夾和文件名拼接
        String filePath = dirPath + "/" + filename;

        //保存文件到此路徑中,transferTo方法,保存作用
        picFile.transferTo(new File(filePath));

        //返回文件名,刪除的時候需要使用此文件名
        return filename;
    }

    //刪除圖片文件
    @DeleteMapping("remove")
    public void remove(String name){
        String filePath = dirPath + "/" +name;
        File file = new File(filePath);
        //判斷存不存在
        if (file.exists()){
            file.delete();//刪除圖片文件
        }
    }

}
