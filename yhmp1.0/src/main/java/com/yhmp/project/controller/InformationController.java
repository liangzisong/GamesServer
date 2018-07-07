package com.yhmp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhmp.common.web.JsonResult;
import com.yhmp.project.entity.PorjectDeclare;
import com.yhmp.project.service.InformationServer;
import com.yhmp.system.entity.User;
/**
 * 项目信息控制器
 * @author liang
 *
 */
@Controller
@RequestMapping("/information/")
public class InformationController {

	@Autowired
	private InformationServer informationServer;
	
	
	@RequestMapping("listUI")
	public String listUI() {
		System.out.println("进入项目信息");
		return "project/Information";
	}
	@ResponseBody
	@RequestMapping("selectInformation")
	public JsonResult selectInformation(PorjectDeclare porjectDeclare) {
		System.out.println("InformationController.selectInformation()===="+porjectDeclare);
		//单个项目详细信息
		if(informationServer.selectProjectInformation(porjectDeclare) instanceof PorjectDeclare) {
			System.out.println("查询出单个项目");
			PorjectDeclare pd = informationServer.selectProjectInformation(porjectDeclare);
			System.out.println("pd="+pd);
			return new JsonResult(pd);
		}
		//多个项目
		List<PorjectDeclare> list = informationServer.selectProjectInformation(porjectDeclare);
		System.out.println("查询出多个项目");
		return new JsonResult(list);
	}
	
	@ResponseBody
	@RequestMapping("editProject")
	public JsonResult editProject(PorjectDeclare porjectDeclare) {
		System.out.println("StffManageController.addPorject()==========================================");
		System.out.println("Porject="+porjectDeclare);
		String state = informationServer.editProject(porjectDeclare);
		System.out.println("state==="+state);
		return new JsonResult(state);
	}
	@ResponseBody
	@RequestMapping("deleteProject")
	public JsonResult deleteProject(PorjectDeclare porjectDeclare) {
		System.out.println("Porject="+porjectDeclare);
		String state = informationServer.deleteProject(porjectDeclare);
		System.out.println("state==="+state);
		return new JsonResult(state);
	}
}
