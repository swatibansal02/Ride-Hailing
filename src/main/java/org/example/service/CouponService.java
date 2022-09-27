package org.example.service;

import org.example.models.Coupon;

import java.util.List;

public interface CouponService {

    List<Coupon> availableCoupons();

    double applyCouponDiscount(Coupon coupon);

    void deleteCoupon(Coupon coupon);
}
