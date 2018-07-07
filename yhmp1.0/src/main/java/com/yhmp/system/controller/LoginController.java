package com.yhmp.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhmp.common.exception.ServiceException;
import com.yhmp.common.web.JsonResult;
import com.yhmp.system.entity.User;
import com.yhmp.system.service.UserServer;
/**
 * 登陆控制器
 * @author Liang
 *
 */
@Controller
public class LoginController {

	@Autowired
	private UserServer userServer;
	
	@RequestMapping("/login")
	public String login() {
		System.out.println("登录");
		return "login";
	}
	@ResponseBody
	@RequestMapping("/onLogin")
	public JsonResult onLogin(User user) {
		System.out.println("用户输入userName="+user.getUsername());
		System.out.println("用户输入password="+user.getPassword());
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		User u = userServer.selectUser(newUser);
		//拿到第一条，也是唯一一条
		//User users = list.get(0);
		if(u == null) {
			System.out.println("User等于null");
			return new JsonResult(new ServiceException("用户名或密码错误！"));
		}
		return new JsonResult(u);

//		}else {
//			System.out.println("User等于null");
//			return new JsonResult(new ServiceException("用户名或密码错误！"));
//		}		
	}
}
