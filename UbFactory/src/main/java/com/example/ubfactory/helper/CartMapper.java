package com.example.ubfactory.helper;

import com.example.ubfactory.entities.Cart;
import com.example.ubfactory.entities.CartItem;
import com.example.ubfactory.entities.Product;
import com.example.ubfactory.objects.AddCartItemRequest;
import com.example.ubfactory.objects.CartItemResponse;
import com.example.ubfactory.objects.CartResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {
    public static CartResponse toCartResponse(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getId());

        List<CartItemResponse> cartItemResponses = cart.getCartItems().stream()
                .map(CartMapper::toCartItemResponse)
                .collect(Collectors.toList());

        cartResponse.setCartItems(cartItemResponses);

        return cartResponse;
    }

    public static CartItem toCartItem(AddCartItemRequest request, Cart cart, Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());

        return cartItem;
    }

    public static CartItemResponse toCartItemResponse(CartItem cartItem) {
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setItemId(cartItem.getId());
        cartItemResponse.setProductName(cartItem.getProduct().getName());
        cartItemResponse.setPrice(cartItem.getProduct().getPrice());
        cartItemResponse.setQuantity(cartItem.getQuantity());

        return cartItemResponse;
    }
}
