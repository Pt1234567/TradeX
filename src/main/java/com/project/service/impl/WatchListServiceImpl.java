package com.project.service.impl;

import com.project.Entity.Coin;
import com.project.Entity.User;
import com.project.Entity.WatchList;
import com.project.Repository.WatchListRepository;
import com.project.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServiceImpl implements WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;

    @Override
    public WatchList findUserWatchList(Long userId) throws Exception {
        WatchList watchList= watchListRepository.findByUserId(userId);
        if(watchList==null){
            throw  new Exception("Watchlist Not found");
        }
        return watchList;
    }

    @Override
    public WatchList createWatchList(User user) {
        WatchList watchList=new WatchList();
        watchList.setUser(user);

        return watchListRepository.save(watchList);
    }

    @Override
    public WatchList findById(Long watchListId) throws Exception {
        Optional<WatchList> optional=watchListRepository.findById(watchListId);
        if(optional.isEmpty()){
            throw  new Exception("WatchList not found");
        }
        return optional.get();
    }

    @Override
    public Coin addItemToWatchList(Coin coin, User user) {
        WatchList watchList=watchListRepository.findByUserId(user.getId());
        if(watchList.getCoins().contains(coin)){
            watchList.getCoins().remove(coin);
        }else{
            watchList.getCoins().add(coin);
        }

         watchListRepository.save(watchList);
        return coin;
    }
}
