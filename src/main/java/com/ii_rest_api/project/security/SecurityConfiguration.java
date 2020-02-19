package com.ii_rest_api.project.security;

import com.ii_rest_api.project.db.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private UserPrincipalDetailsService userPrincipalDetailsService;
    private UserRepository userRepository;


    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService, UserRepository userRepository) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    //authorized requests
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                //WITHOUT JWT VERSION
//                //order of antMatchers is important
//                .authorizeRequests()
//                .antMatchers("/index.html").permitAll()
//                .antMatchers("/profile/**").authenticated()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/management/**").hasAnyRole("ADMIN", "MANAGER")
//                //.antMatchers("/api/public/test3").authenticated()
//                // .antMatchers("/api/public/**").authenticated() //tak mozna zabezpiczyc caly rest controller
//                .antMatchers("/api/public/test1").hasAuthority("ACCESS_TEST1")
//                .antMatchers("/api/public/test2").hasAuthority("ACCESS_TEST2")
//                .antMatchers("/api/public/users").hasRole("ADMIN")
//                .and()
//                //.httpBasic();
//                .formLogin().loginPage("/login").permitAll()
//                .and()
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");


                //JWT VERSION
                .csrf().disable() // Cross-site request forgery
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //add jwt filter (1. authentication fill, 2. authorization
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepository))
                .authorizeRequests()
                //configure access rules
                .antMatchers("/register/user").permitAll()
                .antMatchers("/management/teams").hasAuthority("ACCESS_TEAM")
                .antMatchers("/login").permitAll()
                .antMatchers("/user/*").hasRole("USER")
                .antMatchers("/api/public/management").hasRole("MANAGER")
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/admin/user/{user_id}").hasRole("ADMIN");

    }


    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public TokenStore tokenStore(){
//        return new InMemoryTokenStore();
//    }
//    public InMemoryTokenStore tokenStore() {
//        return new InMemoryTokenStore();
//    }


}
