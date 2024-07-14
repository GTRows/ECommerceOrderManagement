package com.gtrows.ECommerceOrderManagement.controller;

import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.AccessResponse;
import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.ErrorResponse;
import com.gtrows.ECommerceOrderManagement.model.BaseEntity;
import com.gtrows.ECommerceOrderManagement.service.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class GenericController<T extends BaseEntity> {

    private final GenericService<T> service;

    @GetMapping
    public List<T> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<T> entityOptional = service.getById(id);
        if (entityOptional.isEmpty()) {
            return new ErrorResponse("Entity not found", HttpStatus.NOT_FOUND).toResponseEntity();
        }
        return ResponseEntity.ok(entityOptional.get());
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        T savedEntity = service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody T entity) {
        Optional<T> existingEntityOptional = service.getById(id);
        if (existingEntityOptional.isPresent()) {
            T existingEntity = existingEntityOptional.get();
            entity.setId(existingEntity.getId());
            T updatedEntity = service.save(entity);
            return ResponseEntity.ok(updatedEntity);
        } else {
            return new ErrorResponse("Entity not found", HttpStatus.NOT_FOUND).toResponseEntity();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Optional<T> existingEntityOptional = service.getById(id);
        if (existingEntityOptional.isPresent()) {
            service.delete(id);
            return new AccessResponse("Entity deleted successfully").toResponseEntity();
        } else {
            return new ErrorResponse("Entity not found", HttpStatus.NOT_FOUND).toResponseEntity();
        }
    }
}

