# rapidark-data-jpa-sqltoy
sqltoy 集成到 jpa

# jpa Repository
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

# 使用示例

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