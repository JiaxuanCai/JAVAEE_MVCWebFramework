package mymvc.annotation;

//import org.springframework.web.bind.mymvc.annotation.RequestMethod;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestMapping {
    String value() default "";
    String method() default "GET";
}
