package com.yhmp.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhmp.common.web.JsonResult;
import com.yhmp.system.entity.User;
import com.yhmp.system.service.UserServer;
/**
 * 个人信息控制器
 * @author Liang
 *
 */
@Controller
@RequestMapping("/personal/")
public class PersonalConntroller {

	@Autowired
	private UserServer userServer;
	
	/**
	 * 点击个人信息
	 * @param id 用户id
	 * @return	当前用户
	 */
	@ResponseBody
	@RequestMapping("onPersonal")
	public JsonResult onPersonal(@RequestParam("id") String id) {
		System.out.println("个人信息控制器已走，请求id="+id);
		User user = new User();
		user.setId(Integer.valueOf(id));
		System.out.println("User.Id="+user.getId());
		User u = userServer.selectUser(user);
		System.out.println("登陆的user: "+u);
		return new JsonResult(u);
	}
	/**
	 * 修改账号
	 * @param id 用户id
	 * @param username	用户姓名
	 * @return	修改是否成功
	 */
	@ResponseBody
	@RequestMapping("personal")
	public JsonResult personal(@RequestParam("id") String id,@RequestParam("username") String username) {
		User user = new User();
		user.setId(Integer.valueOf(id));
		user.setUsername(username);
		String state = userServer.updateUser(user);
		return new JsonResult(state);
	}
	/**
	 * 修改密码
	 * @param id	用户id
	 * @param password	原密码
	 * @param newPassword	新密码
	 * @return	修改是否成功
	 */
	@ResponseBody
	@RequestMapping("uppassword")
	public JsonResult uppassword(
			@RequestParam("id") Integer id 
			,@RequestParam("password") String password 
			,@RequestParam("newPassword") String newPassword) {
		User user = new User();
		user.setId(id);
		User u = userServer.selectUser(user);
		if(!u.getPassword().equals(password)) {
			return new JsonResult("原密码错误");
		}
		user.setPassword(newPassword);
		String state = userServer.updateUser(user);
		return new JsonResult(state);
	}
}
