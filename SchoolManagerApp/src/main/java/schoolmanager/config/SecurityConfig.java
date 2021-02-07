package schoolmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import schoolmanager.service.StudentService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private StudentService studentService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
	    filter.setEncoding("UTF-8");
	    filter.setForceEncoding(true);
	    http.addFilterBefore(filter, CsrfFilter.class);
		
		http.authorizeRequests()
		.antMatchers("/").hasRole("STUDENT")
		.antMatchers("/student/studentFormAdd").hasRole("ADMIN")
		.antMatchers("/student/saveStudent").hasRole("ADMIN")
		.antMatchers("/student/studentUpdateForm").hasRole("ADMIN")
		.antMatchers("/student/updateStudent").hasRole("ADMIN")
		.antMatchers("/student/deleteStudent").hasRole("ADMIN")
		.antMatchers("/student/showStudentDetails").hasRole("TEACHER")
		.antMatchers("/student/addStudentToGroup").hasRole("TEACHER")
		.antMatchers("/student/addStudentToSubject").hasRole("TEACHER")
		//
		.antMatchers("/group/groupFormAdd").hasRole("ADMIN")
		.antMatchers("/group/saveGroup").hasRole("ADMIN")
		.antMatchers("/group/groupUpdateForm").hasRole("ADMIN")
		.antMatchers("/group/updateGroup").hasRole("ADMIN")
		.antMatchers("/group/deleteGroup").hasRole("ADMIN")
		.antMatchers("/group/showGroupDetails").hasRole("TEACHER")
		.antMatchers("/group/removeStudentFromGroup").hasRole("TEACHER")
		//
		.antMatchers("/subject/subjectFormAdd").hasRole("ADMIN")
		.antMatchers("/subject/saveSubject").hasRole("ADMIN")
		.antMatchers("/subject/subjectUpdateForm").hasRole("ADMIN")
		.antMatchers("/subject/updateSubject").hasRole("ADMIN")
		.antMatchers("/subject/deleteSubject").hasRole("ADMIN")
		.antMatchers("/subject/showSubjectDetails").hasRole("TEACHER")
		.antMatchers("/subject/removeStudentFromSubject").hasRole("TEACHER")
		//
		.antMatchers("/grade/**").hasRole("TEACHER")
			.and()
		.formLogin()
		.loginPage("/showLoginPage")
		.loginProcessingUrl("/authenticateTheUser")
		.permitAll()
			.and()
		.logout().permitAll()
			.and()
		.exceptionHandling().accessDeniedPage("/access-denied");
		
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(studentService);

		return auth;
	}
	
}
