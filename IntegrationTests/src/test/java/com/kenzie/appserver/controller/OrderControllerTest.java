package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.OrderRequest;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.OrderService;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@IntegrationTest
class OrderControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    OrderService orderService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void startOrder_Successful() throws Exception {

        String userName = mockNeat.strings().valStr();
        List<String> items = Arrays.asList("item1", "item2");

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserName(userName);
        orderRequest.setItems(items);

        mvc.perform(post("/order/startOrder")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderRequest)))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("userName").value(is(userName)))
                .andExpect(jsonPath("items", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    public void startOrder_Successful_WithEmptyItems() throws Exception {

        String userName = mockNeat.strings().valStr();
        List<String> items = Collections.emptyList();

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserName(userName);
        orderRequest.setItems(items);

        mvc.perform(post("/order/startOrder")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderRequest)))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("userName").value(is(userName)))
                .andExpect(jsonPath("items", hasSize(0)))
                .andExpect(status().isOk());
    }

    @Test
    public void addItemToOrder_Successful() throws Exception {

        String username = mockNeat.strings().valStr();
        List<String> newItems = Arrays.asList("item3", "item4");

        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setUserName(username);
        orderRecord.setId(UUID.randomUUID().toString());
        orderRecord.setItems(Arrays.asList("item1", "item2"));
        orderService.startOrder(orderRecord);

        mvc.perform(post("/order/add-item/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newItems)))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("userName").value(is(username)))
                .andExpect(jsonPath("items", hasSize(4)))
                .andExpect(status().isOk());
    }

    @Test
    public void addItemToOrder_EmptyItemsList() throws Exception {

        String username = mockNeat.strings().valStr();
        List<String> items = Collections.emptyList();

        mvc.perform(post("/order/add-item/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(items)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchOrderByName_ExistingOrder_Successful() throws Exception {

        String username = mockNeat.strings().valStr();

        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setUserName(username);
        orderRecord.setId(UUID.randomUUID().toString());
        orderRecord.setItems(Arrays.asList("item1", "item2"));
        orderService.startOrder(orderRecord);

        mvc.perform(get("/order/search/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("userName").value(username))
                .andExpect(status().isOk());
    }


    @Test
    public void removeItemFromOrder_Successful() throws Exception {

        String username = "testUsername";
        String itemToRemove = mockNeat.strings().valStr();

        OrderRecord record = new OrderRecord();
        record.setUserName(username);
        record.setId(UUID.randomUUID().toString());
        record.setItems(new ArrayList<>());
        orderService.startOrder(record);

        List<String> items = Arrays.asList("item1", "item2");
        orderService.addItemToOrder(record.getUserName(), items);

        mvc.perform(delete("/order/remove-item/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("item", itemToRemove))
                .andExpect(content().string(""))
                .andExpect(status().isOk());
    }

    @Test
    public void removeItemFromOrder_ItemNotFound() throws Exception {

        String username = "testUsername";
        String nonExistingItem = mockNeat.strings().valStr();

        OrderRecord record = new OrderRecord();
        record.setUserName(username);
        record.setId(UUID.randomUUID().toString());
        record.setItems(Arrays.asList("item1", "item2"));
        orderService.startOrder(record);

        mvc.perform(delete("/order/remove-item/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("item", nonExistingItem))
                .andExpect(status().isOk());
    }
}