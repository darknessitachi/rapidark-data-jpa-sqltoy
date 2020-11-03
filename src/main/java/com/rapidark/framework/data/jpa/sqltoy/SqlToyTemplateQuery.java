package com.rapidark.framework.data.jpa.sqltoy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.sagacity.sqltoy.model.PaginationModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.JpaParameters;
import org.springframework.data.jpa.repository.query.JpaParameters.JpaParameter;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
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
	public Object execute(Object[] values) {
		System.out.println(Arrays.toString(values));
		JpaParameters parameters = getQueryMethod().getParameters();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("name", "张");
		paramsMap.put("age", 25);
		
		int 
		for (JpaParameter jpaParameter : parameters) {
			Class<?> paramType = jpaParameter.getType();
			if (paramType == Pageable.class) {
				continue;
			}
			paramsMap.put(jpaParameter.getName().get(), value)
		}
		if (parameters.hasPageableParameter()) {
            Pageable pageable = (Pageable) (values[parameters.getPageableIndex()]);
            if (pageable != null) {
//                query.setFirstResult((int) pageable.getOffset());
//                query.setMaxResults(pageable.getPageSize());
            	PaginationModel pageModel = new PaginationModel();
        		pageModel.setPageNo(1);
        		pageModel.setPageSize(1);
        		
        		
        		PaginationModel<HashMap> staffInfo = sqlToyLazyDao.findPageBySql(pageModel, "ds_Staff_queryStaffInfo", paramsMap, HashMap.class);
        		System.out.println(JSON.toJSONString(staffInfo));
            }
        } else {
        	List<Map<String, Object>> staffInfo = sqlToyLazyDao.findBySql("ds_Staff_queryStaffInfo", paramsMap, HashMap.class);
        	return staffInfo;
        }
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
	public JpaQueryMethod getQueryMethod() {
		return queryMethod;
	}


}