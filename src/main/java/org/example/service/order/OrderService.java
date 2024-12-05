package org.example.service.order;

import org.example.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrdersFromLastMonth();
}
