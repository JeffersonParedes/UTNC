package Grupo_5.UTNC.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Habilita @PreAuthorize y @PostAuthorize
public class WebSecurityConfig {

    private final JWTAuthentication jwtAuthentication;
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(JWTAuthentication jwtAuthentication, UserDetailsService userDetailsService) {
        this.jwtAuthentication = jwtAuthentication;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {
                })
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/", "/portal", "/portal/home", "/static/**", "/css/**", "/js/**",
                                "/images/**")
                        .permitAll()
                        .requestMatchers("/portal/acceso/**").permitAll() // Páginas de login y registro del portal
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers("/carreras/**", "/admision/**").permitAll()

                        // Páginas informativas públicas
                        .requestMatchers("/nosotros/**", "/portal/nosotros/**").permitAll()
                        .requestMatchers("/campus/**", "/portal/campus/**").permitAll()
                        .requestMatchers("/contacto/**", "/contactos/**", "/portal/contacto/**", "/portal/contactos/**")
                        .permitAll()

                        // Endpoints que requieren autenticación básica
                        .requestMatchers("/api/auth/me", "/api/auth/logout").authenticated()

                        // TODO: Endpoints con roles específicos (cuando implementes roles completos)
                        // .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // .requestMatchers("/api/docente/**").hasRole("DOCENTE")
                        // .requestMatchers("/api/estudiante/**").hasRole("ESTUDIANTE")

                        // Por ahora, todo lo demás requiere autenticación
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthentication, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // CORS configuration moved to WebConfig.java to avoid bean conflicts

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuración del AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configuración del AuthenticationProvider que usa nuestro UserDetailsService
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
