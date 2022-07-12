package com.clothes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    @Autowired(required = true)
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

	}
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/order/checkout").hasAnyRole("ADMIN", "USER","PRODUCT","ORDER")
                .antMatchers("/admin/home/**")
                .hasAnyRole("ADMIN","ORDER","PRODUCT")
                .antMatchers("/admin/product/**",
                                        "/admin/size/**",
                                        "/admin/color/**",
                                        "/admin/category/**")
                .hasAnyRole("ADMIN","PRODUCT")



                .antMatchers("/admin/order/**")
                .hasAnyRole("ADMIN","ORDER")
                .antMatchers("/admin/product/delete",
                                        "/admin/order/delete",
                                        "/admin/account/**","/admin/blog/**",
                                        "/admin/contact/**","/admin/about/**")
                .hasRole("ADMIN")
                .anyRequest().permitAll();
        http.exceptionHandling()
                .accessDeniedPage("/403");

        http.formLogin()
                .loginPage("/account/login")
                .loginProcessingUrl("/spring/login")
                .defaultSuccessUrl("/")
                .failureUrl("/account/login/failure");
        http.rememberMe()
                .tokenValiditySeconds(5 * 24 * 60 * 60);
        http.logout()
                .logoutUrl("/spring/logout")
                .logoutSuccessUrl("/account/login")
                .addLogoutHandler(new SecurityContextLogoutHandler());

        http.oauth2Login()
                .loginPage("/account/login")
                .defaultSuccessUrl("/oauth2/login/success", true)
                .failureUrl("/account/login")
                .authorizationEndpoint()
                .baseUri("/oauth2");
    }


//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**","/local.json");
//    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}