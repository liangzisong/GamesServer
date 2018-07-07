package com.yhmp.project.entity;

import java.io.Serializable;
/**
 * 项目信息实体类
 * @author liang
 *
 */
public class PorjectDeclare implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -149763722978360806L;
	/** 主键id */
	private Integer id;
	/**	项目名称	*/
	private String declareProject;
	/**	项目编号	*/
	private String declareNumber;
	/**	项目标准分	*/
	private String declareFraction;
	/**	项目规模	*/
	private String declareScale;
	/**	项目类型：整理	*/
	private String declareArrange;
	/**	项目类型 ：评估	*/
	private String declareAssess;
	/**	项目类型：测绘	*/
	private String declareSurveying;
	/**	项目类型：规划	*/
	private String declarePlan;
	/**	
	 * 合同签订情况
	 * 	1为已签订   
	 * 	0为未签订
	 */
	private Integer contract;
	/**	单位名称	*/
	private String declareCompany;
	/**	联系人姓名	*/
	private String declareName;
	/**	联系人电话	*/
	private String declarePhone;
	/**	项目完成时间	*/
	private String finishTime;
	/**	
	 * 项目质量要求
	 * 	1为优秀       
	 * 	2为良好       
	 * 	3为合格
	 */
	private Integer quality;
	/**	
	 * 是否需要招投标	
	 * 	1为需要    
	 *  2为不需要     
	 *  3为暂不确定
	 */
	private Integer bid;
	/**	负责人姓名	*/
	private String responsibilityName;
	/**	负责人电话	*/
	private String responsibilityPhone;
	
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
	public String getDeclareNumber() {
		return declareNumber;
	}
	public void setDeclareNumber(String declareNumber) {
		this.declareNumber = declareNumber;
	}
	public String getDeclareFraction() {
		return declareFraction;
	}
	public void setDeclareFraction(String declareFraction) {
		this.declareFraction = declareFraction;
	}
	public String getDeclareScale() {
		return declareScale;
	}
	public void setDeclareScale(String declareScale) {
		this.declareScale = declareScale;
	}
	public String getDeclareArrange() {
		return declareArrange;
	}
	public void setDeclareArrange(String declareArrange) {
		this.declareArrange = declareArrange;
	}
	public String getDeclareAssess() {
		return declareAssess;
	}
	public void setDeclareAssess(String declareAssess) {
		this.declareAssess = declareAssess;
	}
	public String getDeclareSurveying() {
		return declareSurveying;
	}
	public void setDeclareSurveying(String declareSurveying) {
		this.declareSurveying = declareSurveying;
	}
	public String getDeclarePlan() {
		return declarePlan;
	}
	public void setDeclarePlan(String declarePlan) {
		this.declarePlan = declarePlan;
	}
	public Integer getContract() {
		return contract;
	}
	public void setContract(Integer contract) {
		this.contract = contract;
	}
	public String getDeclareCompany() {
		return declareCompany;
	}
	public void setDeclareCompany(String declareCompany) {
		this.declareCompany = declareCompany;
	}
	public String getDeclareName() {
		return declareName;
	}
	public void setDeclareName(String declareName) {
		this.declareName = declareName;
	}
	public String getDeclarePhone() {
		return declarePhone;
	}
	public void setDeclarePhone(String declarePhone) {
		this.declarePhone = declarePhone;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getQuality() {
		return quality;
	}
	public void setQuality(Integer quality) {
		this.quality = quality;
	}
	public Integer getbid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getResponsibilityName() {
		return responsibilityName;
	}
	public void setResponsibilityName(String responsibilityName) {
		this.responsibilityName = responsibilityName;
	}
	public String getResponsibilityPhone() {
		return responsibilityPhone;
	}
	public void setResponsibilityPhone(String responsibilityPhone) {
		this.responsibilityPhone = responsibilityPhone;
	}
	@Override
	public String toString() {
		return "PorjectDeclare [id=" + id + ", declareProject=" + declareProject + ", declareNumber=" + declareNumber
				+ ", declareFraction=" + declareFraction + ", declareScale=" + declareScale + ", declareArrange="
				+ declareArrange + ", declareAssess=" + declareAssess + ", declareSurveying=" + declareSurveying
				+ ", declarePlan=" + declarePlan + ", contract=" + contract + ", declareCompany=" + declareCompany
				+ ", declareName=" + declareName + ", declarePhone=" + declarePhone + ", finishTime=" + finishTime
				+ ", quality=" + quality + ", bid=" + bid + ", responsibilityName=" + responsibilityName
				+ ", responsibilityPhone=" + responsibilityPhone + "]";
	}

	
}
