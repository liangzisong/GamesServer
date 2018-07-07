package system;

import java.util.List;

import org.junit.Test;

import com.yhmp.system.entity.User;
import com.yhmp.system.service.UserServer;

public class TestStffManage extends TestBean{

	@Test
	public void testSelectUser() {
		UserServer us = ctx.getBean("userServerImpl",UserServer.class);
		User user = new User();
		user.setUsername("admin");
		user.setPassword("admin");
		List<User> list = us.selectUser(user);
		for(User u : list) {
			System.out.println(u);
		}
	}
}
