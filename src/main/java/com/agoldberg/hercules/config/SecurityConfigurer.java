package com.agoldberg.hercules.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan
public class SecurityConfigurer extends WebSecurityConfigurerAdapter{

    public static final String ADMIN = "ADMIN";
    public static final String MANAGER = "MANAGER";

    @Autowired
    UserDetailsService userService;

    @Override
    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(encoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Override
    public void configure(WebSecurity webSecurity){
        webSecurity.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/css/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/user/forgot").permitAll()
                .antMatchers("/user/reset").permitAll()
                .antMatchers("/user/changepassword").hasAnyAuthority("PRIVILEGE_CHANGE_PASSWORD")
                .antMatchers("/department/**").hasRole(ADMIN)
                .antMatchers("/storelocation/**").hasRole(ADMIN)
                .antMatchers("/revenue/daily/entry").hasAnyRole(ADMIN, MANAGER)
                .antMatchers("/revenue/department/entry").hasAnyRole(ADMIN, MANAGER)
                .antMatchers("/logout").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().csrf().disable();
    }

}
