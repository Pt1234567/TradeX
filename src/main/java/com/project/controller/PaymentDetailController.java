package com.project.controller;

import com.project.Entity.PaymentDetails;
import com.project.Entity.User;
import com.project.service.PaymentDetailService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paymentDetail")
public class PaymentDetailController {

    @Autowired
    private PaymentDetailService paymentDetailService;

    @Autowired
    private UserService userService;


    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> addPaymentDetails(@RequestBody PaymentDetails paymentDetails,@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);
        PaymentDetails paymentDetails1=paymentDetailService.addPaymentDetails(
                paymentDetails.getAccountNumber(),
                paymentDetails.getAccountHolderName(),
                paymentDetails.getIfsc(),
                paymentDetails.getBankName(),
                paymentDetails.getUser()
        );
        return new ResponseEntity<>(paymentDetails1, HttpStatus.CREATED);
    }

    @GetMapping("/payment-details")
    public ResponseEntity<PaymentDetails> getUserPaymentDetails(@RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);
        PaymentDetails paymentDetails=paymentDetailService.getUserPaymentDetail(user);
        return new ResponseEntity<>(paymentDetails,HttpStatus.OK);
    }

}
