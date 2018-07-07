package com.yhmp.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 登陆控制器
 * @author liang
 *
 */
@Controller
public class IndexController {

	@RequestMapping("/indexUI.oo")
	public String indexUI(@RequestParam(value="date") String date ,@RequestParam(value="username") String username ,@RequestParam(value="id") Integer id){
		System.out.println("indexUI");
	 	//两小时内不用登陆
		if((System.currentTimeMillis() - Long.valueOf(date)) <= 7200000) {
			System.out.println("正常");
			System.out.println("username="+username+"id="+id);
			return "index";
		}else {
			System.out.println("异常");
			return "login";
		}
		//返回的字符串会对应一个/WEB-INF/pages/index.jsp页面
	}

}
