package com.hareshnayak.auth_service.models;
import lombok.Data;

@Data
public class BaseResponse {
    private String message;
    private String success;
    private Object data;

    public BaseResponse(String message, String success) {
        this.message = message;
        this.success = success;
    }

    public BaseResponse(String message, String success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }
}
