package ru.netology.safeapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class AppSecure extends WebSecurityConfigurerAdapter {

    private final String[] userNames = new String[] {"Andrey", "Alexey", "Igor"};
    private final String[] passes = new String[]{"qwerty1", "qwerty12", "qwerty123"};
    private final String[] userRoles = new String[]{"READ", "WRITE", "DELETE"};
    private final int userReader = 0;
    private final int userWriter = 1;
    private final int userDeleter = 2;

    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        managerBuilder.inMemoryAuthentication()
                        .withUser(userNames[userReader])
                        .password(passwordEncoder.encode(passes[userReader]))
                        .roles(userRoles[userReader])
                       .and()
                        .withUser(userNames[userWriter])
                        .password(passwordEncoder.encode(passes[userWriter]))
                        .roles(userRoles[userWriter])
                       .and()
                        .withUser(userNames[userDeleter])
                        .password(passwordEncoder.encode(passes[userDeleter]))
                        .roles(userRoles[userDeleter]);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }
}
