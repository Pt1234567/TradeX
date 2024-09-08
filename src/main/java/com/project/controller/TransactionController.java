package com.project.controller;

import com.project.Entity.User;
import com.project.Entity.Wallet;
import com.project.Entity.WalletTransaction;
import com.project.service.UserService;
import com.project.service.WalletService;
import com.project.service.WalletTransactionService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @GetMapping("/api/transactions")
    public ResponseEntity<List<WalletTransaction>> getTransactions(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);

        Wallet wallet=walletService.getUserWallet(user);

        List<WalletTransaction> walletTransactionList=walletTransactionService.getTransactionByWallet(wallet);
        return new ResponseEntity<>(walletTransactionList, HttpStatus.OK);
    }

}
