package com.yhmp.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhmp.common.web.JsonResult;
import com.yhmp.system.entity.User;
import com.yhmp.system.service.UserServer;
/**
 * 用户权限控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/authorityManage/")
public class AuthorityManageController {
	
	@Autowired
	private UserServer userServer;

	@ResponseBody
	@RequestMapping("saveAuthority")
	public JsonResult saveAuthority(User user) {
		System.out.println(user);
		String state = userServer.editUser(user);
		System.out.println("保存权限");
		return new JsonResult(state);
	}
}
