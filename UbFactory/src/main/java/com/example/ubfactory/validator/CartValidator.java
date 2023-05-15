package com.example.ubfactory.validator;

import com.example.ubfactory.entities.Cart;
import com.example.ubfactory.exception.BusinessException;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.stereotype.Component;

@Component
public class CartValidator {

    public void validateCartItemExistence(Cart cart, Integer itemId) throws BusinessException {
        boolean exists = cart.getCartItems().stream()
                .anyMatch(item -> item.getId().equals(itemId));

        if (!exists) {
            throw new BusinessException(ResponseConstants.CART_ITEM_NOT_FOUND);
        }
    }
}
