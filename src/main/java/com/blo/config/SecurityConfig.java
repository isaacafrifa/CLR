package com.blo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //this LOC enables the @PreAuthorize annotation.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

	
	//not MyUserDetailsService
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoderBean() {
		return new BCryptPasswordEncoder(10);
	}
	
	//for my custom auth failure handler
	 @Autowired
	    public AuthenticationFailureHandler customAuthenticationFailureHandler;
		//for my custom auth success handler
	 @Autowired
	 public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	 
	 //for my custom authEntryPoint
	 @Autowired
	 public CustomAuthenticationEntryPoint authenticationEntryPoint;
	 
	// for external auth
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		 provider.setPasswordEncoder(passwordEncoderBean());
		return provider;
	}
	
	

	// insert your auth bean into authManagerBuilder
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	//for custom Auth handler
//	 @Bean
//	    @Override
//	    public AuthenticationManager authenticationManagerBean() throws Exception {
//	        return super.authenticationManagerBean();
//	    }

	// Note: When you are using authentication, before executing the actual client request,
	// the browser sends an OPTION request.
	// So we have to configure Spring Security to enable all OPTIONS requests,I've done so last method in this class
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	http
		.cors() //enabling cors and using the my customizerd CorsFilter bean provided
		
		//handling csrf
		.and()
		.csrf()//.disable()
		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) 
		.and()
		
		//handling requests
		.authorizeRequests()
			.antMatchers("/registration").permitAll()
			.antMatchers("/test").permitAll()
			.antMatchers("/api/csrf").permitAll()
			//.antMatchers("/forgot_password/*").permitAll() //because Im using /forgot_password?email=... now
			.antMatchers("/forgot_password").permitAll()
			.antMatchers("/logout_success").permitAll()
		.anyRequest().authenticated()
		
		
		//customizing login params
		.and()
		.formLogin()
		//.defaultSuccessUrl("/login_success", true) //commented because the redirect function associated with defaultSuccessUrl is problematic so I created my custom handler
		.successHandler(customAuthenticationSuccessHandler) //customizing login success handler using my custom auth success handler		
		.failureHandler(customAuthenticationFailureHandler) //customizing login failure handler using my custom auth error handler
		// .loginProcessingUrl("") //by default, loginProcessingUrl is "/login"
		// .failureUrl("") //by default, failureUrl is http://localhost:8080/login?error
		
		
		//handling exceptions
		.and()
		.exceptionHandling()
		//.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) //outputs 401 at entry point --commented because I am using custom AuthEntryPoint
		.authenticationEntryPoint(authenticationEntryPoint)
		
		//customizing logout params
		.and()
		.logout()
		//using logoutSuccessHandler instead of logoutSuccessUrl
		.logoutSuccessHandler(new LogoutSuccessHandler() {
				@Override
					public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
					if(authentication!=null) {
					LOGGER.info("USER [" + authentication.getName() + "] LOGGED OUT WITH IP ADDRESS [ "+request.getRemoteAddr()+" ]");
					}
					response.sendRedirect("/logout_success");
					}
				})
	 	//.invalidateHttpSession(true) //by default, session is invalidated 
		.deleteCookies("JSESSIONID") //delete JSESSIONID cookie after logout
		.permitAll()
	
		// 	.logoutUrl("/perform_logout") //by default, logoutUrl is "/logout"
		//	.and()
		//	.httpBasic() //for http's basic auth
		;

//         http
//		 .cors().and()
//		 .csrf().disable() 
//		 .authorizeRequests()
//         .antMatchers("/**/*.{js,html,css}").permitAll()
//         .antMatchers("/").permitAll()
//         .anyRequest().authenticated()
//         .and()
//         .httpBasic()
//         ;

	}

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		final List<String> allowedHeaders= new ArrayList<String>();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("https://myclr.netlify.app"); // this allows this origin
		
		//config.addAllowedOrigin("*"); // this allows all origins
		 config.addAllowedHeader("*"); // this allows all headers

		// setAllowedHeaders is important! Without it, OPTIONS preflight request will
		// fail with 403 Invalid CORS request
		// Preflight OPTIONS requests are always sent when Content-Type of the Request
		// is JSON
	
		/*
		 * commented out so heroku(which uses Java v1.8) can build successfully, workaround provided below
		 */
	//	config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type")); 
		allowedHeaders.add("Authorization");
		allowedHeaders.add("Cache-Control");
		allowedHeaders.add("Content-Type");
		allowedHeaders.add("x-xsrf-token"); 
		//config.setAllowedHeaders(allowedHeaders);
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
