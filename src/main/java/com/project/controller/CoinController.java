package com.project.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Entity.Coin;
import com.project.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<List<Coin>> getCoinList(@RequestParam(required = false ,name="page")int page) throws Exception {
           List<Coin> coinList=coinService.getCoinList(page);
           return new ResponseEntity<>(coinList, HttpStatus.OK);
    }

    @GetMapping("/{coinId}/chart")
    ResponseEntity<JsonNode> getMarketChart(@PathVariable("coinId")String coinId, @RequestParam("days") int days) throws Exception {
        String marketChart=coinService.getMarketChart(coinId,days);
        JsonNode jsonNode=objectMapper.readTree(marketChart);
        return new ResponseEntity<>(jsonNode, HttpStatus.OK);
    }

    @GetMapping("/search")
    ResponseEntity<JsonNode> searchcoin(@RequestParam("q")String keyword) throws Exception {
        String coin=coinService.searchCoin(keyword);
        JsonNode jsonNode=objectMapper.readTree(coin);
        return new ResponseEntity<>(jsonNode,HttpStatus.OK);
    }

    @GetMapping("/top50")
    ResponseEntity<JsonNode> getTop50CoinBYMarketCap() throws Exception {
        String coin=coinService.getTop50CoinByMarketCapRank();
        JsonNode jsonNode=objectMapper.readTree(coin);
        return new  ResponseEntity<>(jsonNode,HttpStatus.OK);
    }

    @GetMapping("/trending")
    ResponseEntity<JsonNode> getCoinList() throws Exception {
          String coin=coinService.getTrendingCoins();
          JsonNode jsonNode=objectMapper.readTree(coin);
          return new ResponseEntity<>(jsonNode,HttpStatus.OK);
    }

    @GetMapping("/details/{coinId}")
    ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId) throws Exception {
        String coin=coinService.coinDetails(coinId);
        JsonNode jsonNode=objectMapper.readTree(coin);
        return ResponseEntity.ok(jsonNode);
    }

}
