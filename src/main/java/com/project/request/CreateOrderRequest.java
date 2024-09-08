package com.project.request;

import com.project.helper.OrderType;
import lombok.Data;

@Data
public class    CreateOrderRequest {
    private  String coinId;
    private double quantity;
    private OrderType orderType;
}
