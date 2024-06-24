package com.xvanop01.isregatta.base.config;

import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

import com.xvanop01.isregatta.user.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * WebSecurityConfig
 * Definuje pristupy ku zdrojom a zabezpecenie aplikacie
 * @author 2024 Peter Vano
 */
@Configuration
@Slf4j
public class WebSecurityConfig {

    /**
     * Hodnota sa doplni podla profilu (application-*.yml)
     */
    @Value("${tunel-url}")
    private String tunelUrl;

    private final UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Definuje povolene a zabezpecene url, registraciu, prihlasovanie, odhlasovanie
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/index.html").permitAll() // povolenie pristupu k FE v monolitnej verzii
                .and()
                .authorizeHttpRequests()
                .requestMatchers(regexMatcher("/.*\\.js")).permitAll() // povolenie pristupu k FE v monolitnej verzii
                .and()
                .authorizeHttpRequests()
                .requestMatchers(regexMatcher("/.*\\.css")).permitAll() // povolenie pristupu k FE v monolitnej verzii
                .and()
                .authorizeHttpRequests()
                .requestMatchers(regexMatcher("/.*\\.ico")).permitAll() // povolenie pristupu k FE v monolitnej verzii
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/register").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/login").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/user").permitAll()
                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(tunelUrl + "/login")
                .loginProcessingUrl("/authenticate")
                .defaultSuccessUrl(tunelUrl + "/", true)
                .failureHandler(getAuthenticationFailureHandler())
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(getLogoutSuccessHandler())
                .logoutSuccessUrl(tunelUrl + "/login")
                .permitAll();
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return this.userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    private AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect(tunelUrl + "/login?error=" + exception.getMessage());
        };
    }

    private LogoutSuccessHandler getLogoutSuccessHandler() {
        return (request, response, authentication) -> response.setStatus(HttpStatus.OK.value());
    }
}
