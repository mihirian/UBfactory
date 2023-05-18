package com.example.ubfactory.config;

import com.example.ubfactory.service.serviceimpl.LoginServiceImp;
import com.example.ubfactory.validator.JwtAuthenticationFilter;
import com.example.ubfactory.validator.JwtAuthenticationFilterEntrypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)// is used for role permission
public class SecurityConfig extends WebSecurityConfigurerAdapter {
            public static final String []PUBLIC_URLS=
                {
                        "/v3/api-docs",
                        "/v2/api-docs",
                        "/swaggeimplements UserDetailsService r_resources/**",
                        "/swagger_ui/**",
                        "/webjar/**"
                };
    @Autowired
    private LoginServiceImp userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtautheticatorfilter;
    @Autowired
    private JwtAuthenticationFilterEntrypoint jwtAuthenticationFilterEntryPoint;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/login", "/customer/registration").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST).permitAll()
                .antMatchers(PUBLIC_URLS).permitAll()
                .antMatchers(HttpMethod.PUT).permitAll()
                .anyRequest().authenticated()
                // Add security rules for specific APIs here
                .antMatchers("/api/secure/**").authenticated()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll() // Allow all other requests without authentication
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationFilterEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtautheticatorfilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

}

