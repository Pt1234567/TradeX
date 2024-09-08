package com.project.controller;

import com.project.Entity.*;
import com.project.helper.WalletTransactionType;
import com.project.response.PaymentResponse;
import com.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @GetMapping
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
         User user=userService.findUserByJwt(jwt);
         Wallet wallet=walletService.getUserWallet(user);
         return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransaction(@RequestHeader("Authorization")String jwt,
                                                            @PathVariable Long walletId,
                                                            @RequestBody WalletTransaction walletTransaction) throws Exception {
                User senderUser=userService.findUserByJwt(jwt);
                Wallet receiverWallet=walletService.findById(walletId);
                Wallet wallet=walletService.walletToWalletTransfer(senderUser,receiverWallet,walletTransaction.getAmount());

                WalletTransaction walletTransaction1=walletTransactionService.createTransaction(wallet, WalletTransactionType.WALLET_TRANSFER,"Wapis kr dena",walletTransaction.getAmount());

                return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

    @PutMapping("/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization")String jwt, @PathVariable Long orderId) throws Exception {
          User user=userService.findUserByJwt(jwt);
          Order order=orderService.getOrderById(orderId);
          Wallet wallet=walletService.payOrderPayment(order,user);

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

    @PutMapping("/deposit")
    public ResponseEntity<Wallet> addBalanceToWallet(@RequestHeader("Authorization")String jwt,@RequestParam(name="order_id")Long orderId,@RequestParam(name="payment_id")String paymentId) throws Exception {
        User user=userService.findUserByJwt(jwt);
        Wallet wallet=walletService.getUserWallet(user);

        PaymentOrder paymentOrder=paymentOrderService.getPaymentOrderById(orderId);

        Boolean status=paymentOrderService.proceedPaymentOrder(paymentOrder,paymentId);

        if(wallet.getBalance()==null){
            wallet.setBalance(BigDecimal.valueOf(0));
        }
        if(status){
            wallet=walletService.addBalance(wallet, paymentOrder.getAmount());
            WalletTransaction walletTransaction1=walletTransactionService.createTransaction(wallet, WalletTransactionType.WALLET_TRANSFER,"Wapis kr dena",paymentOrder.getAmount());
        }

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

}
