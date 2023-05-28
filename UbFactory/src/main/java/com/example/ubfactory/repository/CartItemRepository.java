package com.example.ubfactory.repository;

import com.example.ubfactory.entities.Cart;
import com.example.ubfactory.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartId(Integer id);

    List<CartItem> findAllByCart(Cart cart);
}
