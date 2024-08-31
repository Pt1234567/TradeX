package com.project.service;

import com.project.Entity.Coin;
import com.project.Entity.User;
import com.project.Entity.WatchList;

public interface WatchListService {

    WatchList findUserWatchList(Long userId) throws Exception;
    WatchList createWatchList(User user);
    WatchList findById(Long watchListId) throws Exception;
    Coin addItemToWatchList(Coin coin,User user);

}
