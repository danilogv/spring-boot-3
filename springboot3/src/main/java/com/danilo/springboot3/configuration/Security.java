package com.danilo.springboot3.configuration;

import com.danilo.springboot3.design_pattern.FacadeService;
import com.danilo.springboot3.enumeration.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Objects;

@Configuration
public class Security {

    @Autowired
    private FacadeService service;

    @Autowired
    private Jwt jwt;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.service.user);
        authProvider.setPasswordEncoder(this.passwordEncoder());
        return authProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("*"));
        cors.setAllowCredentials(true);
        cors.setAllowedHeaders(List.of("*"));
        cors.setAllowedMethods(List.of("HEAD","GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cors.setExposedHeaders(List.of("X-Auth-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
        UrlBasedCorsConfigurationSource configuration = new UrlBasedCorsConfigurationSource();
        configuration.registerCorsConfiguration("/**",cors);
        return configuration;
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthority() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String enviroment = System.getenv("ENVIROMENT");

        if (Objects.nonNull(enviroment) && enviroment.equalsIgnoreCase("DEV")) {
            http
                .csrf(AbstractHttpConfigurer::disable)
                .cors((security) -> security.configurationSource(this.corsConfiguration()))
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/**").permitAll());
        }
        else {
            Authentication authentication = new Authentication(this.service.user,this.jwt);

            http
                .csrf(AbstractHttpConfigurer::disable)
                .cors((security) -> security.configurationSource(this.corsConfiguration()))
                .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/usuario/**").permitAll()
                    .requestMatchers("/pessoa","/carro").authenticated()
                    .requestMatchers(HttpMethod.GET,"/pessoa/**").hasAnyRole(Permission.USER.name(),Permission.ADMIN.name())
                    .requestMatchers(HttpMethod.POST,"/pessoa").hasAnyRole(Permission.USER.name(),Permission.ADMIN.name())
                    .requestMatchers(HttpMethod.PUT,"/pessoa").hasAnyRole(Permission.USER.name(),Permission.ADMIN.name())
                    .requestMatchers(HttpMethod.DELETE,"/pessoa/**").hasRole(Permission.ADMIN.name())
                    .requestMatchers(HttpMethod.GET,"/carro/**").hasAnyRole(Permission.USER.name(),Permission.ADMIN.name())
                    .requestMatchers(HttpMethod.POST,"/carro").hasAnyRole(Permission.USER.name(),Permission.ADMIN.name())
                    .requestMatchers(HttpMethod.PUT,"/carro").hasAnyRole(Permission.USER.name(),Permission.ADMIN.name())
                    .requestMatchers(HttpMethod.DELETE,"/carro/**").hasRole(Permission.ADMIN.name())
                )
                .authenticationProvider(this.authenticationProvider())
                .addFilterBefore(authentication,UsernamePasswordAuthenticationFilter.class)
            ;
        }

        return http.build();
    }

}
