package com.yhmp.project.service;

import com.yhmp.project.entity.PorjectDeclare;
/**
 * 项目申报交接接口由具体实现类来实现业务
 * @author Administrator
 *
 */
public interface DeclareServer {
	int insertPorjectDeclare(PorjectDeclare porjectDeclare);
}
