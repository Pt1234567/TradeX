package com.project.controller;

import com.project.Entity.Order;
import com.project.Entity.User;
import com.project.Entity.Wallet;
import com.project.Entity.WalletTransaction;
import com.project.service.OrderService;
import com.project.service.UserService;
import com.project.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
         User user=userService.findUserByJwt(jwt);
         Wallet wallet=walletService.getUserWallet(user);
         return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransaction(@RequestHeader("Authorization")String jwt,
                                                            @PathVariable Long walletId,
                                                            @RequestBody WalletTransaction walletTransaction) throws Exception {
                User senderUser=userService.findUserByJwt(jwt);
                Wallet receiverWallet=walletService.findById(walletId);
                Wallet wallet=walletService.walletToWalletTransfer(senderUser,receiverWallet,walletTransaction.getAmount());

                return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization")String jwt, @PathVariable Long orderId) throws Exception {
          User user=userService.findUserByJwt(jwt);
          Order order=orderService.getOrderById(orderId);
          Wallet wallet=walletService.payOrderPayment(order,user);
          return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

}
