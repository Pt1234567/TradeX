package com.project.service.impl;

import com.project.Entity.PaymentDetails;
import com.project.Entity.User;
import com.project.Repository.PaymentDetailRepository;
import com.project.service.PaymentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService {

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifscCode, String bankName, User user) {
        PaymentDetails paymentDetails=new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfsc(ifscCode);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);

        return paymentDetailRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUserPaymentDetail(User user) {
        return paymentDetailRepository.findByUserId(user.getId());
    }
}
