package com.search.demo.service.impl;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bstek.dorado.data.provider.Page;
import com.search.demo.dao.UserDao;
import com.search.demo.entity.User;
import com.search.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Resource
	UserDao userDao;

	@Override
	public void getUserInfo(Map<String, Object> para, Page page) {
		// TODO Auto-generated method stub
		userDao.getUserInfo(para, page);
	}

	@Override
	public void updateUsers(Collection<User> users) {
		// TODO Auto-generated method stub
		userDao.updateUsers(users);
	}

}
