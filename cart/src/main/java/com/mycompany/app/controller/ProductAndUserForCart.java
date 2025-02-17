package com.mycompany.app.controller;

import com.mycompany.app.model.ProductResponse;
import com.mycompany.app.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "spring-cloud-gateway")
public interface ProductAndUserForCart {

    @GetMapping("api/v1/auth/{userId}")
    UserResponse getUser(@PathVariable(name = "userId") Integer userId);

    @GetMapping("api/v1/products/{productId}")
    ProductResponse getProduct(@PathVariable(name= "productId") Integer productId , @RequestHeader("Authorization") String token);
}
