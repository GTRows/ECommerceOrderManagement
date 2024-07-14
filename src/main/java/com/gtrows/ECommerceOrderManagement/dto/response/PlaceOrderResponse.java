package com.gtrows.ECommerceOrderManagement.dto.response;

import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.IResponse;
import com.gtrows.ECommerceOrderManagement.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderResponse implements IResponse {
    private String orderCode;
    private String message;
    private String timestamp;
    private HttpStatus status = HttpStatus.ACCEPTED;

    public PlaceOrderResponse(Order order) {
        this.orderCode = order.getId();
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.message = "Order placed successfully";
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public ResponseEntity<?> toResponseEntity() {
        return new ResponseEntity<>(this, this.status);
    }

    @Override
    public String toString() {
        return "PlaceOrderResponse{" +
                "orderCode='" + orderCode + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", status=" + status +
                '}';
    }
}
