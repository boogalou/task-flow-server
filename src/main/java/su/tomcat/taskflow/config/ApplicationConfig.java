package su.tomcat.taskflow.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import su.tomcat.taskflow.web.security.JwtTokenFilter;
import su.tomcat.taskflow.web.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationConfig {

  private final JwtTokenProvider jwTokenProvider;
  private final ApplicationContext applicationContext;


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(configurer ->
            configurer.authenticationEntryPoint(
                (req, res, exception) -> {
                  res.setStatus(HttpStatus.UNAUTHORIZED.value());
                  res.getWriter().write("Unauthorized");
                })
                .accessDeniedHandler((req, res, exception) -> {
                  res.setStatus(HttpStatus.FORBIDDEN.value());
                  res.getWriter().write("Access denied");
                })
        )
        .authorizeHttpRequests(configurer ->
            configurer.requestMatchers("/api/v1/auth**").permitAll()
                .requestMatchers("swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .anyRequest()
                .authenticated())
        .anonymous(AbstractHttpConfigurer::disable)
        .addFilterBefore(new JwtTokenFilter(jwTokenProvider), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
