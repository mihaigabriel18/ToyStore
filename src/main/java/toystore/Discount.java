package toystore;

import java.time.LocalDateTime;

public class Discount {

    String name;
    DiscountType discountType;
    double value;
    LocalDateTime lastDateApplied = null;
}
