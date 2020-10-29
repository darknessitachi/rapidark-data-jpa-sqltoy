package com.rapidark.soa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.rapidark.framework.data.jpa.sqltoy.SqlToyJpaRepositoryFactoryBean;

/**
 * @author Darkness
 * @date 2020年10月25日 下午3:58:33
 * @version V1.0
 */
@Configuration
@EnableJpaRepositories(
		basePackages = { "com.xdreamaker","com.rapidark" }, 
		repositoryFactoryBeanClass = SqlToyJpaRepositoryFactoryBean.class)
@EntityScan({
	"com.rapidark.soa.entity"
})
public class SoaConfig {

}
