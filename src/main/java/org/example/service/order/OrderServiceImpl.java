package org.example.service.order;

import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.example.repository.OrderRepositoryMySQL;

import java.util.List;

public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getOrdersFromLastMonth() {
        return orderRepository.getOrdersFromLastMonth();
    }
}
