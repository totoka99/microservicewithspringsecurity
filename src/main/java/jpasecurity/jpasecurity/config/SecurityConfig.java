package jpasecurity.jpasecurity.config;

import jpasecurity.jpasecurity.service.JpaUserDetailsManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JpaUserDetailsManager jpaUserDetailsManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/user/**").hasRole("ADMIN")
                                .requestMatchers("/api/example/**").hasRole("USER")
                                .requestMatchers("/api/registration").permitAll()
                                .requestMatchers("/login", "/logout").permitAll()
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .userDetailsService(jpaUserDetailsManager)
                .httpBasic()
                .and()
                .formLogin()
                .and()
                .build();
    }
}
