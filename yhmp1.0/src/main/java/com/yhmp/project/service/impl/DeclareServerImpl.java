package com.yhmp.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhmp.project.dao.DeclareDao;
import com.yhmp.project.entity.PorjectDeclare;
import com.yhmp.project.service.DeclareServer;
/**
 * 项目申报交接server
 * @author liang
 *
 */
@Service
public class DeclareServerImpl implements DeclareServer {

	@Autowired
	private DeclareDao declareDao;
	
	@Override
	public int insertPorjectDeclare(PorjectDeclare porjectDeclare) {
		
		return declareDao.insertPorjectDeclare(porjectDeclare);

	}

}
