package org.example.core;

import org.example.utils.Constants;

public class TierBasedPricingStrategy implements PricingStrategy {

    @Override
    public double calculatePrice(double distance) {
        return Constants.MINIMUM_RIDE_PRICE + calculateTier1Price(distance);
    }

    //implemented chain pattern for tier based pricing
    private double calculateTier1Price(double distance) {
        if (distance > 2) {
            return calculateTier2Price(distance - 2) + 2 * Constants.DISTANCE_TIER_1_PRICE;
        } else if (distance <= 2) {
            return distance * Constants.DISTANCE_TIER_1_PRICE;
        }
        return 0.0;
    }

    private static double calculateTier2Price(double distance) {
        if (distance > 3) {
            return calculateTier3Price(distance - 3) + 3 * Constants.DISTANCE_TIER_2_PRICE;
        } else if (distance <= 3) {
            return distance * Constants.DISTANCE_TIER_2_PRICE;
        }
        return 0.0;
    }

    private static double calculateTier3Price(double distance) {
        return distance * Constants.DISTANCE_TIER_3_PRICE;
    }
}
