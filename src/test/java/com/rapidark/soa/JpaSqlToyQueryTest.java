package com.rapidark.soa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.rapidark.soa.entity.StaffEntity;
import com.rapidark.soa.repository.StaffRepository;

/**
 * @author Darkness
 * @date 2020年10月25日 下午3:23:49
 * @version V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SqlToyApplication.class)
public class JpaSqlToyQueryTest {

	@Autowired
	private StaffRepository repository;

	@Test
	public void test() {
		StaffEntity u = repository.findById("s01").get();
		Assert.assertEquals("成功的测试用例", "张三", u.getName());
	}
	
	@Test
	public void test2() {
		List<Map<String, Object>> datas = repository.queryListMapByNameAndAge("张", 25);
		System.out.println(JSON.toJSONString(datas));
		Assert.assertEquals("成功的测试用例", 3, datas.size());
	}
	
	@Test
	public void test3() {
		List<StaffEntity> datas = repository.queryListEntityByNameAndAge("张", 25);
		System.out.println(JSON.toJSONString(datas));
		Assert.assertEquals("成功的测试用例", 3, datas.size());
	}
	
	@Test
	public void test4() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Map<String, Object>> datas = repository.queryPageMapByNameAndAge("张", 25, pageable);
		System.out.println(JSON.toJSONString(datas));
		Assert.assertEquals("成功的测试用例", 3, datas.getTotalElements());
		Assert.assertEquals("成功的测试用例", 2, datas.getContent().size());
	}
	
	@Test
	public void test5() {
		Pageable pageable = PageRequest.of(1, 2);
		Page<StaffEntity> datas = repository.queryPageEntityByNameAndAge("张", 25, pageable);
		System.out.println(JSON.toJSONString(datas));
		Assert.assertEquals("成功的测试用例", 1, datas.getContent().size());
	}
	
	@Test
	public void test6() {
		Pageable pageable = PageRequest.of(1, 2);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "张");
		params.put("age", 25);
		
		Page<StaffEntity> datas = repository.queryPageEntityByMap(params, pageable);
		System.out.println(JSON.toJSONString(datas));
		Assert.assertEquals("成功的测试用例", 1, datas.getContent().size());
	}
	
}
