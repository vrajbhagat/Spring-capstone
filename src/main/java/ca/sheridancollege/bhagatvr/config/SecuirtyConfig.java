package ca.sheridancollege.bhagatvr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecuirtyConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private CustomLoginSuccessHandler successHandler;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
			.antMatchers("/user/**").hasAnyAuthority("ROLE_USER")
			.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
			.antMatchers(HttpMethod.POST, "/register").permitAll()
			.antMatchers("/", "/js/**", "/confirm", "/register", "/css/**", "/images/**", "/**").permitAll() 
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated()	
			.and().formLogin()
				.loginPage("/login")
				.successHandler(successHandler)
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
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}
