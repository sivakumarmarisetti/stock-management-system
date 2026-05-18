package com.stockmanagement.repository;

import com.stockmanagement.entity.LoginTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginTrackingRepository
        extends JpaRepository<LoginTracking, Long> {
}