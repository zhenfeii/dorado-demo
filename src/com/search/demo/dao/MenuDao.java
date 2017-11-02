package com.search.demo.dao;

import java.util.Collection;
import java.util.Map;

import com.bstek.dorado.data.provider.Page;
import com.search.demo.entity.User;

public interface MenuDao {
	public  void getAll(Page<User> page, Map<String, Object> parameter);
	
	public void updateUsers(Collection<User> users);
}
