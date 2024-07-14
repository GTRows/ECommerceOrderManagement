package com.gtrows.ECommerceOrderManagement.dto.response.IResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IResponse {
    HttpStatus getStatus();

    ResponseEntity<?> toResponseEntity();
}


