package org.hacsick.jwttemplate.global.user.adapter.in.web;

import org.hacsick.jwttemplate.global.common.LoginUser;
import org.hacsick.jwttemplate.global.user.adapter.in.web.dto.ChangeUserInfoRequest;
import org.hacsick.jwttemplate.global.user.application.port.in.ChangeUserInfoUseCase;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class ChangeUserInfoController {

    private final ChangeUserInfoUseCase changeUserInfoUseCase;

    public ChangeUserInfoController(final ChangeUserInfoUseCase changeUserInfoUseCase) {
        this.changeUserInfoUseCase = changeUserInfoUseCase;
    }

    @PutMapping
    public ResponseEntity<?> changeUserInfo(@LoginUser User user,
                                            @RequestBody ChangeUserInfoRequest request) {
        this.changeUserInfoUseCase.changeUserInfo(user, request.toCommand());
        return ResponseEntity.ok().build();
    }
}
