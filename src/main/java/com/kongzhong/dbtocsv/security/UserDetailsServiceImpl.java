package com.kongzhong.dbtocsv.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kongzhong.dbtocsv.bean.Admin;
import com.kongzhong.dbtocsv.mapper.MainMapper;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MainMapper mainMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = mainMapper.getAdminByUsername(username);
		if(admin == null) {
			throw new UsernameNotFoundException("Admin not found.");
		} else {
			Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER"); 
            auths.add(authority);
            String roles = admin.getRoles();
            String[] roleArray = roles.split(",");
            for(String role : roleArray) {
            	auths.add(new SimpleGrantedAuthority(role));
            }
            User user = new User(username, admin.getPassword(), auths);
            return user;
		}
	}

}
