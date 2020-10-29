package com.rapidark.framework.data.jpa.sqltoy;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;

import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.query.DefaultJpaQueryMethodFactory;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.data.jpa.repository.query.JpaQueryLookupStrategy;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.data.repository.query.RepositoryQuery;

/**
 * <p>模板查询策略</p>
 * @author Darkness
 * @date 2020年10月25日 下午1:46:19
 * @version V1.0
 */
public class TemplateQueryLookupStrategy implements QueryLookupStrategy {

	private SqlToyLazyDao sqlToyLazyDao;
    private final EntityManager entityManager;

    private QueryLookupStrategy jpaQueryLookupStrategy;

    private QueryExtractor extractor;

    public TemplateQueryLookupStrategy(SqlToyLazyDao sqlToyLazyDao, EntityManager entityManager, Key key, QueryExtractor extractor,
    		QueryMethodEvaluationContextProvider evaluationContextProvider) {
    	this.sqlToyLazyDao = sqlToyLazyDao;
        this.jpaQueryLookupStrategy = JpaQueryLookupStrategy.create(
        		entityManager, new DefaultJpaQueryMethodFactory(extractor), key, evaluationContextProvider, EscapeCharacter.DEFAULT);
        this.extractor = extractor;
        this.entityManager = entityManager;
    }

    public static QueryLookupStrategy create(SqlToyLazyDao sqlToyLazyDao, EntityManager entityManager, Key key, QueryExtractor extractor,
    		QueryMethodEvaluationContextProvider evaluationContextProvider) {
        return new TemplateQueryLookupStrategy(sqlToyLazyDao, entityManager, key, extractor, evaluationContextProvider);
    }

    @Override
    public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, ProjectionFactory factory,
                                        NamedQueries namedQueries) {
        if (method.getAnnotation(SqlToyQuery.class) != null) {
        	return new SqlToyTemplateQuery(sqlToyLazyDao,
            		new DefaultJpaQueryMethodFactory(extractor).build(method, metadata, factory), entityManager);
        } else {
        	return jpaQueryLookupStrategy.resolveQuery(method, metadata, factory, namedQueries);
        }
    }
}
