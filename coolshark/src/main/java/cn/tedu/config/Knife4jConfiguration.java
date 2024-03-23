package cn.tedu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration //指明當前類是一個配置類, 就是來替代之前Spring的配置文件
@EnableSwagger2WebMvc //表示此類文 Knife4j 類
public class Knife4jConfiguration {
    //此方法創建Api應用
    @Bean //創建對象後保存到SpringBoot容器當中
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)//告訴Docketbean我們正在使用Swagger規範的版本2,DocumentationType是文檔類型
        .apiInfo(apiInfo()) //創建接口文檔的具體信息,就是把再下面的apiInfo信息,用方法封裝到.apiInfo裡面
        .select()//創建控制器,控制那些接口被加入文檔
        //指定此包下的接口被加入文檔(把cn.tedu.controller路徑的接口加到文檔裡面這是個重點!!!!!)
        .apis(RequestHandlerSelectors.basePackage("cn.tedu.controller"))
        .paths(PathSelectors.any())//允許匹配所有的路徑
        .build();//生成或建造的意思createRestApi
    }
    //此方法作用就是創建API的基本信息(這些基本信息會展示在文檔的基本頁面當中)
    public ApiInfo apiInfo(){
        //創建一個ApiInfoBuilder的意思,Builder就是創建或建造的意思
        return new ApiInfoBuilder()
                .title("接口的文檔標題") //文檔標題
                .description("文檔內容描述") //文檔描述
                .termsOfServiceUrl("http://www/xxx.com") //服務條款url
                .contact(new Contact("zong", "http://baobao.com", "baobao@gmail.com"))//聯繫人信息
                .version("1.0") //版本
                .build(); //生成或建造的意思ApiInfo
    }
}
