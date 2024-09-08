package com.project.service.impl;

import com.project.Entity.Wallet;
import com.project.Entity.WalletTransaction;
import com.project.Repository.WalletTransactionRepository;
import com.project.helper.WalletTransactionType;
import com.project.service.WalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Override
    public List<WalletTransaction> getTransactionByWallet(Wallet wallet) {
          return walletTransactionRepository.findAll();
    }

    @Override
    public WalletTransaction createTransaction(Wallet wallet, WalletTransactionType walletTransactionType, String purpose, Long amount) {
        WalletTransaction walletTransaction=new WalletTransaction();
        walletTransaction.setWallet(wallet);
        walletTransaction.setAmount(amount);
        walletTransaction.setPurpose(purpose);
        walletTransaction.setType(walletTransactionType);
        walletTransaction.setDate(LocalDate.now());

        return walletTransactionRepository.save(walletTransaction);
    }
}
