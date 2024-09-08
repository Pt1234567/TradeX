package com.project.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Entity.Coin;
import com.project.Repository.CoinRepository;
import com.project.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Coin> getCoinList(int page) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/markets?vs_currency=inr&per_page=10&page="+page;

        RestTemplate template=new RestTemplate();

        try{
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<String>("parameters",headers);
            ResponseEntity<String> response=template.exchange(url, HttpMethod.GET,entity,String.class);
            List<Coin> coinList=objectMapper.readValue(response.getBody(),
                    new TypeReference<List<Coin>>() {
                    });
            return coinList;
        }catch (HttpClientErrorException | HttpServerErrorException e){
              throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getMarketChart(String coinId, int days) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/"+coinId+"/market_chart?vs_currency=inr&days="+days;

        RestTemplate template=new RestTemplate();

        try{
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<String>("parameters",headers);
            ResponseEntity<String> response=template.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();

        }catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String coinDetails(String coinId) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/"+coinId;

        RestTemplate template=new RestTemplate();

        try{
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<String>("parameters",headers);
            ResponseEntity<String> response=template.exchange(url, HttpMethod.GET,entity,String.class);

            JsonNode node=objectMapper.readTree(response.getBody());

            Coin coin=new Coin();
            coin.setId(node.get("id").asText());
            coin.setName(node.get("name").asText());
            coin.setSymbol(node.get("symbol").asText());
            coin.setImage(node.get("image").get("large").asText());


            JsonNode marketData=node.get("market_data");
                coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
                coin.setMarketCap(marketData.get("market_cap").get("usd").asDouble());
                coin.setMarketCapRank(marketData.get("market_cap_rank").asDouble());
                coin.setTotalVolume(marketData.get("total_volume").asDouble());
                coin.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
                coin.setLow24h(marketData.get("low_24h").get("usd").asDouble());
                coin.setPriceChange24h(marketData.get("price_change_24h").asDouble());
                coin.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble());
                coin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asDouble());
                coin.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").asDouble());
                coin.setTotalSupply(marketData.get("total_supply").asDouble());
            coinRepository.save(coin);

            return response.getBody();

        }catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Coin findById(String id) throws Exception {
        Optional<Coin> optional=coinRepository.findById(id);
        if(optional.isEmpty()) throw new Exception("Coin not found");
        return optional.get();
    }

    @Override
    public String searchCoin(String keyword) throws Exception {
        String url="https://api.coingecko.com/api/v3/search?query="+keyword;

        RestTemplate template=new RestTemplate();

        try{
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<String>("parameters",headers);
            ResponseEntity<String> response=template.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();

        }catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getTop50CoinByMarketCapRank() throws Exception {

        String url="https://api.coingecko.com/api/v3/coins/markets?vs_currency=inr&per_page=50&page=1";

        RestTemplate template=new RestTemplate();

        try{
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<String>("parameters",headers);
            ResponseEntity<String> response=template.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();

        }catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getTrendingCoins() throws Exception {
        String url="https://api.coingecko.com/api/v3/search/trending";

        RestTemplate template=new RestTemplate();

        try{
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<String>("parameters",headers);
            ResponseEntity<String> response=template.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();

        }catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }
}
