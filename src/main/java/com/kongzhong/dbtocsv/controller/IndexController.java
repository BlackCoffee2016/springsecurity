package com.kongzhong.dbtocsv.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kongzhong.dbtocsv.bean.SQL;
import com.kongzhong.dbtocsv.mapper.MainMapper;

@Controller
public class IndexController {
	
	@Autowired
	private MainMapper mainMapper;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	private ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("index");
		
		List<SQL> list = mainMapper.getSQLList();
		
		List<SQL> newList = new ArrayList<SQL>();
		for(SQL sql : list) {
			if(request.isUserInRole(sql.getRole())) {
				newList.add(sql);
			}
		}
		
		
		
		mv.addObject("list", newList);
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/csv", method = RequestMethod.GET, produces = "application/octet-stream; charset=GBK")
	private String csv(String date, int id, HttpServletRequest request, HttpServletResponse response) throws ParseException, UnsupportedEncodingException {
        
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse(date);
        Timestamp start = new Timestamp(startDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, 1);
        Date endDate = calendar.getTime();
        Timestamp end = new Timestamp(endDate.getTime());
        
        SQL sql = mainMapper.getSQL(id);
        if(!request.isUserInRole(sql.getRole())) {
        	throw new RuntimeException("权限不足");
		}
        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(sql.getName().getBytes("GBK"), "ISO8859-1") + "(" + date + ")" + ".csv");
        
        StringBuilder sb = new StringBuilder();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        	connection = DriverManager.getConnection(sql.getDbUrl(), sql.getDbUsername(), sql.getDbPassword());
        	ps = connection.prepareStatement(sql.getSql());
        	ps.setTimestamp(1, start);
        	ps.setTimestamp(2, end);
        	rs = ps.executeQuery();
        	
        	ResultSetMetaData metaData = rs.getMetaData();
        	int columnCount = metaData.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String columnName = metaData.getColumnLabel(i);
        		sb.append(columnName);
        		if(i != columnCount) {
        			sb.append(",");
        		}
        	}
        	
        	sb.append("\r\n");
        	while(rs.next()) {
        		for(int i = 1; i <= columnCount; i++) {
        			String data = rs.getString(metaData.getColumnLabel(i));
            		sb.append(data == null ? "" : data);
            		if(i != columnCount) {
            			sb.append(",");
            		}
        		}
        		sb.append("\r\n");
        	}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
        
		return sb.toString();
	}

}
