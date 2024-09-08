package com.project.service.impl;

import com.project.Entity.PaymentOrder;
import com.project.Entity.User;
import com.project.Repository.PaymentOrderRepository;
import com.project.helper.PaymentMethod;
import com.project.helper.PaymentOrderStatus;
import com.project.response.PaymentResponse;
import com.project.service.PaymentOrderService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecretKey;

    @Override
    public PaymentOrder createPaymentOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder=new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        paymentOrder.setOrderStatus(PaymentOrderStatus.PENDING);
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
         return paymentOrderRepository.findById(id).orElseThrow(()->new Exception("Payment order not found"));
    }

    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {

        if(paymentOrder.getOrderStatus()==null){
            paymentOrder.setOrderStatus(PaymentOrderStatus.PENDING);
        }

          if(paymentOrder.getOrderStatus().equals(PaymentOrderStatus.PENDING)){
              if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
                  RazorpayClient razorpay=new RazorpayClient(apiKey,apiSecretKey);
                  Payment payment=razorpay.payments.fetch(paymentId);

                  Integer amount=payment.get("amount");
                  String status=payment.get("status");

                  if(status.equals("captured")){
                      paymentOrder.setOrderStatus(PaymentOrderStatus.SUCCESS);
                      return true;
                  }
                      paymentOrder.setOrderStatus(PaymentOrderStatus.FAILED);
                      paymentOrderRepository.save(paymentOrder);
                      return false;
              }
              paymentOrder.setOrderStatus(PaymentOrderStatus.SUCCESS);
              paymentOrderRepository.save(paymentOrder);
              return true;
          }
          return false;
    }

    @Override
    public PaymentResponse createRazorpayPaymentLink(User user, Long amount,Long orderId) {
        Long Amount=amount*100;

        try{

            //Instantiate a razorpay client with your id and secret key
            RazorpayClient razorpayClient=new RazorpayClient(apiKey,apiSecretKey);

            //create Json with payment link request parameters
            JSONObject paymentLinkRequest=new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            //create json object with cutomer details
            JSONObject customer=new JSONObject();
            customer.put("name",user.getName());
            customer.put("email",user.getEmail());

            paymentLinkRequest.put("customer",customer);

            //create json object with notification setting
            JSONObject notify=new JSONObject();
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            //set reminder setting
            paymentLinkRequest.put("reminder_enable",true);

            //set the callback url and method
            paymentLinkRequest.put("callback_url","http://localhost:5050/wallet?order_id="+orderId);
            paymentLinkRequest.put("callback_method","get");

            //create paymentlink using payment link create method
            PaymentLink payment=razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkId=payment.get("id");
            String paymentLinkUrl=payment.get("short_url");

            PaymentResponse paymentResponse=new PaymentResponse();
            paymentResponse.setPaymentUrl(paymentLinkUrl);

            return  paymentResponse;

        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {
        Stripe.apiKey=stripeSecretKey;

        SessionCreateParams params=SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5050/wallet?order_id="+orderId)
                .setCancelUrl("http://localhost:5050/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("INR")
                                        .setUnitAmount(amount*100)
                                        .setProductData(
                                                SessionCreateParams
                                                        .LineItem
                                                        .PriceData
                                                        .ProductData
                                                        .builder()
                                                        .setName("Top up wallet")
                                                        .build()
                                        ).build()
                        ).build()
                ).build();

        Session session=Session.create(params);

        System.out.println("Session____"+session);
        PaymentResponse paymentResponse=new PaymentResponse();
        paymentResponse.setPaymentUrl(session.getUrl());

        return paymentResponse;
    }
}
