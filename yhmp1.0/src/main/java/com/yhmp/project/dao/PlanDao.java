package com.yhmp.project.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yhmp.project.entity.ProjectPlan;
/**
 * 项目计划dao对应mapper
 * @author liang
 *
 */
public interface PlanDao {

	Integer addPrjectPlan(List<ProjectPlan> list);
	Integer deletePrjectPlan(ProjectPlan projectPlan);
	List<ProjectPlan> selectPrjectPlan(ProjectPlan projectPlan);
}
