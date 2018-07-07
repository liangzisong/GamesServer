package com.yhmp.project.service;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import com.yhmp.project.entity.ProjectPlan;
/**
 * 项目计划接口由具体实现类来实现业务
 * @author Administrator
 *
 */
public interface PlanServer {

	String addPrjectPlan(ProjectPlan projectPlan);
	String deletePrjectPlan(ProjectPlan projectPlan);
	List<ProjectPlan> selectPrjectPlan(ProjectPlan projectPlan);
}
