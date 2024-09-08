package com.project.Repository;

import com.project.Entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Long> {
}
