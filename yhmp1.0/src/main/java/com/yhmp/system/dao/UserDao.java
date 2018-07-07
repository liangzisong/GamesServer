package com.yhmp.system.dao;

import java.util.List;

import com.yhmp.system.entity.User;
/**
 * 用户dao对应mapper
 * @author liang
 *
 */
public interface UserDao {

	public List<User> selectUser(User user);
	public Integer updateUser(User user);
	public Integer addUser(User user);
	public Integer editUser(User user);
	public Integer deleteUser(Integer id);
}