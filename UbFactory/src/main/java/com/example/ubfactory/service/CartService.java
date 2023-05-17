package com.example.ubfactory.service;

import com.example.ubfactory.objects.AddCartItemRequest;
import com.example.ubfactory.objects.CartItemResponse;
import com.example.ubfactory.objects.CartResponse;
import com.example.ubfactory.objects.UpdateCartItemRequest;

public interface CartService {
    CartResponse getCartItems(Integer customerId);

    CartItemResponse addCartItem(Integer customerId, AddCartItemRequest request);

    CartItemResponse updateCartItemQuantity(Integer customerId, Integer itemId, UpdateCartItemRequest request);

    void deleteCartItem(Integer customerId, Integer itemId);
}
