package com.search.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.search.demo.entity.Dept;
import com.search.demo.entity.User;

@Component
public class DeptPR {
	
	@Resource
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取部门信息，（递归树）
	 * @param parameter
	 * @return
	 */
	@DataProvider
	public Collection<Dept> getdDepts(Object parameter){
		if(parameter == null){
			Collection<Dept> collection = jdbcTemplate.query("select * from dept where parentid is null",new RowMapper<Dept>(){

				@Override
				public Dept mapRow(ResultSet rs, int arg1)
						throws SQLException {
					// TODO Auto-generated method stub
					Dept dept = new Dept();
					dept.setId(rs.getInt("id"));
					dept.setParentid(rs.getInt("parentid"));
					dept.setDeptname(rs.getString("deptname"));
					return dept;
				}
				
			});
			return collection;
		}else{
			Collection<Dept> collection = jdbcTemplate.query("select * from dept where parentid=?",new Object[]{(int)parameter},new RowMapper<Dept>(){

				@Override
				public Dept mapRow(ResultSet rs, int arg1)
						throws SQLException {
					// TODO Auto-generated method stub
					Dept dept = new Dept();
					dept.setId(rs.getInt("id"));
					dept.setParentid(rs.getInt("parentid"));
					dept.setDeptname(rs.getString("deptname"));
					return dept;
				}
				
			});
			return collection;
		}
	}

	
	@DataProvider
	public Collection<User> getUser(int deptid){
		Collection<User> list = jdbcTemplate.query("select * from dept where deptid=?",new Object[]{deptid}, new RowMapper<User>(){
				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user =new User();
					user.setId(rs.getInt("id"));
					user.setLoginname(rs.getString("loginname"));
					user.setUsername(rs.getString("username"));
					user.setPhone(rs.getString("phone"));
					user.setDate(rs.getString("date"));
					user.setPosition(rs.getString("position"));
					user.setDepartment(rs.getString("department"));
					user.setDeptid(rs.getInt("deptid"));
					return user;
				}			
			});
		return list;
	}

}
