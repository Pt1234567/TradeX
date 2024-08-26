package com.project.service;

import com.project.Entity.Coin;
import com.project.Entity.Order;
import com.project.Entity.OrderItem;
import com.project.Entity.User;
import com.project.helper.OrderType;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrderOfUsers(Long userId,OrderType orderType,String assetSymbol);

    Order processOrder(Coin coin,double quantity,OrderType orderType,User user) throws Exception;



}
