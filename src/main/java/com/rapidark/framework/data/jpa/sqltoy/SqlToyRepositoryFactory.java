package com.rapidark.framework.data.jpa.sqltoy;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.lang.Nullable;

/**
 * @author Darkness
 * @date 2020年10月25日 下午4:01:08
 * @version V1.0
 */
public class SqlToyRepositoryFactory extends JpaRepositoryFactory {

	protected SqlToyLazyDao sqlToyLazyDao;
	protected final EntityManager entityManager;

	protected final PersistenceProvider extractor;

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
        		SqlToyQueryLookupStrategy.create(sqlToyLazyDao,
        				entityManager, key, extractor, evaluationContextProvider));
    }
	
}
