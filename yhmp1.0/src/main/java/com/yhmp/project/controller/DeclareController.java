package com.yhmp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhmp.common.web.JsonResult;
import com.yhmp.project.entity.PorjectDeclare;
import com.yhmp.project.service.DeclareServer;
/**
 * 项目申报交接控制器
 * @author liang
 *
 */
@Controller
@RequestMapping("/declare/")
public class DeclareController {

	@Autowired
	private DeclareServer declareServer; 
	@RequestMapping("listUI")
	public String listUI() {
		System.out.println("进入declare.listUI");
		return "project/Declare";
	}
	
	@ResponseBody
	@RequestMapping("disposeSubmit")
	public JsonResult disposeSubmit(PorjectDeclare porjectDeclare) {
		if(declareServer.insertPorjectDeclare(porjectDeclare)==1) {
			return new JsonResult("操作成功");
		}
			return new JsonResult("操作失败");
	}
} 