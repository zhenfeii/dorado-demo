package com.search.demo.service;

import java.util.Collection;
import java.util.Map;

import com.bstek.dorado.data.provider.Page;
import com.search.demo.entity.User;

public interface UserService {
	void getUserInfo(Map<String, Object> para, Page page);
	void updateUsers(Collection<User> users);
}
