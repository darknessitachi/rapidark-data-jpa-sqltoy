package com.rapidark.soa.repository;

import java.util.List;

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

	@SqlToyQuery
	List<StaffEntity> queryByNameAndAge(@Param("name") String name, @Param("age") int age);
	
}