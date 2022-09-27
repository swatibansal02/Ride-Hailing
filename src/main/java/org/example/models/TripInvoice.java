package org.example.models;

import lombok.Builder;
import lombok.Data;
import org.example.utils.Constants;

@Builder
@Data
public class TripInvoice {

    @Builder.Default
    private Double totalPrice = Constants.MINIMUM_RIDE_PRICE;
    private Double discount;
    private Double priceAfterDiscount;
}
