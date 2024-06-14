package com.prodapt.capstoneproject.security.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prodapt.capstoneproject.security.jwt.AuthTokenFilter;
import com.prodapt.capstoneproject.security.service.UserDetailsServiceImpl;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
 
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
 
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
 
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
 
		return authProvider;
	}
 
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
 
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
 
	public final static String[] PUBLIC_REQUEST_MATCHERS = { "/api/test/all", "/api/auth/**", "/api-docs/**",
			"/swagger-ui/**", "/v3/api-docs/**" };
 
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// http.cors(AbstractHttpConfigurer::disable)
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req -> req.requestMatchers(PUBLIC_REQUEST_MATCHERS).permitAll()
						.requestMatchers("customer/signup/**").permitAll()
						.requestMatchers("customer/signin/**").permitAll()
						//account controller has preauthorize in controller
						//admin controller - reports access
						.requestMatchers("/admin/getperfomancedashboardreport/{adminid}").hasRole("SUPER_USER_ADMIN")
						.requestMatchers("/admin/getexceptionreport/{adminid}").hasRole("SUPER_USER_ADMIN")
						.requestMatchers("/admin/getadminactionreport/{adminid}").hasRole("SUPER_USER_ADMIN")
						.requestMatchers("/admin/getstatusreport/{adminid}").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						.requestMatchers("/admin/getdunningreport/{adminid}").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						.requestMatchers("/admin/getpaymentdetailsreport/{adminid}").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						//admin controller - remaining mappings
						.requestMatchers("/admin/updateadmin").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						.requestMatchers("/admin/findall").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						.requestMatchers("/admin/findbyid/{id}").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						.requestMatchers("/admin/deletebyid/{id}").hasRole("SUPER_USER_ADMIN")
						.requestMatchers("/admin/deleteall").hasRole("SUPER_USER_ADMIN")
						.requestMatchers("/admin/sendnotification").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						.requestMatchers("/admin/getadmin/{username}").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						.requestMatchers("/admin/sendnotification").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						.requestMatchers("/admin/sendnotification").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						//auth controller is open- allowed in public_request_matcher static variable
						//customer controller
						.requestMatchers("/customer/**").hasRole("CUSTOMER")
						//notification controller					
						.requestMatchers("/notification/**").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						//payment controller
						.requestMatchers("/payment/addpayment").hasRole("CUSTOMER")
						.requestMatchers("/payment/**").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						//report controller
						.requestMatchers("/reports/**").hasAnyRole("SUPER_USER_ADMIN","SUPPORT_ADMIN")
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
 
}