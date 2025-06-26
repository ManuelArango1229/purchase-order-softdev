package com.softdev.purchase_order.infrastucture.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.JwtDecoder;


import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;


class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() throws Exception {
        securityConfig = new SecurityConfig();

        // Setear valor a campo privado jwtSecretKey usando Reflection
        Field field = SecurityConfig.class.getDeclaredField("jwtSecretKey");
        field.setAccessible(true);
        field.set(securityConfig, "dGVzdGtleXRlc3RrZXl0ZXN0a2V5"); // Base64 de 'testkeytestkeytestkey'
    }

    @Test
    void testJwtDecoderNotNull() {
        JwtDecoder decoder = securityConfig.jwtDecoder();
        assertNotNull(decoder);
    }

    @Test
    void testJwtAuthenticationConverterNotNull() {
        assertNotNull(securityConfig.jwtAuthenticationConverter());
    }

}
