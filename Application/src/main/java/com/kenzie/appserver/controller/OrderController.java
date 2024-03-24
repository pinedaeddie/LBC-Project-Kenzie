package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ItemRequest;
import com.kenzie.appserver.controller.model.OrderRequest;
import com.kenzie.appserver.controller.model.OrderResponse;

import com.kenzie.appserver.service.OrderService;
import com.kenzie.appserver.service.model.Item;
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

        try {
            Order order = new Order(orderRequest.getId(), orderRequest.getName(), new Date(),
                    orderRequest.getOrderItems(), orderRequest.getQuantity(), orderRequest.getOrderTotal());
            orderService.startOrder(order);
            OrderResponse response = new OrderResponse();
            response.setOrderId(order.getId());
            response.setItemName(order.getName());
            response.setQuantity(order.getQuantity());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/add-item/{id}")
    public ResponseEntity<OrderResponse> addItemToOrder(@PathVariable("id") String id, @RequestBody ItemRequest itemRequest) {

        try {
            Item item = new Item(itemRequest.getItemId(), itemRequest.getName(), itemRequest.getQuantity(), itemRequest.getPrice());
            orderService.addItemToOrder(id, item);
            OrderResponse response = new OrderResponse();
            response.setOrderId(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetail(@PathVariable("orderId") String orderId) {

        try {
            Order order = orderService.getOrderDetail(orderId);
            OrderResponse response = new OrderResponse();
            response.setOrderId(orderId);
            response.setOrderItems(order.getOrderItems());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/total/{orderId}")
    public ResponseEntity<Double> getOrderTotal(@PathVariable("orderId") String orderId) {

        try {
            double total = orderService.getTotal(orderId);
            return ResponseEntity.ok(total);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/remove-item/{itemId}/{orderId}")
    public ResponseEntity<String> removeItemFromOrder(@PathVariable ("itemId") String itemId, @PathVariable ("orderId") String orderId) {

        try {
            orderService.removeItemFromOrder(itemId,orderId);
            return ResponseEntity.ok("Item was successfully removed from Order");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/items/search/{itemId}")
    public ResponseEntity<Item> searchItemById(@PathVariable("itemId") String itemId) {

        try {
            Item item = orderService.searchItemById(itemId);
            if (item != null) {
                return ResponseEntity.ok(item);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
