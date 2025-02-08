package com.mycompany.app.controller;


import com.mycompany.app.record.ProductRequest;
import com.mycompany.app.record.ProductResponse;
import com.mycompany.app.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> addProducts(@RequestBody ProductRequest productRequest){
        productService.saveProduct(productRequest);
        return ResponseEntity.ok("Product added successfully");
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){
        List<ProductResponse> productsList = productService.getAllProducts();
        return ResponseEntity.ok(productsList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable(name = "id") Integer id, @RequestParam(name = "quantity") Integer quantity){
        String productName = productService.updateProductQuantity(id, quantity);
        return ResponseEntity.ok("Product" + productName +  "updated successfully");
    }

    @GetMapping("/{productId}")
    public ProductResponse getProduct(@PathVariable(name = "productId") Integer productId){
        return productService.getProductById(productId);
    }
}
