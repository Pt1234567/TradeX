package com.project.Repository;

import com.project.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long>
{
    Wallet findByUserId(Long userId);
}
