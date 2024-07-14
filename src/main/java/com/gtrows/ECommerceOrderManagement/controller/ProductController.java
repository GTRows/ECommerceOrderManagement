package com.gtrows.ECommerceOrderManagement.controller;

import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.ErrorResponse;
import com.gtrows.ECommerceOrderManagement.model.Product;
import com.gtrows.ECommerceOrderManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController extends GenericController<Product> {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        super(productService);
        this.productService = productService;
    }

    // @GetMapping
    // The GetAllProduct service is inherited from

    // @GetMapping("/{id}")
    // The GetProduct service is inherited from

    // @PostMapping
    // The CreateProduct service is inherited from the GenericController

    // @PutMapping("/{id}")
    // The UpdateProduct service is inherited from the GenericController

    // @DeleteMapping("/{id}")
    // The DeleteProduct service is inherited from the GenericController


    @Override
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Product product){
        product.setId(id);
        Product updatedProduct = productService.updateProduct(product);
        if (updatedProduct == null) {
            return new ErrorResponse("Product not found for id: " + product.getId()).toResponseEntity();
        }
        return ResponseEntity.ok(updatedProduct);
    }
}