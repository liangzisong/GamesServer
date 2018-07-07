package com.yhmp.system.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhmp.system.dao.UserDao;
import com.yhmp.system.entity.User;
import com.yhmp.system.service.UserServer;
/**
 * 用户server
 * @author liang
 *
 */
@Service
public class UserServerImpl implements UserServer {

	@Autowired
	private UserDao userDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T selectUser(User user) {
		List<User> u = userDao.selectUser(user);
		System.out.println("u.size()="+u.size());
		switch (u.size()) {
		//登陆
		case 0:
			System.out.println("未查到");
			return null;
		//个人信息
		case 1:
			System.out.println("查询出一条");
			return (T) u.get(0);
		//人员管理
		default:
			System.out.println("查询出多条");
			//屏蔽显示管理员
			Iterator<User> it = u.iterator();
			while (it.hasNext()) {
				User x = it.next();
				//管理员id为-1
				if(-1 == x.getId()) {
					it.remove();
				}
			}
			return (T) u;
		}
	}
	@Override
	public String updateUser(User user) {
		Integer i = userDao.updateUser(user);
		if(i == 1) {
			return "修改成功";
		}
		return "修改失败";
	}
	@Override
	public String addUser(User user) {
		Integer i = userDao.addUser(user);
		if(i == 1) {
			return "添加成功";
		}
		return "添加失败";
	}
	@Override
	public String editUser(User user) {
		Integer i = userDao.editUser(user);
		if(i == 1) {
			return "修改成功";
		}
		return "修改失败";
	}
	@Override
	public String deleteUser(String id) {
		try {
			Integer userId = Integer.valueOf(id);
			Integer i = userDao.deleteUser(userId);
			if(i == 1) {
				return "删除成功";
			}
			return "删除失败";
		}catch (NumberFormatException e) {
			return "删除失败";
		}

	}

}
