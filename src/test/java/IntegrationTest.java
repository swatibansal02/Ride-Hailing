import org.example.core.CabFindingStrategy;
import org.example.core.DefaultCabFindingStrategy;
import org.example.core.PricingStrategy;
import org.example.core.TierBasedPricingStrategy;
import org.example.models.Cab;
import org.example.models.CabType;
import org.example.models.Coupon;
import org.example.models.Driver;
import org.example.models.Location;
import org.example.models.Rider;
import org.example.models.Trip;
import org.example.repo.BookingRepo;
import org.example.repo.CabRepo;
import org.example.repo.DriverRepo;
import org.example.repo.RiderRepo;
import org.example.service.BookingService;
import org.example.service.CabService;
import org.example.service.CouponService;
import org.example.service.DriverService;
import org.example.service.RiderService;
import org.example.service.impl.CouponServiceImpl;
import org.example.service.impl.TripServiceImpl;
import org.example.service.impl.CabServiceImpl;
import org.example.service.impl.DriverServiceImpl;
import org.example.service.impl.RiderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class IntegrationTest {

    private static final String DRIVER_1 = "Driver-1";
    private static final String DRIVER_2 = "Driver-2";
    private static final String RIDER_1 = "Rider-1";

    private RiderService riderService;
    private DriverService driverService;
    private CabService cabService;
    private CabFindingStrategy cabFindingStrategy;
    private BookingService bookingService;
    private PricingStrategy pricingStrategy;
    private CouponService couponService;


    @BeforeEach
    void setUp() {
        RiderRepo riderRepo = new RiderRepo();
        riderService = new RiderServiceImpl(riderRepo);
        CabRepo cabRepo = new CabRepo();
        cabFindingStrategy = new DefaultCabFindingStrategy();
        cabService = new CabServiceImpl(cabRepo, cabFindingStrategy);
        DriverRepo driverRepo = new DriverRepo();
        driverService = new DriverServiceImpl(driverRepo, cabService);

        pricingStrategy = new TierBasedPricingStrategy();
        BookingRepo bookingRepo = new BookingRepo();
        bookingService = new TripServiceImpl(cabService, pricingStrategy, bookingRepo);
        couponService = new CouponServiceImpl();
    }

    @Test
    void createRide() {
        Rider rider = Rider.builder()
                .name(RIDER_1)
                .contactNumber("8708394297")
                .location(location(1.2, 2.0))
                .build();
        riderService.register(rider);

        System.out.println("rider Details: " + riderService.getRider("8708394297").toString());
    }


    @Test
    void createDriver() {
        Driver driver1 = Driver.builder()
                .name(DRIVER_1)
                .contactNumber("9708394299")
                .cab(buildCab("1122", location(1.0, 2.0)))
                .build();

        driverService.registerDriver(driver1);

        System.out.println("driver 1: " + driverService.getDriver("9708394299").toString());

        Driver driver2 = Driver.builder()
                .name(DRIVER_2)
                .contactNumber("9908394299")
                .cab(buildCab("1133", location(1.0, 3.0)))
                .build();

        driverService.registerDriver(driver2);

        System.out.println("driver 1: " + driverService.getDriver("9908394299").toString());
    }

    @Test
    void Test() {
        createRide();
        createDriver();

        List<Coupon> coupons = couponService.availableCoupons();
        double discount = couponService.applyCouponDiscount(coupons.get(0));
        System.out.println("discount :" + discount);

        {
             // test case for assign another cab type if current not available
            Trip trip2 = bookingService.createTrip("8708394297", CabType.Sedan, location(0.2, 2.0), location(2.2, 4.0), 0.0);
            System.out.println("trip1 distance:" + trip2.getDistance());
            System.out.println("trip1 price:" + trip2.getInvoice().toString());

            bookingService.endTrip(trip2.getTripId());

            System.out.println("--rider history--");
            System.out.println(bookingService.getRideHistory("8708394297").toString());
        }

//        {
//            //trip without discount
//            Trip trip2 = bookingService.createTrip("8708394297", CabType.Hatchback, location(0.1, 2.0), location(2.2, 4.0), 0.0);
//            System.out.println("trip2 distance:" + trip2.getDistance());
//            System.out.println("trip2 price:" + trip2.getInvoice().toString()); //50 + 10*2 + .9*8
//
//            bookingService.endTrip(trip2.getTripId());
//
//            System.out.println("--rider history--");
//            System.out.println(bookingService.getRideHistory("8708394297").toString());
//            System.out.println("--driver1 history--");
//            System.out.println(bookingService.driverBookingHistory("9708394299").toString());
//            System.out.println("--driver2 history--");
//            System.out.println(bookingService.driverBookingHistory("9908394299").toString());
//        }
//        System.out.println("----------");
//
//        {
//            //trip with discount
//            Trip trip3 = bookingService.createTrip("8708394297", CabType.Hatchback, location(0.1, 2.0), location(2.2, 4.0), discount);
//            System.out.println("trip3 distance:" + trip3.getDistance());
//            System.out.println("trip3 price:" + trip3.getInvoice().toString());
//
//            System.out.println("--rider history--");
//            System.out.println(bookingService.getRideHistory("8708394297"));
//            System.out.println("--driver1 history--");
//            System.out.println(bookingService.driverBookingHistory("9708394299"));
//            System.out.println("--driver2 history--");
//            System.out.println(bookingService.driverBookingHistory("9908394299"));
//        }
    }


    Location location(Double from, Double to) {
        return Location.builder()
                .gridId("1")
                .x(from)
                .y(to)
                .build();
    }

    Cab buildCab(String vehicleNo, Location location) {
        return Cab.builder()
                .vehicleNumber(vehicleNo)
                .cabType(CabType.Hatchback)
                .currentLocation(location)
                .isAvailable(true)
                .build();
    }

}
