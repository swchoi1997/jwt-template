package org.hacsick.jwttemplate.global.auth.adapter.in.web.validator;

import static org.hacsick.jwttemplate.global.utils.time.CustomAssert.ifFalse;

import lombok.RequiredArgsConstructor;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.SignUpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpRequestPassword implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpRequest.class);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        SignUpRequest req = (SignUpRequest) target;
        this.checkPassword(req, errors);

    }

    private void checkPassword(final SignUpRequest req, final Errors errors) {
        ifFalse(req.getPassword().equals(req.getPasswordConfirm()),
                () -> errors.rejectValue("passwordConfirm", "Invalid passwordConfirm",
                        new Object[]{req.getPasswordConfirm()}, "비밀번호가 일치하지 않습니다.")
        );
    }
}
