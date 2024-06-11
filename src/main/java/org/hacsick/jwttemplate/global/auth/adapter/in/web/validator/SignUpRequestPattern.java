package org.hacsick.jwttemplate.global.auth.adapter.in.web.validator;

import static org.hacsick.jwttemplate.global.utils.time.CustomAssert.ifFalse;

import lombok.RequiredArgsConstructor;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.SignUpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpRequestPattern implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpRequest.class);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        SignUpRequest req = (SignUpRequest) target;
        this.checkPatterns(req, errors);

    }

    private void checkPatterns(final SignUpRequest req, final Errors errors) {
        ifFalse(AuthPatternType.match(AuthPatternType.EMAIL, req.getEmail()),
                () -> errors.rejectValue("email", "Invalid email", "이메일 형식이 아닙니다."));

        ifFalse(AuthPatternType.match(AuthPatternType.NAME, req.getName()),
                () -> errors.rejectValue("name", "Invalid name", "올바른 이름을 입력해주세요."));

        ifFalse(AuthPatternType.match(AuthPatternType.PASSWORD, req.getPassword()),
                () -> errors.rejectValue("password", "Invalid password", "비밀번호는 영문과 숫자 특수문자 조합으로 8 ~ 20자리로 설정해주세요."));

        ifFalse(AuthPatternType.match(AuthPatternType.PHONE_NUMBER, req.getPhoneNumber()),
                () -> errors.rejectValue("phoneNumber", "Invalid phoneNumber", "올바른 휴대폰 형식을 입력해주세요"));
    }
}
