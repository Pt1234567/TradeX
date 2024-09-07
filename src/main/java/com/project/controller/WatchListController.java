package com.project.controller;

import com.project.Entity.Coin;
import com.project.Entity.User;
import com.project.Entity.WatchList;
import com.project.service.CoinService;
import com.project.service.UserService;
import com.project.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);
        WatchList watchList=watchListService.findUserWatchList(user.getId());
        return new ResponseEntity<>(watchList, HttpStatus.OK);
    }

    @GetMapping("/{watchListId}")
    public ResponseEntity<WatchList> getWatchListById(@PathVariable Long watchListId) throws Exception {
          WatchList watchList1=watchListService.findById(watchListId);
          return ResponseEntity.ok(watchList1);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin>  addItemToWatchList(@RequestHeader("Authorization") String jwt,@PathVariable String coinId) throws Exception {
        Coin coin=coinService.findById(coinId);
        User user=userService.findUserByJwt(jwt);
        Coin addedCoin=watchListService.addItemToWatchList(coin,user);
        return ResponseEntity.ok(addedCoin);
    }



}
