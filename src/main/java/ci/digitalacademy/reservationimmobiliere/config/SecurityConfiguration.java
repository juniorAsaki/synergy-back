package ci.digitalacademy.reservationimmobiliere.config;

import ci.digitalacademy.reservationimmobiliere.security.AuthorityConstants;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers("/api/v1/customers/**").hasAuthority(AuthorityConstants.ROLE_CUSTOMER)
                        .requestMatchers("/api/v1/reviews").hasAnyAuthority(AuthorityConstants.ROLE_OWNER, AuthorityConstants.ROLE_CUSTOMER)
                        .requestMatchers("/api/v1/other/**").permitAll()
                        .requestMatchers("/api/v1/authenticate").permitAll()
                        .requestMatchers("/api/v1/users/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/v1/public/**").permitAll()
                        .requestMatchers("/api/v1/owners/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);// Utilisé pour les APIs sécurisées via JWT

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

