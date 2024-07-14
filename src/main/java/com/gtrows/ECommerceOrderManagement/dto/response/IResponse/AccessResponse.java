package com.gtrows.ECommerceOrderManagement.dto.response.IResponse;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

@Getter
@Setter
public class AccessResponse implements IResponse {
    private String timestamp;
    private String message;
    private HttpStatus status;

    public AccessResponse(String message, HttpStatus status) {
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.message = message;
        this.status = status;
        System.out.println(message);
    }

    public AccessResponse(String message) {
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.message = message;
        this.status = HttpStatus.OK;
        System.out.println(message);
    }

    public AccessResponse() {
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.message = "Success";
        this.status = HttpStatus.OK;
        System.out.println("Success");
    }

    public ResponseEntity<?> toResponseEntity() {
        return new ResponseEntity<>(this, this.status);
    }
}
