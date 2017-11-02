package com.search.demo.controller;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Page;
import com.search.demo.entity.User;
import com.search.demo.service.UserService;

@Component
public class UserController {
	@Resource
	UserService userService;
	
	

	@DataProvider
	public void getUserInfo(Page<User> page, Map<String, Object> parameter) {
		userService.getUserInfo(parameter, page);
	}
	
	@DataResolver
	@Transactional
	public void updateUsers(Collection<User> users){
		userService.updateUsers(users);
	}
		
}
