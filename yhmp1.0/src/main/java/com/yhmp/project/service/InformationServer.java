package com.yhmp.project.service;

import java.util.List;

import com.yhmp.project.entity.PorjectDeclare;
/**
 * 项目信息接口由具体实现类来实现业务
 * @author Administrator
 *
 */
public interface InformationServer {

	<T> T selectProjectInformation(PorjectDeclare porjectDeclare);
	String editProject(PorjectDeclare porjectDeclare);
	String deleteProject(PorjectDeclare porjectDeclare);
}
