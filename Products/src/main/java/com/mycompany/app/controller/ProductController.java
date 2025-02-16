package com.mycompany.app.controller;


import com.mycompany.app.record.ProductRequest;
import com.mycompany.app.record.ProductResponse;
import com.mycompany.app.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProducts(@RequestPart("product") @Valid ProductRequest productRequest,
                                              @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        productService.saveProduct(productRequest, multipartFile);
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
