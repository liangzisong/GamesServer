package com.yhmp.system.entity;

import java.io.Serializable;
/**
 * 用户实体类
 * @author liang
 *
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3344624928266674948L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 职位
	 */
	private String job;
	/**
	 * 部门
	 */
	private String department;
	/**
	 * 入职时间
	 */
	private String hiredate;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 入职状态    0.实习     1：在职    2：离职
	 */
	private Integer status;
	/**
	 * 居住地
	 */
	private String domicile;
	/**
	 * 性别            0：女         1.男
	 */
	private Integer sex;
	/**
	 * 年纪
	 */
	private String age;
	/**
	 * 人员权限
	 */
	private String authority;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getHiredate() {
		return hiredate;
	}
	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDomicile() {
		return domicile;
	}
	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", job=" + job + ", department="
				+ department + ", hiredate=" + hiredate + ", phone=" + phone + ", status=" + status + ", domicile="
				+ domicile + ", sex=" + sex + ", age=" + age + ", authority=" + authority + "]";
	}
}
