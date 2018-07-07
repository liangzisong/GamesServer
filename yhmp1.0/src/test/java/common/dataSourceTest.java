package common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class dataSourceTest {
	private ClassPathXmlApplicationContext ctx;
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext(
	    "spring-pool.xml","spring-mybatis.xml");
	}
	@Test
	public void DataSource1Test(){
		Object obj=
		ctx.getBean("dataSource");
		//测试obj是否不等于空
		Assert.assertNotEquals(null, obj);
		//当使用Assert测试失败时后续语句不再执行
		System.out.println("obj="+obj);
	}
	@Test
	public void SessionFactoryTest(){
		Object obj=
		ctx.getBean("sqlSessionFactory");
		//测试obj是否不等于空
		Assert.assertNotEquals(null, obj);
		//当使用Assert测试失败时后续语句不再执行
		System.out.println("obj="+obj);
	}
	@After
	public void destory(){
		ctx.close();
	}

}
