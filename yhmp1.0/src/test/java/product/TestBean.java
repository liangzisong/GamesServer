package product;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBean {
	ClassPathXmlApplicationContext ctx;
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext(
		"spring-pool.xml",
		"spring-mybatis.xml",
		"spring-mvc.xml");
	}
	@After
	public void destory(){
		ctx.close();
	}
}
