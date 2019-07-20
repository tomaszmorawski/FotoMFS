package com.example.fotomfs.Security;

import com.example.fotomfs.Handlers.CustomLoginSuccessHandler;
import com.example.fotomfs.Handlers.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final
    PasswordEncoder passwordEncoder;

    private final
    UserDetailsService userDetailsService;

    private final
    CustomLogoutSuccessHandler customLogoutSuccessHandler;

    private final
    CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,
                                       UserDetailsService userDetailsService,
                                       CustomLogoutSuccessHandler customLogoutSuccessHandler,
                                       CustomLoginSuccessHandler customLoginSuccessHandler) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/").hasRole("USER")
                .antMatchers("/login",/*"/init",*/ "/webjars/**", "/error**").permitAll()
                .and().formLogin().successHandler(customLoginSuccessHandler).loginPage("/loginHome").loginProcessingUrl("/login").usernameParameter("login")
                .and().logout().logoutSuccessHandler(customLogoutSuccessHandler).permitAll()
                .and().csrf().disable();
    }

}
