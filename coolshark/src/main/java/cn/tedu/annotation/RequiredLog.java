package cn.tedu.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@interface 接口註解
//自定義註解,一個特殊的類,所有的註解都默認繼承Annotation接口
@Retention(RetentionPolicy.RUNTIME)//原註解,修飾註解的註解就叫原註解,運行時有效
@Target(ElementType.METHOD)//原註解,修飾註解的註解就叫原註解,這個註解所修是的地方在哪裡?,就是修飾方法
public @interface RequiredLog {
    String operation();
}
