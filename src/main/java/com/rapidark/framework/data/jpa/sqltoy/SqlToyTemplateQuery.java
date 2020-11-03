package com.rapidark.framework.data.jpa.sqltoy;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.sagacity.sqltoy.model.PaginationModel;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.JpaParameters;
import org.springframework.data.jpa.repository.query.JpaParameters.JpaParameter;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
import org.springframework.data.repository.query.Parameter;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * <p>
 * SqlToy模板查询
 * </p>
 * 
 * @author Darkness
 * @date 2020年10月25日 下午1:41:10
 * @version V1.0
 */
public class SqlToyTemplateQuery implements RepositoryQuery {

	private SqlToyLazyDao sqlToyLazyDao;
	private Method method;
	private JpaQueryMethod queryMethod;
	private EntityManager entityManager;

	public SqlToyTemplateQuery(SqlToyLazyDao sqlToyLazyDao, Method method, JpaQueryMethod queryMethod, EntityManager entityManager) {
		this.sqlToyLazyDao = sqlToyLazyDao;
		this.method = method;
		this.queryMethod = queryMethod;
		this.entityManager = entityManager;
	}

	@Override
	public Object execute(Object[] values) {
		System.out.println(Arrays.toString(values));
		
		String namedQueryName = getNamedQueryName();
		JpaParameters parameters = getQueryMethod().getParameters();
		Map<String, Object> paramsMap = getParams(values);//new HashMap<String, Object>();

		Class<?> objectType = getQueryMethod().getReturnedObjectType();
		Class<?> genericType;
		if (objectType.isAssignableFrom(Map.class)) {
            genericType = objectType;
        } else {
        	ClassTypeInformation<?> ctif = ClassTypeInformation.from(objectType);
            TypeInformation<?> actualType = ctif.getActualType();
            genericType = actualType.getType();
        }
		
        
//		int paramIndex = 0;
//		for (JpaParameter jpaParameter : parameters) {
//			Class<?> paramType = jpaParameter.getType();
//			Object value = values[paramIndex++];
//			if (paramType == Pageable.class) {
//				continue;
//			}
//			Optional<String> paramOptional = jpaParameter.getName();
//			if (paramOptional.isPresent()) {
//				String paramName = jpaParameter.getName().get();
//				paramsMap.put(paramName, value);
//			}
//		}
		if (parameters.hasPageableParameter()) {
			Pageable pageable = (Pageable) (values[parameters.getPageableIndex()]);
			if (pageable != null) {
//                query.setFirstResult((int) pageable.getOffset());
//                query.setMaxResults(pageable.getPageSize());
				PaginationModel<?> result = queryPagedData(namedQueryName, pageable, paramsMap, genericType);
				Page<?> pageResult = new PageImpl<>(result.getRows(), pageable, result.getRecordCount());
				return pageResult;
			}
		}
		
		List<?> result = queryData(namedQueryName, paramsMap, genericType);
		return result;
	}
	
	private Map<String, Object> getParams(Object[] values) {
        JpaParameters parameters = getQueryMethod().getParameters();
        //gen model
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameters.getNumberOfParameters(); i++) {
            Object value = values[i];
            Parameter parameter = parameters.getParameter(i);
            if (value != null && canBindParameter(parameter)) {
                if (!isValidValue(value)) {
                    continue;
                }
                Class<?> clz = value.getClass();
                if (clz.isPrimitive() 
                		|| String.class.isAssignableFrom(clz) 
                		|| Number.class.isAssignableFrom(clz)
                        || clz.isArray() 
                        || Collection.class.isAssignableFrom(clz) 
                        || clz.isEnum()
                        || Date.class.isAssignableFrom(clz) 
                        || java.sql.Date.class.isAssignableFrom(clz)) {
                    params.put(parameter.getName().orElse(null), value);
                } else {
                    params = toParams(value);
                }
            }
        }
        return params;
    }
	
	@SuppressWarnings("unchecked")
    public static Map<String, Object> toParams(Object beanOrMap) {
        Map<String, Object> params;
        if (beanOrMap instanceof Map) {
            params = (Map<String, Object>) beanOrMap;
        } else {
            params = toMap(beanOrMap);
        }
        if (!CollectionUtils.isEmpty(params)) {
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                if (!isValidValue(params.get(key))) {
                    keys.remove();
                }
            }
        }
        return params;
    }
	
	 public static Map<String, Object> toMap(Object bean) {
	        if (bean == null) {
	            return Collections.emptyMap();
	        }
	        try {
	            Map<String, Object> description = new HashMap<String, Object>();
	            if (bean instanceof DynaBean) {
	                DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass().getDynaProperties();
	                for (DynaProperty descriptor : descriptors) {
	                    String name = descriptor.getName();
	                    description.put(name, BeanUtils.getProperty(bean, name));
	                }
	            } else {
	                PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
	                for (PropertyDescriptor descriptor : descriptors) {
	                    String name = descriptor.getName();
	                    if (PropertyUtils.getReadMethod(descriptor) != null) {
	                        description.put(name, PropertyUtils.getNestedProperty(bean, name));
	                    }
	                }
	            }
	            return description;
	        } catch (Exception e) {
	            return Collections.emptyMap();
	        }
	    }

    public static boolean isValidValue(Object object) {
        if (object == null) {
            return false;
        }
        /*if (object instanceof Number && ((Number) object).longValue() == 0) {
            return false;
		}*/
        return !(object instanceof Collection && CollectionUtils.isEmpty((Collection<?>) object));
    }
	
	private boolean canBindParameter(Parameter parameter) {
        return parameter.isBindable();
    }
	
	private <T> List<T> queryData(String namedQueryName, Map<String, Object> paramsMap, Class<T> clazz) {
		List<T> result = sqlToyLazyDao.findBySql(namedQueryName, paramsMap, clazz);
		return result;
	}
	
	private <T> PaginationModel<T> queryPagedData(String namedQueryName, Pageable pageable, Map<String, Object> paramsMap, Class<T> clazz) {
		PaginationModel<T> pageModel = new PaginationModel<>();
		pageModel.setPageNo(pageable.getPageNumber() + 1);
		pageModel.setPageSize(pageable.getPageSize());

		PaginationModel<T> result = sqlToyLazyDao.findPageBySql(pageModel, namedQueryName,
				paramsMap, clazz);
		System.out.println(JSON.toJSONString(result));
		return result;
	}
	
	@Override
	public JpaQueryMethod getQueryMethod() {
		return queryMethod;
	}
	
	public String getNamedQueryName() {
		String annotatedName = getAnnotationValue("value", String.class);
		return StringUtils.hasText(annotatedName) ? annotatedName : getClassMethodName();
	}
	
	private <T> T getAnnotationValue(String attribute, Class<T> type) {
		return getMergedOrDefaultAnnotationValue(attribute, SqlToyQuery.class, type);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> T getMergedOrDefaultAnnotationValue(String attribute, Class annotationType, Class<T> targetType) {

		Annotation annotation = AnnotatedElementUtils.findMergedAnnotation(method, annotationType);
		if (annotation == null) {
			return targetType.cast(AnnotationUtils.getDefaultValue(annotationType, attribute));
		}

		return targetType.cast(AnnotationUtils.getValue(annotation, attribute));
	}
	
	private String getClassMethodName() {
		return method.getDeclaringClass().getSimpleName() + "_" + method.getName();
	}

}