package com.tattvafoundation.diphonline.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication()
		.passwordEncoder(new BCryptPasswordEncoder())
		.dataSource(dataSource)
		.authoritiesByUsernameQuery("select user_nm, user_status from user_info where user_nm = ?")
		.usersByUsernameQuery("select user_nm, user_pass, 1 from user_info where user_nm = ?");
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors()
		.and()
		.httpBasic()
		.and()
		.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS, "/**")
		.permitAll()
		.anyRequest()
		.authenticated();
		
		http.csrf().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	    .antMatchers("/getcountriesinfo/**")
	    .antMatchers("/getregionsinfo/**")
	    .antMatchers("/getstatesinfo/**")
	    .antMatchers("/getdcyclesinfo/**")
	    .antMatchers("/getsdistrictsinfo/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
}
