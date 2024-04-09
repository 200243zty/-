package com.xs.config;

import com.xs.service.impl.AdminServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	private AdminServiceImpl adminService;

	@Resource
	private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(adminService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				//禁用 csrf 防御
				.csrf().disable()
				//开启跨域支持  并设置 CORS 的配置源，corsConfigurationSource() 方法应该返回一个 CorsConfiguration 对象，该对象包含了 CORS 的配置信息
				.cors().configurationSource(corsConfigurationSource())
				.and()
				//设置会话管理策略为不创建会话，即基于 Token 进行认证，而不是基于 Session。
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				//配置请求的授权规则
				.authorizeRequests()
				//任何 /admin 开头的路径下的请求都需要经过JWT验证
				.antMatchers("/admin/**").authenticated()
				//其它路径全部放行
				.anyRequest().permitAll()
				.and()
				.headers().frameOptions().disable()
				.and()
				// 在 UsernamePasswordAuthenticationFilter 之前添加自定义的 JwtLoginFilter，用于处理登录请求生成 JWT。
				//  在 UsernamePasswordAuthenticationFilter 之前添加自定义的 JwtFilter，用于验证请求中的 JWT。
				.addFilterBefore(new JwtLoginFilter("/admin/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
				//未登录时，返回json，在前端执行重定向
				.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint);

	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setMaxAge(Duration.ofHours(1));
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
