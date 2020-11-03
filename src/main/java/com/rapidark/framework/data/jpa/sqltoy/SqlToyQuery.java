package com.rapidark.framework.data.jpa.sqltoy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.annotation.QueryAnnotation;

/**
 * @author Darkness
 * @date 2020年10月25日 下午3:56:44
 * @version V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@QueryAnnotation
@Documented
public @interface SqlToyQuery {
	
	String value() default "";
}
