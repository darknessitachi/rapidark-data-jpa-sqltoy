package com.rapidark.framework.data.jpa.sqltoy;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.springframework.aop.AfterAdvice;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * @author Darkness
 * @date 2020年10月25日 下午4:01:08
 * @version V1.0
 */
public class SqlToyRepositoryFactory extends JpaRepositoryFactory {

	private SqlToyLazyDao sqlToyLazyDao;
	private final EntityManager entityManager;

    private final PersistenceProvider extractor;

	public SqlToyRepositoryFactory(SqlToyLazyDao sqlToyLazyDao, EntityManager entityManager) {
		super(entityManager);
		this.sqlToyLazyDao = sqlToyLazyDao;
		this.entityManager = entityManager;
		this.extractor = PersistenceProvider.fromEntityManager(entityManager);
	}

	@Override
    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(@Nullable QueryLookupStrategy.Key key,
    		QueryMethodEvaluationContextProvider evaluationContextProvider) {
        return Optional.of(
        		TemplateQueryLookupStrategy.create(sqlToyLazyDao,
        				entityManager, key, extractor, evaluationContextProvider));
    }
	
}
