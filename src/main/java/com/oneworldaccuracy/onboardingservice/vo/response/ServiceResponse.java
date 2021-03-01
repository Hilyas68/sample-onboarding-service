package com.oneworldaccuracy.onboardingservice.vo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ServiceResponse {
    private Date timeStamp = new Date();
    private Boolean status;
    private String message;
    private Object data;

    public ServiceResponse() {
    }

    public ServiceResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ServiceResponse(Boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
