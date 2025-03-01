package com.mycompany.app.controller;

import com.mycompany.app.entity.Cart;
import com.mycompany.app.entity.Order;
import com.mycompany.app.entity.PaymentMethod;
import com.mycompany.app.model.CartResponse;
import com.mycompany.app.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/carts")
@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addToCart(@RequestHeader("Authorization") String authorization,
                                          @RequestParam("productId") Integer productId) {
        cartService.addToCart(authorization, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/Inprogress")
    public ResponseEntity<CartResponse> getCartInProgressForUser(@RequestHeader("Authorization") String authorization) {
        CartResponse cartResponse = cartService.getCartByUserIdInProgress(authorization);
        return ResponseEntity.ok(cartResponse);
    }

    @PostMapping("/{cartId}")
    public ResponseEntity<String> createOrderFromCart(@RequestHeader("Authorization") String authorization, @PathVariable("cartId") Integer cartId,
                                                      @RequestParam("paymentMethod") PaymentMethod paymentMethod){
        cartService.createOrder(authorization, cartId, paymentMethod);
        return ResponseEntity.ok("Order created");
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders  = cartService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/users")
    public ResponseEntity<List<Order>> getOrdersByUserId(@RequestHeader("Authorization") String authorization) {
        List<Order> orderResponses = cartService.getOrdersForUser(authorization);
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/orders/products/{productId}")
    public ResponseEntity<List<Order>> getOrdersByProductId(@PathVariable("productId") Integer productId){
        List<Order> orders = cartService.getOrdersForProduct(productId);
        return ResponseEntity.ok(orders);
    }

}
