package com.yhmp.project.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhmp.project.dao.PlanDao;
import com.yhmp.project.entity.ProjectPlan;
import com.yhmp.project.service.PlanServer;
/**
 * 项目计划server
 * @author liang
 *
 */
@Service
public class PlanServerImpl implements PlanServer {

	@Autowired
	private PlanDao planDao;
	
	@Override
	public String addPrjectPlan(ProjectPlan projectPlan) {
		List<ProjectPlan> list = new ArrayList<ProjectPlan>();
		
		//分计划|
		String[] projects = projectPlan.getPeriodicPlan().split("\\|");
		System.out.println("============s==========");
		System.out.println(Arrays.toString(projects));
		
		for(int i=0;i<projects.length;i++) {
			ProjectPlan newProjectPlan = new ProjectPlan();
			System.out.println("projects[i]="+projects[i]);
			//获取各计划的值
			String[] plan = projects[i].split("\\^");
			System.out.println("========p==========");
			System.out.println("plan.length="+plan.length);
			System.out.println(Arrays.toString(plan));
			//id
			newProjectPlan.setId(projectPlan.getId());
			
			newProjectPlan.setDeclareProject(projectPlan.getDeclareProject());
			newProjectPlan.setResponsibilityName(projectPlan.getResponsibilityName());
			newProjectPlan.setProjectPlan(Integer.valueOf(plan[0]));
			newProjectPlan.setPlanSchedule(Integer.valueOf(plan[1]));
			newProjectPlan.setProjectContent(plan[2]);
			newProjectPlan.setFinishTime(plan[3]);
			newProjectPlan.setPersonLiable(plan[4]);
			list.add(newProjectPlan);
		}
		System.out.println("list.stream().forEach(p -> System.out.println(p));");
		list.stream().forEach(p -> System.out.println(p));
		System.out.println("for(ProjectPlan pp :list) {");
		for(ProjectPlan pp :list) {
			System.out.println(pp);
		}
		Integer i = planDao.addPrjectPlan(list);
		if(i == 1) {
			return "添加成功";
		}
		return "添加失败";
	}

	@Override
	public List<ProjectPlan> selectPrjectPlan(ProjectPlan porjectPlan) {
		List<ProjectPlan> list = planDao.selectPrjectPlan(porjectPlan);
		System.out.println("===========================");
		list.forEach(i ->System.out.println(i));
		return list;
	}

	@Override
	public String deletePrjectPlan(ProjectPlan projectPlan) {
		Integer i =planDao.deletePrjectPlan(projectPlan);
		if(i == 1) {
			return "删除成功";
		}
		return "删除失败";
	}

	
}
