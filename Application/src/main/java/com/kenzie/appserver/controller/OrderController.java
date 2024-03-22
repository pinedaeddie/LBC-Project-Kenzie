package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.OrderRequest;
import com.kenzie.appserver.controller.model.OrderResponse;

import com.kenzie.appserver.service.OrderService;
import com.kenzie.appserver.service.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@Component
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/start")
    public ResponseEntity<OrderResponse> startOrder(@RequestBody OrderRequest orderRequest) {
        // needs to Implement
        Order order = new Order(orderRequest.getId(), orderRequest.getName(), new Date(),
                orderRequest.getOrderItems(), orderRequest.getQuantity(), orderRequest.getPrice(), orderRequest.getOrderTotal());
        orderService.startOrder(order);
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setItemName(order.getName());
        response.setQuantity(order.getQuantity());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-item")
    public ResponseEntity<OrderResponse> addItemToOrder(@RequestBody OrderRequest orderRequest) {
        // needs to Implement
        return null;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetail(@PathVariable("orderId") String orderId) {
        // needs to Implement
        return null;
    }

    @DeleteMapping("/remove-item/{itemId}")
    public ResponseEntity<String> removeItemFromOrder(@PathVariable ("itemId") String itemId) {
        // needs to Implement
        return null;
    }

    @GetMapping("/items/search/{itemId}")
    public ResponseEntity<String> searchItemById(@PathVariable("itemId") String itemId) {
        // needs to Implement
        return null;
    }
}
