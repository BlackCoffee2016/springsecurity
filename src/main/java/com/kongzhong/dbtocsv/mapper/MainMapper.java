package com.kongzhong.dbtocsv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.kongzhong.dbtocsv.bean.Admin;
import com.kongzhong.dbtocsv.bean.SQL;

public interface MainMapper {

	@Select("select USERNAME,PASSWORD,ROLES from XXG_USER where USERNAME=#{username}")
	Admin getAdminByUsername(String username);

	@Select("select ID,NAME,ROLE from XXG_SQL")
	List<SQL> getSQLList();
	
	@Select("select ID,NAME,ROLE,DB_URL as dbUrl,DB_USERNAME as dbUsername,DB_PASSWORD as dbPassword,SQL from XXG_SQL where ID=#{id}")
	SQL getSQL(int id);
}
