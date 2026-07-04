package org.example.config;

import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

         http.authorizeRequests()
                 .antMatchers("/admin/**").hasRole("admin")
                 .antMatchers("/db/**").hasRole("dba")
                 .antMatchers("/user/**").hasRole("user")
                 .anyRequest().authenticated()
                 .and()
                 .formLogin()
                 .loginProcessingUrl("/login").permitAll()
                 .and()
                 .logout()
                 .logoutUrl("/logout")
                 .clearAuthentication(true)
                 .invalidateHttpSession(true)
                 .addLogoutHandler(new LogoutHandler() {
                     @Override
                     public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

                     }
                 })
                 .logoutSuccessHandler(new LogoutSuccessHandler() {
                     @Override
                     public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                         httpServletResponse.sendRedirect("/login");
                     }
                 })
                 .and()
                 .csrf().disable();
    }
}
