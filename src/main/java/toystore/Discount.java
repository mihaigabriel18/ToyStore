package toystore;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Discount implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    private String name;
    private DiscountType discountType;
    private double value;
    private LocalDateTime lastDateApplied = null;

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
}
