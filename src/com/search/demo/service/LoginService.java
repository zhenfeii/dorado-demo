package com.search.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.search.demo.entity.Login;

@Component
public class LoginService {
	
	@Resource
	JdbcTemplate jdbcTemplate;
	
	@Expose
	public Map doLogin(Map parameters){
		Map<String, Object> result = new HashMap<String, Object>();
		String username = (String) parameters.get("username");
		String password = (String) parameters.get("password");
		
		Collection<Login> logins = jdbcTemplate.query("select * from login", new RowMapper<Login>(){

			@Override
			public Login mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Login login = new Login();
				login.setUser(rs.getString("username"));
				login.setPass(rs.getString("password"));
				return login;
			}
			
		});
		
		/**
		 * 遍历logins集合的用户名与密码
		 */
		for(Login login : logins){
			if(username.equals(login.getUser()) && password.equals(login.getPass())){
				result.put("result", true);
			}else{
				result.put("result", false);
			}
		}
		return result;
	}
	
	
}
