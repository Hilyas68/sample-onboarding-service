package com.oneworldaccuracy.onboardingservice.vo.response;


import com.oneworldaccuracy.onboardingservice.util.Constants;

public class ErrorResponse extends ServiceResponse {

    public ErrorResponse(String message){
        super(false, message, null);
    }

    public ErrorResponse(){
        super(false, Constants.ERROR_PROCESSING, null);
    }
}
