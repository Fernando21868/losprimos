package com.example.backend.config;

import com.example.backend.security.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration of end-points
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configuration to exclude end-points
     * @param http default parameter
     * @throws Exception and exception
     */
    @Override
    protected void configure ( HttpSecurity http ) throws Exception {
        http.cors();
        http.csrf()
                .disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/**/verifyRegisteredAccount")
                .permitAll()
                //.antMatchers(HttpMethod.DELETE, "/api/v1/administrators/**")
                //.permitAll()
                //.antMatchers(HttpMethod.POST, "/api/v1/administrators/register")
                //.permitAll()
                //.antMatchers(HttpMethod.PATCH, "/api/v1/administrators/**")
                //.permitAll()
                //.antMatchers(HttpMethod.GET, "/api/v1/administrators/**")
                //.permitAll()
                .antMatchers(
                        "/api/v1/employees/**",
                        "/api/v1/administrators/**",
                        "/api/v1/doctors/**",
                        "/api/v1/kinesiologists/**",
                        "/api/v1/nurses/**",
                        "/api/v1/nutritionists/**",
                        "/api/v1/psychologists/**"
                ).hasAnyAuthority("ADMIN")
                .antMatchers(
                        "/api/v1/patients/**"
                ).hasAnyAuthority("EMPLOYEE", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/sign-in")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/ping")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/fake")
                .permitAll()

                .anyRequest()
                .permitAll();
    }

    /**
     * Configuration to exclude pages
     *
     * @param web default parameter
     * @throws Exception an exception
     */
    @Override
    public void configure ( WebSecurity web ) throws Exception {
        web.ignoring()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**");

        web.ignoring()
                .antMatchers(
                        "/h2-console/**");
    }
}
