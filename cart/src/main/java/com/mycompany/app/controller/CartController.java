package com.mycompany.app.controller;

import com.mycompany.app.entity.Cart;
import com.mycompany.app.model.CartResponse;
import com.mycompany.app.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/carts")
@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addToCart(@RequestHeader("Authorization") String authorization,
                                          @RequestParam("userId") Integer userId,
                                          @RequestParam("productId") Integer productId) {
        cartService.addToCart(authorization, userId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCartInProgressForUser(@RequestHeader("Authorization") String authorization,
                                                                 @PathVariable("userId") Integer userId) {
        CartResponse cartResponse = cartService.getCartByUserIdInProgress(authorization, userId);
        return ResponseEntity.ok(cartResponse);
    }
}
