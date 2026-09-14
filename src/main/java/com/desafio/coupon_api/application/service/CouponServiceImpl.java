package com.desafio.coupon_api.application.service;

import com.desafio.coupon_api.application.dto.CouponRequest;
import com.desafio.coupon_api.application.dto.CouponResponse;
import com.desafio.coupon_api.application.usecase.CouponUseCases;
import com.desafio.coupon_api.domain.entity.Coupon;
import com.desafio.coupon_api.domain.exceptions.CouponAlreadyExistsException;
import com.desafio.coupon_api.domain.exceptions.CouponNotFoundException;
import com.desafio.coupon_api.domain.repository.CouponRepository;
import com.desafio.coupon_api.infrastructure.persistence.mapper.CouponMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class CouponServiceImpl implements CouponUseCases {

    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;

    public CouponServiceImpl(CouponRepository couponRepository, CouponMapper couponMapper) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

    @Override
    @Transactional
    public CouponResponse createCoupon(CouponRequest request) {

        Coupon coupon = Coupon.create(
                request.code(),
                request.description(),
                request.discountValue(),
                request.expirationDate(),
                Boolean.TRUE.equals(request.published())
        );

        if (couponRepository.existsByCode(coupon.getCode())) {
            throw new CouponAlreadyExistsException(coupon.getCode());
        }

        Coupon savedCoupon = couponRepository.save(coupon);

        return toResponse(savedCoupon);
    }

    @Override
    @Transactional(readOnly = true)
    public CouponResponse getCoupon(UUID id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() ->
                        new CouponNotFoundException(
                                "Cupom não encontrado: " + id
                        )
                );

        return toResponse(coupon);
    }

    @Override
    @Transactional
    public void deleteCoupon(UUID id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() ->
                        new CouponNotFoundException(
                                "Cupom não encontrado: " + id
                        )
                );

        coupon.delete();

        couponRepository.save(coupon);
    }

    private CouponResponse toResponse(Coupon coupon) {

        return new CouponResponse(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.getStatus(),
                coupon.isPublished(),
                coupon.isRedeemed()
        );
    }
}