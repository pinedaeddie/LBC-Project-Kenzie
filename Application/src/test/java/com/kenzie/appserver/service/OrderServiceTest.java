package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.OrderRepository;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }


    /** ------------------------------------------------------------------------
     *  OrderService.removeItemFromOrder
     *  ------------------------------------------------------------------------ **/

    @Test
    void removeItemFromOrder_WhenOrderDoesNotExist_ShouldThrowIllegalArgumentException() {
        // GIVEN
        String orderId = "123";
        String itemToRemove = "ItemToRemove";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> orderService.removeItemFromOrder(orderId, itemToRemove));
    }

    @Test
    void removeItemFromOrder_WhenOrderExistsAndItemExists_ShouldRemoveItemFromOrder() {
        // GIVEN
        String orderId = "123";
        String itemToRemove = "ItemToRemove";
        List<String> items = new ArrayList<>();
        items.add("Item1");
        items.add("Item2");
        items.add(itemToRemove);
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(orderId);
        orderRecord.setUserName("TestUser");
        orderRecord.setOrderDate(new Date());
        orderRecord.setItems(items);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderRecord));

        // WHEN
        orderService.removeItemFromOrder(orderId, itemToRemove);

        // THEN
        verify(orderRepository).save(any(OrderRecord.class));
        assertFalse(orderRecord.getItems().contains(itemToRemove));
    }

}
