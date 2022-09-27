package org.example.service.impl;

import org.example.exceptions.CouponNotValid;
import org.example.models.Coupon;
import org.example.service.CouponService;

import java.util.ArrayList;
import java.util.List;

public class CouponServiceImpl implements CouponService {

    private final List<Coupon> coupons = new ArrayList<>();

    @Override
    public List<Coupon> availableCoupons() {
        //storing some static values as of now
        Coupon coupon1 = new Coupon("Rs50Off", 50.0);
        Coupon coupon2 = new Coupon("Rs20Off", 20.0);
        coupons.add(coupon2);
        coupons.add(coupon1);
        return coupons;
    }

    @Override
    public double applyCouponDiscount(Coupon coupon) {
        boolean isValidCoupon = false;
        for(Coupon c : coupons){
            if(c.equals(coupon)){
                isValidCoupon = true;
                break;
            }
        }
        if(isValidCoupon){
            return coupon.getDiscount();
        }
        throw new CouponNotValid();
    }

    @Override
    public void deleteCoupon(Coupon coupon) {

    }
}
