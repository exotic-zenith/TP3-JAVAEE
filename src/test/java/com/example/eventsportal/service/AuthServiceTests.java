package com.example.eventsportal.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AuthServiceTests {

    @Test
    void strongPasswordValidationWorks() {
        assertTrue(AuthService.isStrongPassword("Aa123456!"));
        assertFalse(AuthService.isStrongPassword("weakpass"));
    }
}

