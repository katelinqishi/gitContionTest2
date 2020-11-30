package com.securiyt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.securiyt.service.MytestUserDetailService;

@EnableWebSecurity
public class SecurityConfigKuang extends WebSecurityConfigurerAdapter {
	
	@Autowired
	MytestUserDetailService myservice;
	// 采用链式编程
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 首页所有人可以访问，功能页只有对应权限的人才能访问 super.configure(http);

		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/love1/***").hasRole("vip1")
				.antMatchers("/love2/***").hasRole("vip2").antMatchers("/love3/***").hasRole("vip3");

		// 默认没有权限会进入login页面 这个页面是security自带的
		// 开启登录页面
		// 定制登录页
		// 如果前端的登录路径就是/login的话在这加.loginProcessingUrl("/login")
		// 前端的表单name属性如果不是username和password这两个可以使用usernameParameter("user").passwordParameter("password")进行定制
		http.formLogin();
//		.loginPage("/tologin").usernameParameter("user").passwordParameter("password").loginProcessingUrl("/login");
		// 防止网站攻击：get post
		http.csrf().disable();// 关闭csrf功能，这是网页登出失败可能存在的原因 要不用post方式进行注销要不就关掉
		// 开启注销功能
		http.logout();
		// 开启记住我功能 自定义接受前端name参数
		http.rememberMe().rememberMeParameter("remeber");
	}

	// 认证 在springboot 2.1.x中可以直接使用
	// 密码编码：passwordEncoder
	// 在springSecurity 5+中新增了很多加密方法
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// super.configure(auth);
		// 这些数据正常应该从数据库中读取
		auth.userDetailsService(myservice).passwordEncoder(passwordencoder());
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
				.withUser("li")
				.password(new BCryptPasswordEncoder().encode("123456")).roles("vip1", "vip2")
				.and().withUser("li2")
				.password(new BCryptPasswordEncoder().encode("123456")).roles("vip2", "vip3")
				.and().withUser("li3")
				.password(new BCryptPasswordEncoder().encode("123456")).roles("vip1", "vip2", "vip3");
	}

	@Bean
	PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}

}
