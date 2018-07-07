package com.yhmp.system.service;

import java.util.List;

import com.yhmp.system.entity.User;
/**
 * 用户server接口由具体实现类来实现业务
 * @author liang
 *
 */
public interface UserServer {

	public <T> T selectUser(User user);
	public String updateUser(User user);
	public String addUser(User user);
	public String editUser(User user);
	public String deleteUser(String id);
}