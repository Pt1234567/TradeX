package com.project.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Coin {

    @JsonProperty("id")
    @Id
    private String id;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("name")
    private String name;

    @JsonProperty("image")
    private String image;

    @JsonProperty("current_price")
    private Double currentPrice;

    @JsonProperty("market_cap")
    private Double marketCap;

    @JsonProperty("market_cap_rank")
    private Double marketCapRank;

    @JsonProperty("fully_diluted_valuation")
    private Double fullyDilutedValuation;

    @JsonProperty("total_volume")
    private Double totalVolume;

    @JsonProperty("high_24h")
    private Double high24h;

    @JsonProperty("low_24h")
    private Double low24h;

    @JsonProperty("price_change_24h")
    private Double priceChange24h;

    @JsonProperty("price_change_percentage_24h")
    private Double priceChangePercentage24h;

    @JsonProperty("market_cap_change_24h")
    private Double marketCapChange24h;

    @JsonProperty("market_cap_change_percentage_24h")
    private Double marketCapChangePercentage24h;

    @JsonProperty("circulating_supply")
    private Double circulatingSupply;

    @JsonProperty("total_supply")
    private Double totalSupply;

    @JsonProperty("max_supply")
    private Double maxSupply;

    @JsonProperty("ath")
    private Long ath;

    @JsonProperty("ath_change_percentage")
    private Double athChangePercentage;

    @JsonProperty("ath_date")
    private String athDate;

    @JsonProperty("atl")
    private Double atl;

    @JsonProperty("atl_change_percentage")
    private Double atlChangePercentage;

    @JsonProperty("atl_date")
    private String atlDate;

    @JsonProperty("last_updated")
    private String lastUpdated;
}
