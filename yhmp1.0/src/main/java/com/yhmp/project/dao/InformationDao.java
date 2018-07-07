package com.yhmp.project.dao;

import java.util.List;

import com.yhmp.project.entity.PorjectDeclare;
/**
 * 项目信息dao对应mapper
 * @author liang
 *
 */
public interface InformationDao {

	List<PorjectDeclare> selectProjectInformation(PorjectDeclare porjectDeclare);
	Integer editProject(PorjectDeclare porjectDeclare);
	Integer deleteProject(PorjectDeclare porjectDeclare);
}