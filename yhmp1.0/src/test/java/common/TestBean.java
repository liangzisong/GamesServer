package common;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBean {
	ClassPathXmlApplicationContext ctx;
	@Before
	public void init(){
		System.out.println("-1");
		ctx=new ClassPathXmlApplicationContext(
		"spring-pool.xml",
		"spring-mybatis.xml",
		"spring-mvc.xml");
		System.out.println("0");
	}
	@After
	public void destory(){
		ctx.close();
	}
}
