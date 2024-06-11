package org.hacsick.jwttemplate.global.user.adapter.in.web;

import org.hacsick.jwttemplate.global.common.LoginUser;
import org.hacsick.jwttemplate.global.common.PersistenceAdapter;
import org.hacsick.jwttemplate.global.user.application.port.in.WithdrawUserUseCase;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PersistenceAdapter
public class WithdrawUserController {

    private final WithdrawUserUseCase withdrawUserUseCase;

    public WithdrawUserController(final WithdrawUserUseCase withdrawUserUseCase) {
        this.withdrawUserUseCase = withdrawUserUseCase;
    }

    @PostMapping("/api/v1/users")
    public ResponseEntity<?> withdraw(@LoginUser User user) {
        this.withdrawUserUseCase.withdraw(user);
        return ResponseEntity.ok().build();
    }

}
