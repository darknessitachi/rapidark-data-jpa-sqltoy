package com.rapidark.soa.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rapidark.framework.data.jpa.sqltoy.SqlToyQuery;
import com.rapidark.soa.entity.StaffEntity;

/**
 * 
 * @author Darkness
 * @date 2020年10月25日 下午3:22:46
 * @version V1.0
 */
@Repository
public interface StaffRepository extends JpaRepository<StaffEntity,String> {

	@SqlToyQuery("queryByNameAndAge")
	List<Map<String, Object>> queryListMapByNameAndAge(@Param("name") String name, @Param("age") int age);
	
	@SqlToyQuery("queryByNameAndAge")
	List<StaffEntity> queryListEntityByNameAndAge(@Param("name") String name, @Param("age") int age);
	
	// 默认名称为：类名简称 + '_' + 方法名，此处为：StaffRepository_queryPageMapByNameAndAge
	@SqlToyQuery
	Page<Map<String, Object>> queryPageMapByNameAndAge(@Param("name") String name, @Param("age") int age, Pageable pageable);
	
	@SqlToyQuery("queryByNameAndAge")
	Page<StaffEntity> queryPageEntityByNameAndAge(@Param("name") String name, @Param("age") int age, Pageable pageable);
	
	@SqlToyQuery("queryByNameAndAge")
	Page<StaffEntity> queryPageEntityByMap(Map<String, Object> params, Pageable pageable);
	
}