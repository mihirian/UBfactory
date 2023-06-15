package com.example.ubfactory.controller;

import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.service.CartService;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
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
    public Response<CartResponse> getCartItems(@PathVariable Integer customerId) {
        GenericResponse<CartResponse> response = new GenericResponse<>();
        try{
            CartResponse response1 = cartService.getCartItems(customerId);
            return response.createSuccessResponse(response1, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        }catch (Exception e) {
            return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
        }
    }

    @PostMapping("/{customerId}/items")
    public Response<CartItemResponse> addCartItem(@PathVariable Integer customerId, @Valid @RequestBody AddCartItemRequest request) {
        GenericResponse<CartItemResponse> response = new GenericResponse<>();
        try {
            CartItemResponse response1 = cartService.addCartItem(customerId, request);
            return response.createSuccessResponse(response1, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        } catch (BusinessException b) {
            return response.createErrorResponse(b.getErrorCode(), b.getMessage());
        } catch (Exception e) {
            return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
        }

    }

    @PutMapping("/{customerId}/items/{productId}")
    public Response<CartItemResponse> updateCartItemQuantity(@PathVariable Integer customerId, @PathVariable Integer productId, @Valid @RequestBody UpdateCartItemRequest request) {
        GenericResponse<CartItemResponse> response = new GenericResponse<>();
        try{
            CartItemResponse response1 = cartService.updateCartItemQuantity(customerId, productId, request);
            return response.createSuccessResponse(response1, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        }catch (Exception e) {
            return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
        }
    }

    @DeleteMapping("/{customerId}/items/{productId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Integer customerId, @PathVariable Integer productId) {
        cartService.deleteCartItem(customerId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{customerId}/cart")
    public Response<?> getCartItemsWithShipping(@PathVariable Integer customerId) {
        GenericResponse<CartItemPriceResponse> response = new GenericResponse<>();
        try {
            CartItemPriceResponse response1 = cartService.getCartItemsWithShipping(customerId);
            return response.createSuccessResponse(response1, HttpStatus.OK.value(), ResponseConstants.SUCCESS);
        } catch (Exception e) {
            return response.createErrorResponse(401, ResponseConstants.REQUEST_TIME_OUT);
        }
    }

}
