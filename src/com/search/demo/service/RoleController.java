package com.search.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.entity.*;
import com.bstek.dorado.data.provider.Page;
import com.search.demo.entity.Menu;
import com.search.demo.entity.Role;
import com.search.demo.entity.User;

@Component
public class RoleController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@DataProvider 
	public Collection<Role> getroles(){
		Collection<Role> collection = jdbcTemplate.query("select * from role", new RowMapper<Role>(){

			@Override
			public Role mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Role role = new Role();
				role.setId(rs.getInt("id"));
				role.setRolename(rs.getString("rolename"));
				role.setDescriptionString(rs.getString("description"));
				return role;
			}
		});
		return collection;
	}
	
	
	/**
	 * ��ѯ��ǰ��ɫ�µ������û�
	 */
	@DataProvider
	public void getusers(Page<User> page, Object parameter){
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		int startRow = (pageNo - 1) * pageSize ; 
		
		String sql = "select u.id,u.loginname, u.username, u.phone, u.department,u.position " +
				"from role r, user u, roleuser ru  " +
				"where ru.userid=u.id and ru.roleid=r.id and ru.roleid=? limit " +startRow+", " +pageSize+" "; 
		Collection<User> collection = jdbcTemplate.query(sql, new Object[]{(int)parameter},new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setLoginname(rs.getString("loginname"));
				user.setUsername(rs.getString("username"));
				user.setPhone(rs.getString("phone"));
				user.setDepartment(rs.getString("department"));
				user.setPosition(rs.getString("position"));
				return user;
			}
			
		});
		
		/**
		 * ������������
		 */
		int count = jdbcTemplate.queryForInt("select count(*) from role r, user u, roleuser ru where ru.userid=u.id and ru.roleid=? ",new Object[]{(int)parameter});
		
		page.setEntities(collection);
		page.setEntityCount(count);
	}
	
	
	/**
	 * ���ҵ�ǰ��ɫӵ�еĹ���,
	 * ǰ̨����һ��roleid����
	 */
	@DataProvider
	public Collection<Menu> getMenuByRoleId(Object roleid){
		Collection<Menu> collection =null;
		if(roleid != null){
			String sql = "select menu.* from role, menu, rolemenu WHERE rolemenu.roleid=role.id and menu.id=rolemenu.menuid and role.id=?";
			collection = jdbcTemplate.query(sql, new Object[]{(int)roleid},new RowMapper<Menu>(){

				@Override
				public Menu mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Menu menu = new Menu();
					menu.setId(rs.getInt("id"));
					menu.setParentid(rs.getInt("parentid"));
					menu.setMenuname(rs.getString("menuname"));
					return menu;
				}
				
			});
			
		}
		return collection;
	}
	
	
	
	/**
	 * ���ҵ�ǰ��ɫӵ�еĹ��ܣ�2�����ܣ�
	 * @param para
	 * @return
	 */
	@DataProvider
	public Collection<Menu> getmenuslevel2(Object para){
		Collection<Menu> collection = jdbcTemplate.query("select * from menu where parentid=?",new Object[]{(int)para}, new RowMapper<Menu>(){

			@Override
			public Menu mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Menu menu = new Menu();
				menu.setMenuname(rs.getString("menuname"));
				return menu;
			}
			
		});
		return collection;
	
	}
	
	
	/**
	 * ��ɫ���� ����ɾ����
	 */
	@DataResolver
	@Transactional
	public void updateRole(Collection<Role> roles){
		for(Role role : roles){
			EntityState entityState = EntityUtils.getState(role);
			if(entityState.NEW.equals(entityState)){
				jdbcTemplate.update("insert into role(rolename,description)values(?,?)",new Object[]{role.getRolename(),role.getDescriptionString()});
				System.out.println("��ɫ��ӳɹ�");
			}
			if(entityState.DELETED.equals(entityState)){
				jdbcTemplate.update("delete from role where id=?",new Object[]{role.getId()});
				System.out.println("��ɫɾ���ɹ�");
			}
			if(entityState.MODIFIED.equals(entityState)){
				jdbcTemplate.update("update role set rolename=?,description=? where id=?",new Object[]{role.getRolename(),role.getDescriptionString(),role.getId()});
				System.out.println("��ɫ��Ϣ�޸ĳɹ�");
			}
		}
		
	}
}
