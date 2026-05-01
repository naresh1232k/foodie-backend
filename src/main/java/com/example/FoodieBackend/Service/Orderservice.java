package com.example.FoodieBackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.FoodieBackend.Models.MenuItem;
import com.example.FoodieBackend.Dto.Createorderrequest;
import com.example.FoodieBackend.Dto.Orderresponse;
import com.example.FoodieBackend.Models.Order;
import com.example.FoodieBackend.Models.OrderItem;
import com.example.FoodieBackend.Models.User;
import com.example.FoodieBackend.Repository.MenuItemRepository;
import com.example.FoodieBackend.Repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Orderservice {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderAsyncService orderAsyncService; // 👈 async service

    @Transactional
    public Orderresponse createOrder(Createorderrequest req, User user) {

        Order order = Order.builder()
                .user(user)
                .totalAmount(req.getTotalAmount())
                .deliveryAddress(req.getDeliveryAddress())
                .paymentId(req.getPaymentId())
                .status(Order.OrderStatus.PENDING)
                .build();

        List<OrderItem> items = req.getItems().stream().map(i -> {
            MenuItem menuItem = menuItemRepository.findById(i.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found: " + i.getMenuItemId()));

            return OrderItem.builder()
                    .order(order)
                    .menuItem(menuItem)
                    .quantity(i.getQuantity())
                    .price(i.getPrice())
                    .build();
        }).collect(Collectors.toList());

        order.setItems(items);

        Order saved = orderRepository.save(order);

        // 🔥 ASYNC CALL (background thread)
        orderAsyncService.processOrder(saved);

        return Orderresponse.from(saved);
    }

    public List<Orderresponse> getMyOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user)
                .stream().map(Orderresponse::from).collect(Collectors.toList());
    }

    public List<Orderresponse> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(Orderresponse::from).collect(Collectors.toList());
    }

    @Transactional
    public Orderresponse updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));

        return Orderresponse.from(orderRepository.save(order));
    }
}