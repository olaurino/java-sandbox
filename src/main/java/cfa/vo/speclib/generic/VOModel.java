package cfa.vo.speclib.generic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by olaurino on 9/9/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface VOModel {
    String utype();
    String UCD() default "";
    String unit() default "";
    Class contentType();
    int[] shape() default {-1};
    //TODO Add default value
}
