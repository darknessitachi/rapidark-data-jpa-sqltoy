package com.rapidark.framework.data.jpa.sqltoy;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @author Darkness
 * @date 2020年10月25日 下午4:00:29
 * @version V1.0
 */
public class SqlToyJpaRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
		extends JpaRepositoryFactoryBean<R, T, I> implements ApplicationContextAware {

	public SqlToyJpaRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
		SqlToyLazyDao sqlToyLazyDao = ContextHolder.getBean(SqlToyLazyDao.class);
		return new SqlToyRepositoryFactory(sqlToyLazyDao, em);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ContextHolder.appContext = applicationContext;
	}

}