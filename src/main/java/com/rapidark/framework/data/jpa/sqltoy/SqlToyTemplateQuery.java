package com.rapidark.framework.data.jpa.sqltoy;

import java.util.Arrays;
import java.util.LinkedHashMap;

import javax.persistence.EntityManager;

import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.sagacity.sqltoy.model.PaginationModel;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;

import com.alibaba.fastjson.JSON;

/**
 * @author Darkness
 * @date 2020年10月25日 下午4:03:09
 * @version V1.0
 */
/**
 * <p>Freemarker模板查询</p>
 * @author Darkness
 * @date 2020年10月25日 下午1:41:10
 * @version V1.0
 */
public class SqlToyTemplateQuery implements RepositoryQuery {
	
	private SqlToyLazyDao sqlToyLazyDao;
	private JpaQueryMethod queryMethod;
	private EntityManager entityManager;

	public SqlToyTemplateQuery(SqlToyLazyDao sqlToyLazyDao, JpaQueryMethod queryMethod, EntityManager entityManager) {
		this.sqlToyLazyDao = sqlToyLazyDao;
		this.queryMethod = queryMethod;
		this.entityManager = entityManager;
	}

	@Override
	public Object execute(Object[] parameters) {
		System.out.println(Arrays.toString(parameters));
//		PaginationModel pageModel = new PaginationModel();
////		StaffInfoVO staffVO = new StaffInfoVO();
//		// 作为查询条件传参数
////		staffVO.setStaffName("陈");
//		// 使用了分页优化器
//		// 第一次调用:执行count 和 取记录两次查询
//		PaginationModel result = sqlToyLazyDao.findPageBySql(pageModel, "sqltoy_fastPage", );
//		System.err.println(JSON.toJSONString(result));
//		// 第二次调用:过滤条件一致，则不会再次执行count查询
//		//设置为第二页
//		pageModel.setPageNo(2);
//		result = sqlToyLazyDao.findPageBySql(pageModel, "sqltoy_fastPage", staffVO);
		return null;
	}

	@Override
	public QueryMethod getQueryMethod() {
		return queryMethod;
	}


}