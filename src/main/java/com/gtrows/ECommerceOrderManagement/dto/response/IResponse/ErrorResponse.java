package com.gtrows.ECommerceOrderManagement.dto.response.IResponse;

import com.gtrows.ECommerceOrderManagement.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse implements IResponse {
    private String message;
    private String timestamp;
    private HttpStatus status;

    public ErrorResponse(String message) {
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        System.out.println("Error: " + message);
    }

    public ErrorResponse(String message, HttpStatus status) {
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.message = message;
        this.status = status;
        System.out.println("Error: " + message);
    }

    public ErrorResponse(CustomException e) {
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.message = e.getMessage();
        this.status = e.getStatus();
        System.out.println("Error: " + e.getMessage());
    }

    public ErrorResponse() {
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.message = "Unknown error";
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        System.out.println("Error: Unknown error");
    }

    public ResponseEntity<?> toResponseEntity() {
        return new ResponseEntity<>(this, this.status);
    }
}

