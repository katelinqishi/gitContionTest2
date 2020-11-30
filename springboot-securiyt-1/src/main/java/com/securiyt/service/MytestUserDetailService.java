package com.securiyt.service;

import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("mytestUserDetailService") // 这个类用来从数据库中查询用户进行拦截验证
public class MytestUserDetailService implements UserDetailsService {
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		/**
		 * 这段写数据库操作
		 */

		// **********************将权限存入
		List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role，ROLE_sale");// hasRole方法的引用必须加前缀ROLE_
		// 返回用户名密码和权限
		return new User("lisang", new BCryptPasswordEncoder().encode("123"), auths);
	}

}
