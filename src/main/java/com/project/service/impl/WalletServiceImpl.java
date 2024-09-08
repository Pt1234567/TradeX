package com.project.service.impl;

import com.project.Entity.Order;
import com.project.Entity.User;
import com.project.Entity.Wallet;
import com.project.Repository.WalletRepository;
import com.project.helper.OrderType;
import com.project.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet=walletRepository.findByUserId(user.getId());
        if(wallet==null){
            wallet=new Wallet();
            wallet.setUser(user);
            walletRepository.save(wallet);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long amount) {
        BigDecimal balance=wallet.getBalance();
        BigDecimal newBalance=balance.add(BigDecimal.valueOf(amount));
        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findById(Long id) throws Exception {
        Optional<Wallet> optional=walletRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }

        throw  new Exception("Wallet not found");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet,Long amount) throws Exception {
        Wallet senderWallet=walletRepository.findByUserId(sender.getId());
        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0){
            throw new Exception("Insufficient wallet");
        }
        BigDecimal newBalance=senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(newBalance);
        walletRepository.save(senderWallet);

        BigDecimal receiverBalance=receiverWallet.getBalance().add(BigDecimal.valueOf(amount));
        receiverWallet.setBalance(receiverBalance);
        walletRepository.save(receiverWallet);
        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet=walletRepository.findByUserId(user.getId());

        if(order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBalance=wallet.getBalance().subtract(order.getPrice());
            if(newBalance.compareTo(order.getPrice())<0){
                throw new Exception("Insufficient Balance");
            }
            wallet.setBalance(newBalance);
        }else{
            BigDecimal newBalance=wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }
        return walletRepository.save(wallet);
    }
}
