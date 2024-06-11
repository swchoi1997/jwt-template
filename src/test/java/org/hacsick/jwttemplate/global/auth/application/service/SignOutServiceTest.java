package org.hacsick.jwttemplate.global.auth.application.service;

import static org.junit.jupiter.api.Assertions.*;

import org.hacsick.jwttemplate.global.auth.application.port.in.SignOutUseCase;
import org.hacsick.jwttemplate.global.user.application.port.out.LoadUserPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@SpringBootTest
class SignOutServiceTest {

    @Autowired
    SignOutUseCase signOutUseCase;

    @Autowired
    LoadUserPort loadUserPort;

    @Test
    void signOutTest() {
        String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjFAZXhhbXBsZS5jb20iLCJpYXQiOjE3MTU5NTk1NTIsImV4cCI6MTcxNjA0NTk1Mn0.rU8kZI5WdoiTublZDxo2WTQ5vIJUNZjtjc56Xnz8vfVtdLHf9EiRpqOdVpnyuktEgbnFSgmKhK4tjiAzNs0m2g";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization","Bearer " + accessToken);

        signOutUseCase.signOut(request, loadUserPort.findUserFromEmail("admin1@example.com").get());




    }

}