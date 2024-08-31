package com.project.controller;

import com.project.Entity.User;
import com.project.Entity.Wallet;
import com.project.Entity.Withdrawal;
import com.project.service.UserService;
import com.project.service.WalletService;
import com.project.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(@PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);
        Wallet userWallet=walletService.getUserWallet(user);

        Withdrawal withdrawalRequest=withdrawalService.requestWithdrawal(amount,user);
        walletService.addBalance(userWallet,-withdrawalRequest.getAmount());
        return new ResponseEntity<>(withdrawalRequest, HttpStatus.OK);
    }

    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(@PathVariable Long id,
                                               @PathVariable boolean accept,
                                               @RequestHeader("Authorization")String jwt) throws Exception {
                   User user=userService.findUserByJwt(jwt);
                   Withdrawal withdrawal=withdrawalService.proceedWithdrawal(id,accept);

                   Wallet wallet=walletService.getUserWallet(user);

                   if(!accept){
                       walletService.addBalance(wallet,withdrawal.getAmount());
                   }
                   return new ResponseEntity<>(withdrawal,HttpStatus.OK);
    }

    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(@RequestHeader("Authorization") String jwt) throws Exception {
              User user=userService.findUserByJwt(jwt);
              List<Withdrawal> withdrawalList=withdrawalService.getUsersWithdrawalHistory(user);
              return new ResponseEntity<>(withdrawalList,HttpStatus.OK);
    }

    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(@RequestHeader("Authorization") String jwt) throws Exception {
           User user=userService.findUserByJwt(jwt);
           List<Withdrawal> withdrawalList=withdrawalService.getAllWithdrawalRequest();
           return new ResponseEntity<>(withdrawalList,HttpStatus.OK);
    }

}
