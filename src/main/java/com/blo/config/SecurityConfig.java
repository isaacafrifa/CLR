package com.blo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //this LOC enables the @PreAuthorize annotation.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// not MyUserDetailsService
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoderBean() {
		return new BCryptPasswordEncoder(10);
	}

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
		.cors()
		.and()
		.csrf().disable() // CHANGE THIS LATER
		.authorizeRequests()
		.antMatchers("/registration").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.defaultSuccessUrl("/login-success", true) 
		// .loginProcessingUrl("") //by default, loginProcessingUrl is "/login"
		// .failureUrl("") //by default, failureUrl is http://localhost:8080/login?error
		//  .and()
		//	.logout()
		// 	.logoutUrl("/perform_logout") //by default, logoutUrl is "/logout"
		// 	.invalidateHttpSession(true)
		// 	.deleteCookies("JSESSIONID")
		//	.and()
		//	.httpBasic() //use this when you're using http's basic auth
		;

//         http
//		 .cors().and()
//		 .csrf().disable() // will change this later
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
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:3000"); // this allows this origin
		
		//config.addAllowedOrigin("*"); // this allows all origins
		// config.addAllowedHeader("*"); // this allows all headers

		// setAllowedHeaders is important! Without it, OPTIONS preflight request will
		// fail with 403 Invalid CORS request
		// Preflight OPTIONS requests are always sent when Content-Type of the Request
		// is JSON
		config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
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
