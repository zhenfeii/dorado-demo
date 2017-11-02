package com.search.demo.dao;

import java.util.Collection;
import java.util.Map;

import com.bstek.dorado.data.provider.Page;
import com.search.demo.entity.User;

public interface UserDao {
	void getUserInfo(Map<String, Object> parameter, Page page);
	void updateUsers(Collection<User> users);
}
