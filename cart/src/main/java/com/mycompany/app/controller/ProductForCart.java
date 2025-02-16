package com.mycompany.app.controller;

import com.mycompany.app.model.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "spring-cloud-gateway")
public interface ProductForCart {

    @GetMapping("api/v1/products/{productId}")
    ProductResponse getProduct(@PathVariable(name= "productId") Integer productId , @RequestHeader("Authorization") String token);
}
