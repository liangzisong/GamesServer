package com.yhmp.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhmp.project.dao.InformationDao;
import com.yhmp.project.entity.PorjectDeclare;
import com.yhmp.project.service.InformationServer;
/**
 * 项目信息server
 * @author liang
 *
 */
@Service
public class InformationServerImpl implements InformationServer {

	@Autowired
	private InformationDao informationDAO;
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T selectProjectInformation(PorjectDeclare porjectDeclare) {
		//informationDAO.selectProjectInformation();
		List<PorjectDeclare> list = informationDAO.selectProjectInformation(porjectDeclare);
		System.out.println("===============");
		list.forEach(i -> System.out.println(i));
		switch (list.size()) {
		//单个项目信息
		case 1:
			System.out.println("查询出一条");
			System.out.println("list.get(0)="+list.get(0));
			return (T) list.get(0);
		//多个项目信息
		default:
			System.out.println("查询出多条");
			return (T) list;
		}
	}

	@Override
	public String editProject(PorjectDeclare porjectDeclare) {
		Integer i = informationDAO.editProject(porjectDeclare);
		if(i == 1) {
			return "编辑成功";
		}
		return "编辑失败";
	}

	@Override
	public String deleteProject(PorjectDeclare porjectDeclare) {
		Integer i = informationDAO.deleteProject(porjectDeclare);
		if(i == 1) {
			return "删除成功";
		}
		return "删除失败";
	}



}
