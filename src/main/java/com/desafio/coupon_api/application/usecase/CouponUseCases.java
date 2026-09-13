package com.desafio.coupon_api.application.usecase;

import com.desafio.coupon_api.application.dto.CouponRequest;
import com.desafio.coupon_api.application.dto.CouponResponse;

import java.util.UUID;

public interface CouponUseCases {

    CouponResponse createCoupon(CouponRequest request);

    CouponResponse getCoupon(UUID id);

    void deleteCoupon(UUID id);
}