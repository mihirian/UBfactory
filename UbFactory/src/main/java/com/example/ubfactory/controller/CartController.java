package com.example.ubfactory.controller;

import com.example.ubfactory.objects.*;
import com.example.ubfactory.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;


    @GetMapping("/{customerId}")
    public ResponseEntity<CartResponse> getCartItems(@PathVariable Integer customerId) {
        CartResponse response = cartService.getCartItems(customerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{customerId}/items")
    public ResponseEntity<CartItemResponse> addCartItem(
            @PathVariable Integer customerId,
            @Valid @RequestBody AddCartItemRequest request) {
        CartItemResponse response = cartService.addCartItem(customerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{customerId}/items/{itemId}")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(
            @PathVariable Integer customerId,
            @PathVariable Integer itemId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        CartItemResponse response = cartService.updateCartItemQuantity(customerId, itemId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{customerId}/items/{itemId}")
    public ResponseEntity<Void> deleteCartItem(
            @PathVariable Integer customerId,
            @PathVariable Integer itemId) {
        cartService.deleteCartItem(customerId, itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{customerId}/cart")
    public ResponseEntity<Object> getCartItemsWithShipping(@PathVariable Integer customerId) {
        try {
            CartItemPriceResponse response = cartService.getCartItemsWithShipping(customerId);
             return GenricResponse.genricResponse("Success", HttpStatus.CREATED, response);
        }
        catch (Exception e){
            return GenricResponse.genricResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
