package com.project.service;

import com.project.Entity.Wallet;
import com.project.Entity.WalletTransaction;
import com.project.helper.WalletTransactionType;

import java.util.List;

public interface WalletTransactionService {
    List<WalletTransaction> getTransactionByWallet(Wallet wallet);
    WalletTransaction createTransaction(Wallet wallet, WalletTransactionType walletTransactionType,String purpose,Long amount);
}
