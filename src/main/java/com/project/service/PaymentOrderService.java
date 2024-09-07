package com.project.service;

import com.project.Entity.PaymentOrder;
import com.project.Entity.User;
import com.project.helper.PaymentMethod;
import com.project.helper.PaymentOrderStatus;
import com.project.response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentOrderService {

    PaymentOrder createPaymentOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    Boolean proceedPaymentOrder(PaymentOrder paymentOrder,String paymentId) throws RazorpayException;
    PaymentResponse createRazorpayPaymentLink(User user, Long amount);//for indians
    PaymentResponse createStripePaymentLink(User user, Long amount,Long orderId) throws StripeException;//for international payment

}
