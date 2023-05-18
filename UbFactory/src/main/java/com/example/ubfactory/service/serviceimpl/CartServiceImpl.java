package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Cart;
import com.example.ubfactory.entities.CartItem;
import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.entities.Product;
import com.example.ubfactory.helper.CartMapper;
import com.example.ubfactory.objects.AddCartItemRequest;
import com.example.ubfactory.objects.CartItemResponse;
import com.example.ubfactory.objects.CartResponse;
import com.example.ubfactory.objects.UpdateCartItemRequest;
import com.example.ubfactory.repository.CartRepository;
import com.example.ubfactory.repository.CustomerRepository;
import com.example.ubfactory.repository.ProductRepository;
import com.example.ubfactory.service.CartService;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;


@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired

    private CustomerRepository customerRepository;
    @Autowired

    private ProductRepository productRepository;


    @Override
    public CartResponse getCartItems(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer).orElseGet(() -> createCartForCustomer(customer));

        return CartMapper.toCartResponse(cart);
    }

    @Override
    public CartItemResponse addCartItem(Integer customerId, AddCartItemRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer).orElseGet(() -> createCartForCustomer(customer));

        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        CartItem cartItem = CartMapper.toCartItem(request, cart, product);
        cart.getCartItems().add(cartItem);
        cart.setUpdatedAt(new Date());
        cartRepository.save(cart);

        return CartMapper.toCartItemResponse(cartItem);
    }

    @Override
    public CartItemResponse updateCartItemQuantity(Integer customerId, Integer itemId, UpdateCartItemRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer).orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getId().equals(itemId)).findFirst().orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cart);

        return CartMapper.toCartItemResponse(cartItem);
    }

    @Override
    public void deleteCartItem(Integer customerId, Integer itemId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException(ResponseConstants.CUSTOMER_NOT_FOUND));

        Cart cart = cartRepository.findByCustomer(customer).orElseThrow(() -> new EntityNotFoundException(ResponseConstants.CART_ITEM_NOT_FOUND));

        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getId().equals(itemId)).findFirst().orElseThrow(() -> new EntityNotFoundException(ResponseConstants.CART_ITEM_NOT_FOUND));

        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);
    }

    private Cart createCartForCustomer(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());

        customer.setCart(cart); // Set the cart for the customer

        return cartRepository.save(cart);
    }

}
