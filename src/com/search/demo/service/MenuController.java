package com.search.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sun.print.resources.serviceui;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.search.demo.entity.Menu;
import com.sun.crypto.provider.RSACipher;



@Component
public class MenuController {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * ²éÑ¯²Ëµ¥Ãû
	 * @param parameter
	 * @return
	 */
	@DataProvider
	public Collection<Menu> getmenus(Object parameter){
		
		if(parameter == null){
			Collection<Menu> collection = jdbcTemplate.query("select * from menu where parentid is null", new RowMapper<Menu>(){

				@Override
				public Menu mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Menu menu = new Menu();
					menu.setId(rs.getInt("id"));
					menu.setParentid(rs.getInt("id"));
					menu.setMenuname(rs.getString("menuname"));
					return menu;
				}
			});
			return collection;
		}else{
			int id =  (int)parameter;
			Collection<Menu> collection = jdbcTemplate.query("select * from menu where parentid=?",new Object[]{id}, new RowMapper<Menu>(){

				@Override
				public Menu mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Menu menu = new Menu();
					menu.setId(rs.getInt("id"));
					menu.setParentid(rs.getInt("id"));
					menu.setMenuname(rs.getString("menuname"));
					menu.setUrl(rs.getString("url"));
					return menu;
				}
			});
			return collection;
		}
	}
	
	
	@DataResolver
	@Transactional
	public void updateMenu(Collection<Menu> menus){
		
	}
}
