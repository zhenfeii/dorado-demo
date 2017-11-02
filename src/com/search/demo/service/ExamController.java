package com.search.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.search.demo.entity.Exam;

@Component
public class ExamController {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@DataProvider
	public Collection<Exam> getExams(){
		Collection<Exam> collection = jdbcTemplate.query("select * from  tktm", new RowMapper<Exam>(){

			@Override
			public Exam mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Exam exam = new Exam();
				exam.setId(rs.getString("ID_"));
				exam.setDate(rs.getString("CREATE_DATE"));
				exam.setContent(rs.getString("CONTENT"));
				System.out.println(exam.getDate());
				return exam;
			}
			
		});
		return collection;
	}
	
	
}
