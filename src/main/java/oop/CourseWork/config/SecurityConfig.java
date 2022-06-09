package oop.CourseWork.config;

import oop.CourseWork.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/").permitAll()
                .antMatchers("/checks/**").hasAnyRole("CASHIER", "MANAGER", "ADMIN")
                .antMatchers("/receivings/**").hasAnyRole("RECEIVER", "MANAGER", "ADMIN")
                .antMatchers("/productbase", "/productlog", "/employees/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/orders/**").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/orders").hasAnyRole("MANAGER", "ADMIN")
                .and().formLogin()
                .and().csrf().disable();
    }
}
