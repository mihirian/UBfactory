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
import java.util.Optional;


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

        // Check if the product is already in the cart
        boolean productAlreadyInCart = cart.getCartItems().stream()
                .anyMatch(item -> item.getProduct().equals(product));

        if (productAlreadyInCart) {
            throw new IllegalStateException("Product is already in the cart");
        }

        CartItem cartItem = CartMapper.toCartItem(request, cart, product);
        cart.getCartItems().add(cartItem);
        cart.setUpdatedAt(new Date());

        // Save the cart which contains the new CartItem
        Cart savedCart = cartRepository.save(cart);

        // Now the CartItem should have been saved and assigned an id
        // Find the saved CartItem in the saved Cart
        Optional<CartItem> savedCartItem = savedCart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();

        if (savedCartItem.isPresent()) {
            return CartMapper.toCartItemResponse(savedCartItem.get());
        } else {
            throw new IllegalStateException("Failed to save CartItem");
        }
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
