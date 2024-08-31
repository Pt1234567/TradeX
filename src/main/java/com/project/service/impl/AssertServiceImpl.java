package com.project.service.impl;

import com.project.Entity.Asset;
import com.project.Entity.Coin;
import com.project.Entity.User;
import com.project.Repository.AssetRepository;
import com.project.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssertServiceImpl implements AssetService {
    @Autowired
    private AssetRepository assetRepository;

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset=new Asset();
        asset.setCoin(coin);
        asset.setUser(user);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        return assetRepository.save(asset);
    }

    @Override
    public Asset getAssetById(Long assetId) throws Exception {
        return assetRepository.findById(assetId).orElseThrow(()->new Exception("Asset not found"));
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUsersAsset(Long userId) {
        return assetRepository.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {
        Optional<Asset> oldAsset=assetRepository.findById(assetId);
        if(oldAsset.isPresent()){
            oldAsset.get().setQuantity(quantity+oldAsset.get().getQuantity());
           return assetRepository.save(oldAsset.get());
        }
        throw  new Exception("Asset Not found");
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return assetRepository.findByUserIdAndCoinId(userId,coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
         assetRepository.deleteById(assetId);
    }
}
