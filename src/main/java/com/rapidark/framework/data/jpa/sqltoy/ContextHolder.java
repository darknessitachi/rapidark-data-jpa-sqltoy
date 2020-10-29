package com.rapidark.framework.data.jpa.sqltoy;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

/**
 * @author Darkness
 * @date 2020年10月25日 下午5:16:33
 * @version V1.0
 */
public class ContextHolder {

	public static ApplicationContext appContext;

	public static <T> Collection<T> getBeansOfType(Class<T> clazz) {
		return appContext.getBeansOfType(clazz).values();
	}

	public static <T> T getBean(Class<T> clazz) {
		return appContext.getBean(clazz);
	}
}
