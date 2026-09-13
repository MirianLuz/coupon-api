package com.desafio.coupon_api.presentation.controller;

import com.desafio.coupon_api.application.dto.CouponRequest;
import com.desafio.coupon_api.application.dto.CouponResponse;
import com.desafio.coupon_api.application.usecase.CouponUseCases;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponUseCases couponUseCases;

    public CouponController(CouponUseCases couponUseCases) {
        this.couponUseCases = couponUseCases;
    }

    @PostMapping
    public ResponseEntity<CouponResponse> create(@Valid @RequestBody CouponRequest request) {

        CouponResponse response = couponUseCases.createCoupon(request);

        return ResponseEntity
                .created(URI.create("/coupon/" + response.id()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponse> getCoupon(@PathVariable UUID id) {

        return ResponseEntity.ok(
                couponUseCases.getCoupon(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable UUID id) {
        couponUseCases.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
}