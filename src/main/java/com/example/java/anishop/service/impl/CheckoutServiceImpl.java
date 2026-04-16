package com.example.java.anishop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.java.anishop.converter.MapperConverter;
import com.example.java.anishop.enums.OrderStatus;
import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.model.reponse.ApiResponse;
import com.example.java.anishop.model.reponse.OrderDTO;
import com.example.java.anishop.repository.CartItemRepository;
import com.example.java.anishop.repository.CartRepository;
import com.example.java.anishop.repository.OrderDetailRepository;
import com.example.java.anishop.repository.OrderRepository;
import com.example.java.anishop.repository.ShopRepository;
import com.example.java.anishop.repository.entity.CartItems;
import com.example.java.anishop.repository.entity.Carts;
import com.example.java.anishop.repository.entity.OrderDetails;
import com.example.java.anishop.repository.entity.Orders;
import com.example.java.anishop.repository.entity.Shops;
import com.example.java.anishop.service.CheckoutService;
import com.example.java.anishop.util.SecurityUtils;

import jakarta.transaction.Transactional;

public class CheckoutServiceImpl implements CheckoutService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private MapperConverter mapperConverter;
    @Autowired
    private ShopRepository shopRepository;

    @Transactional
    @Override
    public ApiResponse<?> checkout(Long userId, String address) {
        String email=securityUtils.getCurrentUserEmail();

        Shops shop=shopRepository.findByUserShopIdAndDeletedFalse(userId);
        // kiểm tra xem có phải mình mua shop chính mình kh
        validateShopOwnerUser(shop, email);

        Carts cart=cartRepository.findByUserCartsIdAndDeletedFalse(userId)
                .orElseThrow(()-> new AppException("Không tìm thấy giỏ hàng", 404));
         
        List<CartItems> cartItems=cartItemRepository.findByCartCartsIdAndDeletedFalse(cart.getCartsId());
        
        if(cartItems.isEmpty()){
            throw new RuntimeException("Giỏ hàng đang trống");
        }

        Double totalPrice = cartItems.stream().mapToDouble(it-> it.getQuantity() * it.getCartProduct().getPrice())
                        .sum();

       
        // 3. Tạo Orders
        Orders order = new Orders();
        order.setUserOrder(cart.getUserCarts());
        order.setTotalPrice(totalPrice);
        order.setAddress(address);
        order.setStatus(OrderStatus.valueOf("PENDING"));
        Orders savedOrder = orderRepository.save(order);
 
        // 4. Tạo OrderDetails — snapshot giá tại thời điểm mua
        List<OrderDetails> details = cartItems.stream().map(item -> {
            OrderDetails detail = new OrderDetails();
            detail.setOrder(savedOrder);
            detail.setProductOrderDetail(item.getCartProduct());
            detail.setPrice(item.getCartProduct().getPrice()); // snapshot!
            detail.setQuantity(item.getQuantity());
            return detail;
        }).collect(Collectors.toList());
 
        orderDetailRepository.saveAll(details);
 
        // 5. Xóa giỏ hàng sau khi đặt thành công
        cartItems.forEach(i -> i.setDeleted(true));
        cartItemRepository.saveAll(cartItems);
        
        OrderDTO dto=mapperConverter.setOrderDTO(order);

        return ApiResponse.<OrderDTO>builder()
                    .status(201)
                    .message("Đã đặt hàng thành công")
                    .data(dto)
                    .build();

    }
    @Override
    public void validateShopOwnerUser(Shops shop, String email) {
        // TODO Auto-generated method stub
        if(shop.getUserShop().getEmail().equals(email)){
            throw new AppException("Không thể đặt hàng của Shop của bạn", 403);
        }
    }

}
