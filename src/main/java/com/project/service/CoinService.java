package com.project.service;

import com.project.Entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinService  {

    List<Coin> getCoinList(int page) throws Exception;
    String getMarketChart(String coinId,int days) throws Exception;
    String coinDetails(String coinId) throws Exception;//get coin from api
    Coin findById(String id) throws Exception;//get coin from database
    String searchCoin(String keyword) throws Exception;
    String getTop50CoinByMarketCapRank() throws Exception;
    String getTrendingCoins() throws Exception;


}
