package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.*;
import com.example.ubfactory.helper.CartMapper;
import com.example.ubfactory.objects.*;
import com.example.ubfactory.repository.*;
import com.example.ubfactory.service.CartService;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired

    private CustomerRepository customerRepository;
    @Autowired

    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ShippingRepository shippingRepository;


    @Override
    public CartResponse getCartItems(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer).orElseThrow(() -> new EntityNotFoundException("Cart not found"));

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

    @Override
    public CartItemPriceResponse getCartItemsWithShipping(Integer customerId) {
        CartItemPriceResponse cartItemResponse =new CartItemPriceResponse();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Cart cart = cartRepository.findByCustomer(customer).orElseThrow(() -> new EntityNotFoundException("cart not found"));
        List<CartItem> cartItem = cartItemRepository.findAllByCart(cart);

        AtomicReference<BigDecimal> subtotal = new AtomicReference<BigDecimal>(new BigDecimal(0.00));
        AtomicReference<BigDecimal> price = new AtomicReference<BigDecimal>(new BigDecimal(0.00));

        cartItem.forEach((ele) -> {
            Product product = ele.getProduct();
            subtotal.updateAndGet(v -> v.add(ele.getProduct().getOriginalPrice().multiply(BigDecimal.valueOf(ele.getQuantity()))));
            price.updateAndGet(v->v.add(product.getPrice().multiply(BigDecimal.valueOf(ele.getQuantity()))));

        });
        BigDecimal  discount = price.get().subtract(subtotal.get());
        cartItemResponse.setTotalPrice(price.get());
        cartItemResponse.setDiscount(discount);
        cartItemResponse.setPrice(subtotal.get());
        Shipping shipping = shippingRepository.findById(1).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        cartItemResponse.setShippingCharges(String.valueOf(shipping.getCharges()));
        BigDecimal orderAmount= shipping.getCharges().add(subtotal.get());
        cartItemResponse.setOrderAmount(orderAmount);
        return cartItemResponse;
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
