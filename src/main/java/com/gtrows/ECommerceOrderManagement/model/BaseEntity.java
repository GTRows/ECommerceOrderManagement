package com.gtrows.ECommerceOrderManagement.model;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public abstract class BaseEntity {
    @Id
    @Setter
    private String id;

    BaseEntity() {
        this.id = UUID.randomUUID().toString();
    }
}
