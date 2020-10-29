package com.rapidark.soa;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rapidark.soa.entity.StaffEntity;
import com.rapidark.soa.repository.StaffRepository;

/**
 * @author Darkness
 * @date 2020年10月25日 下午3:23:49
 * @version V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SqlToyApplication.class)
public class SpringbootH2ApplicationTests {

	@Autowired
	private StaffRepository repository;

	@Test
	public void test() {
		StaffEntity u = repository.findById("s01").get();
		Assert.assertEquals("成功的测试用例", "张三", u.getName());
	}
	
	@Test
	public void test2() {
		List<StaffEntity> datas = repository.queryByNameAndAge("张", 25);
		Assert.assertEquals("成功的测试用例", 3, datas.size());
	}

}
