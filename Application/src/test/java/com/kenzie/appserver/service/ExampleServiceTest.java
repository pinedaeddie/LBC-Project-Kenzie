package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.OrderRepository;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExampleServiceTest {
    private OrderRepository orderRepository;
    private OrderService exampleService;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        exampleService = new OrderService(orderRepository);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findById() {
        // GIVEN
        String id = randomUUID().toString();

        OrderRecord record = new OrderRecord();
        record.setItemId(id);
        record.setItemName("testName");

        // WHEN
        when(orderRepository.findById(id)).thenReturn(Optional.of(record));
        Order example = exampleService.findById(id);

        // THEN
        Assertions.assertNotNull(example, "The object is returned");
        //Assertions.assertEquals(record.getItemId(), example.getId(), "The id matches");
        //Assertions.assertEquals(record.getName(), example.getName(), "The name matches");
    }

    @Test
    void findByConcertId_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Order example = exampleService.findById(id);

        // THEN
        Assertions.assertNull(example, "The example is null when not found");
    }

}
