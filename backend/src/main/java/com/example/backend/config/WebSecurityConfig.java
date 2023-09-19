package com.example.backend.config;

import com.example.backend.security.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Configuración para excluir end-points
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure ( HttpSecurity http ) throws Exception {
        http.cors();
        http.csrf()
                .disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/clients/register")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/clients/profile/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/clients/verifyRegisteredAccount")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/clients/administrators/**")
                .permitAll()
                //.antMatchers(
                //        "/api/v1/employees/**",
                //        "/api/v1/clients/**"
                //).hasAnyAuthority("ADMIN")//
                //.antMatchers(
                //        "/api/v1/clients/**"
                //).hasAnyAuthority("EMPLOYEE", "ADMIN")
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
     * Configuración para excluir paginas
     *
     * @param web
     * @throws Exception
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
