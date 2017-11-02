package com.search.demo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Page;
import com.search.demo.dao.MenuDao;
import com.search.demo.entity.User;

public class MenuDaoImpl implements MenuDao {
	@Resource
	JdbcTemplate jdbcTemplate;

	@Override
	public void getAll(Page<User> page, Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		//����ӵڼ��п�ʼȡ��¼,(0��ʼ��)
		int startRowNumber = (pageNo - 1)*pageSize;
		
		String sql = null;
		
		List<Object> args = new ArrayList<Object>();
		/**
		 * ƴ��sql���,  
		 */
		
		if(parameter == null){
			
		}else{
		     sql = "select * from user where 1=1";
		     
		     if(parameter.get("id") != null){
		    	 args.add((int)parameter.get("id"));
		    	 sql = sql + " and deptid=?";
		     }
		     
			if(parameter.get("loginname") == null){
				
			}else if ((String)parameter.get("loginname") == "") {
				
			}else{
				args.add((String)parameter.get("loginname"));
				sql = sql + " and loginname=? ";
			}
			
			
			if(parameter.get("username") == null ){
				
			}else if ((String)parameter.get("username") == "") {
				
			}else{
				args.add((String)parameter.get("username"));
				sql = sql + " and username=? ";
			}
			
			
			if(parameter.get("phone") == null ){
				
			}else if((String)parameter.get("phone")==""){
				
			}else{
				args.add((String)parameter.get("phone"));
				sql = sql + " and phone=? ";
			}
			
			
			if(parameter.get("date") == null ){
				
			}else if((String)parameter.get("date") == ""){
				
			}else{
				args.add((String)parameter.get("date"));
				sql = sql + " and date=? ";
			}
			
			
			if(parameter.get("position") == null ){
				
			}else if((String)parameter.get("position")==""){
				
			}else{
				args.add((String)parameter.get("position"));
				sql = sql + " and position=? ";
			}
			
			
			if(parameter.get("department") == null ){
				
			}else if ((String)parameter.get("department")=="") {
				
			}else{
				args.add((String)parameter.get("department"));
				sql = sql + " and department=? ";
			}
			
			sql = sql + " limit "+ startRowNumber +" ,"+ pageSize+" ";
			
			/**
			 * ��ʼִ��sql���
			 */
			Collection<User> list = jdbcTemplate.query(sql,args.toArray(), new RowMapper<User>(){
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
			
			int totalCount = jdbcTemplate.queryForInt("select count(*) from user");
			
			page.setEntities(list);
			page.setEntityCount(totalCount);
		}
	}

	@Override
	public void updateUsers(Collection<User> users) {
		// TODO Auto-generated method stub
		for(User user : users){
			EntityState entityState = EntityUtils.getState(user);
			
			if(EntityState.NEW.equals(entityState)){
				//���� 
				String  sql = "INSERT INTO user(loginname,username,phone,date,position,department)VALUES(?,?,?,?,?,?)";
				jdbcTemplate.update(sql,new Object[] { user.getLoginname(),user.getUsername(),user.getPhone(),user.getDate(),user.getPosition(),user.getDepartment() });
				System.out.println("�����ɹ�");
			}else if(EntityState.MODIFIED.equals(entityState)){
				//�޸�
				System.out.println(user.getId());
				String sql = "UPDATE user SET loginname=?,username=?,phone=?,date=?,position=?,department=? WHERE id=?";
				jdbcTemplate.update(sql,new Object[] { user.getLoginname(),user.getUsername(),user.getPhone(),user.getDate(),user.getPosition(),user.getDepartment(),user.getId()});
				System.out.println("�޸ĳɹ�");
			}else if(EntityState.DELETED.equals(entityState)){
				//ɾ��
				String sql = "DELETE from user WHERE id=?"; 
				jdbcTemplate.update(sql,new Object[]{ user.getId()});
				System.out.println("ɾ���ɹ�");
			}
		}
	}

}
