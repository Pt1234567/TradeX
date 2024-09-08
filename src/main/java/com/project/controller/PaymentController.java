package com.project.controller;

import com.project.Entity.PaymentOrder;
import com.project.Entity.User;
import com.project.helper.PaymentMethod;
import com.project.response.PaymentResponse;
import com.project.service.PaymentOrderService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
             User user=userService.findUserByJwt(jwt);

             PaymentResponse paymentResponse;
        PaymentOrder paymentOrder=paymentOrderService.createPaymentOrder(user,amount,paymentMethod);
        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse=paymentOrderService.createRazorpayPaymentLink(user,amount,paymentOrder.getId());
        }else{
            paymentResponse=paymentOrderService.createStripePaymentLink(user,amount,paymentOrder.getId());
        }

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }




}
