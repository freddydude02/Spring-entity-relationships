package com.nology.java.consolidation.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.nology.java.consolidation.security.jwt.AuthEntryPointJwt;
import com.nology.java.consolidation.security.jwt.AuthTokenFilter;
import com.nology.java.consolidation.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
    private UserService userService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public AuthTokenFilter authTokenFilter() {
	     return new AuthTokenFilter();
	}
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
	 @Bean
	    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
	        http
	               
        	.cors().and().csrf().ignoringAntMatchers("/auth/**", "/test/**")
            .and()
//            ignoring instead of disable
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests().antMatchers("/auth/**", "/test/**").permitAll()
            .anyRequest().authenticated()
            .and()
//            extra
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

	        return http.build();
	    }
	 
	
	
}
