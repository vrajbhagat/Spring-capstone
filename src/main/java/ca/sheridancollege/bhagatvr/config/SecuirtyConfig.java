package ca.sheridancollege.bhagatvr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecuirtyConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// remove the two lines in production system
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
			.antMatchers("/user/**").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/register").permitAll()
			.antMatchers("/", "/js/**", "/css/**", "/images/**", "/**").permitAll() 
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated()
			.and().formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.permitAll() 
			.and().logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
				.logoutSuccessUrl("/login?logout").permitAll()
			.and()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//			.passwordEncoder(NoOpPasswordEncoder.getInstance())
//			.withUser("Frank").password("1234").roles("USER")
//			.and()
//			.withUser("manager").password("password").roles("MANAGER");
//	}
}
