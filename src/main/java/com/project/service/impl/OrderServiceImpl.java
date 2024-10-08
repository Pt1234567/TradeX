package com.project.service.impl;

import com.project.Entity.*;
import com.project.Repository.OrderItemRepository;
import com.project.Repository.OrderRepository;
import com.project.helper.OrderStatus;
import com.project.helper.OrderType;
import com.project.helper.WalletTransactionType;
import com.project.service.AssetService;
import com.project.service.OrderService;
import com.project.service.WalletService;
import com.project.service.WalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
         double price=orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
         Order newOrder=new Order();
         newOrder.setUser(user);
         newOrder.setOrderType(orderType);
         newOrder.setPrice(BigDecimal.valueOf(price));
         newOrder.setOrderItem(orderItem);
         newOrder.setTimestamp(LocalDateTime.now());
         newOrder.setOrderStatus(OrderStatus.PENDING);
         return orderRepository.save(newOrder);
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(()->new Exception("Order not found"));
    }

    @Override
    public List<Order> getAllOrderOfUsers(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    private OrderItem createOrderItem(Coin coin,double quantity,double buyPrice,double sellPrice){
        OrderItem orderItem=new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        orderItem.setQuantity(quantity);

        return orderItemRepository.save(orderItem);
    }

    public Order buyAsset(Coin coin,double quantity,User user) throws Exception {
        if(quantity<=0){
            throw  new Exception("quantity should be > 0");
        }
        double buyPrice=coin.getCurrentPrice();
        OrderItem orderItem=createOrderItem(coin,quantity,buyPrice,0);
        Order order=createOrder(user,orderItem,OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order,user);
        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);

        Order saveOrder=orderRepository.save(order);

        //create asset
        Asset oldAsset=assetService.findAssetByUserIdAndCoinId(order.getUser().getId(), order.getOrderItem().getCoin().getId());

        if(oldAsset==null){
            assetService.createAsset(order.getUser(),order.getOrderItem().getCoin(),orderItem.getQuantity());
        }else{
            assetService.updateAsset(oldAsset.getId(), quantity);
        }
        return saveOrder;
    }


    public Order sellAsset(Coin coin,double quantity,User user) throws Exception {
        if(quantity<=0){
            throw  new Exception("quantity should be > 0");
        }
        double sellPrice=coin.getCurrentPrice();

        Asset assetToSell=assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
        double buyPrice = assetToSell.getBuyPrice();
        if(assetToSell!=null) {
            OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
            Order order = createOrder(user, orderItem, OrderType.SELL);
            orderItem.setOrder(order);

            if (assetToSell.getQuantity() >= quantity) {

                order.setOrderStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);

                Order saveOrder = orderRepository.save(order);
                walletService.payOrderPayment(order, user);
                Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);
                if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
                    assetService.deleteAsset(updatedAsset.getId());
                }
                return saveOrder;
            }
            throw new Exception("Insufficient Quantity to sell");
        }
        throw  new Exception("asset not found");
    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
        if(orderType==OrderType.BUY){
            return buyAsset(coin,quantity,user);
        }else if(orderType==OrderType.SELL){
            return sellAsset(coin,quantity,user);
        }

        throw new Exception("Invalid order type");
    }
}
