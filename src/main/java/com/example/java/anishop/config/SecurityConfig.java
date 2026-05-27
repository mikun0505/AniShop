package com.example.java.anishop.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.example.java.anishop.enums.Permission.ADMIN_CREATE;
import static com.example.java.anishop.enums.Permission.ADMIN_DELETE;
import static com.example.java.anishop.enums.Permission.ADMIN_READ;
import static com.example.java.anishop.enums.Permission.ADMIN_UPDATE;
import static com.example.java.anishop.enums.Role.ADMIN;
import com.example.java.anishop.filter.JwtFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private UserDetailsService userDetails;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.
            csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(Request -> Request
                .requestMatchers("/login","/register").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/shops/**","/api/products/**","/api/animes/**","/api/reviews/**").permitAll()
                .requestMatchers("/api/reviews/**").authenticated()
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/api-docs/**",
                    "/api-docs",
                    "/v3/api-docs/**"
                ).permitAll()
                .requestMatchers("/api/v1/stream/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/admin/**").hasAuthority(ADMIN_CREATE.name())
                .requestMatchers(HttpMethod.DELETE,"/api/admin/**").hasAuthority(ADMIN_DELETE.name())
                .requestMatchers(HttpMethod.PUT,"/api/admin/**").hasAuthority(ADMIN_UPDATE.name())
                .requestMatchers(HttpMethod.GET,"/api/admin/**").hasAuthority(ADMIN_READ.name())
                .requestMatchers("/api/admin/**").hasRole(ADMIN.name())
                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    // @Bean
    // public CorsConfigurationSource corsConfigurationSource(){
    //     CorsConfiguration config=new CorsConfiguration();
    //     config.setAllowedOrigins(List.of("*"));
    //     config.setAllowedMethods(List.of("GET","POST","DELETE","OPTIONS"));
    //     config.setAllowedHeaders(List.of("*"));
    //     UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", config);
    //     return source;
        
    // }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Fix: dùng origin cụ thể thay vì "*" để Bearer token hoạt động đúng
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        // Fix: thêm PUT để ProductAPI.updatedProduct() hoạt động
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetails);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
