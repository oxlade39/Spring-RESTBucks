package com.fb.restbucks.integration;

import com.fb.restbucks.domain.*;
import com.fb.restbucks.integration.builder.TestBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * @author dan
 */
public class OrderBuilder implements TestBuilder<Order> {
    private String id;
    private OrderStatus status = OrderStatus.PENDING;
    private List<Item> items = Arrays.asList(item());

    static OrderBuilder forAnAmericano() {
        return new OrderBuilder();
    }

    private Item item() {
        Item item = new Item();
        item.setMilk(Milk.SKIM);
        item.setName("americano");
        item.setQuantity(1);
        item.setSize(Size.SMALL);
        return item;
    }

    @Override
    public Order build() {
        Order order = new Order();
        order.setId(id);
        order.setStatus(status);
        order.setItems(items);
        return order;
    }
    
    public OrderBuilder withId(String id) {
        this.id = id;
        return this;
    }
}
