package com.example.paurusdemo.repository;

import com.example.paurusdemo.repository.dao.Trader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraderRepository extends JpaRepository<Trader, Long> {

}
