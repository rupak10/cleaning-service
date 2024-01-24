package com.app.util;

import com.app.dto.LoginRequest;
import com.app.dto.SignupRequest;

public class RequestValidator {

    public static Boolean isSignUpRequestValid(SignupRequest signupRequest) {
        if(signupRequest == null) {
            return false;
        }
        return CommonUtil.isValueNotNullAndEmpty(signupRequest.getFirstName())
                && CommonUtil.isValueNotNullAndEmpty(signupRequest.getLastName())
                && CommonUtil.isValueNotNullAndEmpty(signupRequest.getEmail())
                && CommonUtil.isValueNotNullAndEmpty(signupRequest.getPassword())
                && CommonUtil.isValueNotNullAndEmpty(signupRequest.getGender())
                && CommonUtil.isValueNotNullAndEmpty(signupRequest.getRegistrationType());
    }

    public static Boolean isLoginUpRequestValid(LoginRequest loginRequest) {
        if(loginRequest == null) {
            return false;
        }
        return CommonUtil.isValueNotNullAndEmpty(loginRequest.getEmail())
                && CommonUtil.isValueNotNullAndEmpty(loginRequest.getPassword());
    }

}
