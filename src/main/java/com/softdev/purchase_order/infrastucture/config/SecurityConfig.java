package com.softdev.purchase_order.infrastucture.config;


import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


/** Clase para la configuracion de seguridad en la aplicacion. */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /** Clave secreta utilizada para la firma de los tokens JWT. */
    @Value("${jwt.secret}")
    private String jwtSecretKey;


    /**
     * Filtro de seguridad de la aplicación, se hace uso del jwtdecoder definido abajo para decodificar el token
     * de la solicitud y agregarlo al contexto de seguridad de Spring.
     * @param http
     * @param jwtDecoder
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, final JwtDecoder jwtDecoder) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/ordenes/**").hasRole("CLIENTE")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .decoder(jwtDecoder)
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            );

        return http.build();
    }

    /**
     * Decodificador de JWT personalizado.
     * @return JwtDecoder
     */
    @Bean
    public JwtDecoder jwtDecoder() {

        MacAlgorithm algorithm = MacAlgorithm.HS512;

        byte[] keyBytes = Base64.getDecoder().decode(jwtSecretKey);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, algorithm.getName());

        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(algorithm)
                .build();
    }




    /**
     * Creates a JwtAuthenticationConverter bean that customizes the extraction of authorities
     * from a JWT token by defining the claim name and prefix for roles.
     * <p>
     * This converter uses {@link JwtGrantedAuthoritiesConverter} to specify the claim in the
     * JWT that holds role information and the prefix expected for roles. Adjust the claim name
     * and prefix according to the JWT structure used in your application.
     * </p>
     *
     * @return a configured instance of {@link JwtAuthenticationConverter}.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        grantedAuthoritiesConverter.setAuthoritiesClaimName("rol");

        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


}
