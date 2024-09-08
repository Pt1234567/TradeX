package com.project.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Entity.Coin;
import com.project.Entity.Order;
import com.project.Entity.User;
import com.project.helper.OrderType;
import com.project.request.CreateOrderRequest;
import com.project.service.CoinService;
import com.project.service.OrderService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CoinService coinService;

    @Autowired
    private UserService userService;


    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization")String jwt, @RequestBody CreateOrderRequest request) throws Exception {
        User user=userService.findUserByJwt(jwt);
        Coin coin=coinService.findById(request.getCoinId());
        Order order=orderService.processOrder(coin, request.getQuantity(), request.getOrderType(),user);
        return ResponseEntity.ok(order);
    }

        @GetMapping("/{orderId}")
        public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization")String jwt,@PathVariable Long orderId) throws Exception {
              User user=userService.findUserByJwt(jwt);

              Order order=orderService.getOrderById(orderId);

              if(order.getUser().getId().equals(user.getId())){
                  return ResponseEntity.ok(order);
              }else{
                  throw  new Exception("Invalid User");
              }
        }

        @GetMapping()
        public ResponseEntity<List<Order>> getAllOrdersForUser(@RequestHeader("Authorization")String jwt,
                                                               @RequestParam(required = false) OrderType order_type,
                                                               @RequestParam(required = false)String asset_symbol) throws Exception {
                Long userId=userService.findUserByJwt(jwt).getId();
                List<Order> userOrders=orderService.getAllOrderOfUsers(userId,order_type,asset_symbol);
                return ResponseEntity.ok(userOrders);
        }

}
