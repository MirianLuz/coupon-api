package com.desafio.coupon_api.infrastructure.persistence.repository;

import com.desafio.coupon_api.infrastructure.persistence.entity.JpaCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaCouponRepository extends JpaRepository<JpaCouponEntity, UUID> {

}