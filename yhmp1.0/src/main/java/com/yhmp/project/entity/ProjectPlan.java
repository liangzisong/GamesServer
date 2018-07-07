package com.yhmp.project.entity;

import java.io.Serializable;
import java.util.Arrays;
/**
 * 项目计划实体类
 * @author liang
 *
 */
public class ProjectPlan implements Serializable {

	private static final long serialVersionUID = -6849897108068531382L;
	/** 主键id */
	private Integer id;
	/**	项目名称	*/
	private String declareProject;
	/**	负责人姓名	*/
	private String responsibilityName;
	/** 项目阶段计划 */
	private String periodicPlan;
	/** 各阶段 */
	private Integer projectPlan;
	/** 计划进度 */
	private Integer planSchedule;
	/** 计划内容 */
	private String projectContent;
	/** 完成时间 */
	private String finishTime;
	/** 项目成员 */
	private String personLiable;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDeclareProject() {
		return declareProject;
	}
	public void setDeclareProject(String declareProject) {
		this.declareProject = declareProject;
	}
	public String getResponsibilityName() {
		return responsibilityName;
	}
	public void setResponsibilityName(String responsibilityName) {
		this.responsibilityName = responsibilityName;
	}
	public String getPeriodicPlan() {
		return periodicPlan;
	}
	public void setPeriodicPlan(String periodicPlan) {
		this.periodicPlan = periodicPlan;
	}
	
	public Integer getProjectPlan() {
		return projectPlan;
	}
	public void setProjectPlan(Integer projectPlan) {
		this.projectPlan = projectPlan;
	}
	public Integer getPlanSchedule() {
		return planSchedule;
	}
	public void setPlanSchedule(Integer planSchedule) {
		this.planSchedule = planSchedule;
	}
	public String getProjectContent() {
		return projectContent;
	}
	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getPersonLiable() {
		return personLiable;
	}
	public void setPersonLiable(String personLiable) {
		this.personLiable = personLiable;
	}
	@Override
	public String toString() {
		return "ProjectPlan [id=" + id + ", declareProject=" + declareProject + ", responsibilityName="
				+ responsibilityName + ", periodicPlan=" + periodicPlan + ", projectPlan=" + projectPlan
				+ ", planSchedule=" + planSchedule + ", projectContent=" + projectContent + ", finishTime=" + finishTime
				+ ", personLiable=" + personLiable + "]";
	}
	
	
}
