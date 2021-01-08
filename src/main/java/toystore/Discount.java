package toystore;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.javatuples.*;

public class Discount implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    private String name;
    private DiscountType discountType;
    private double value;
    private LocalDateTime lastDateApplied = null;
    private static final Map<Pair<DiscountType, Double>, Discount> allDiscounts = new HashMap<>();

    public Discount(String name, DiscountType discountType, double value) {
        this.name = name;
        this.discountType = discountType;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getValue() {
        return value;
    }

    public LocalDateTime getLastDateApplied() {
        return lastDateApplied;
    }

    public static Map<Pair<DiscountType, Double>, Discount> getAllDiscounts() {
        return allDiscounts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setLastDateApplied(LocalDateTime lastDateApplied) {
        this.lastDateApplied = lastDateApplied;
    }

    public void setAsAppliedNow() {
        this.lastDateApplied = LocalDateTime.now();
    }

    public static void addDiscount(Discount discount) {
        allDiscounts.put(new Pair<>(discount.discountType, discount.value), discount);
    }

    public static Discount getDiscount(DiscountType discountType, Double value) {
        return allDiscounts.get(new Pair<>(discountType, value));
    }

    @Override
    public String toString() {
        return discountType.name() + " " +
                value + " " +
                name + " " +
                lastDateApplied;
    }
}
