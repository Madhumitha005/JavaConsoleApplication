package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

    @Test
    void signupNullUserTest(){

        AuthService service = new AuthService(null,null);

        assertThrows(
            NullPointerException.class,
            () -> service.signup(null)
        );

    }

}