package com.example.ubfactory.service;

import com.example.ubfactory.objects.*;

public interface CartService {
    CartResponse getCartItems(Integer customerId);
    CartItemResponse addCartItem(Integer customerId, AddCartItemRequest request);
    CartItemResponse updateCartItemQuantity(Integer customerId, Integer itemId, UpdateCartItemRequest request);
    void deleteCartItem(Integer customerId, Integer itemId);
}
