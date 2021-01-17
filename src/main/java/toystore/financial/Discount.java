package toystore.financial;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.javatuples.*;

/**
 * Used to describe a discount, with a name, type (either fixed discount: <em>"-10 RON"</em>,
 * or percentage one: <em>"-15% BLACK FRIDAY"</em>), value, and date of appliance, which is initially
 * <em>null</em> and later change to current date.
 * <p>
 * The class also keeps track of all discounts added so far, storing them inside a map, using a
 * pair of {@link #discountType} and {@link #value} as key, since those 2 determine an unique discount,
 * and returns a {@link Discount} object as value. The map is updated and queried using public methods
 * since it's a private collection. The pair used to create a tuple of those 2 elements is from the
 * {@link org.javatuples} library.
 * <p>
 * Class implements {@linkplain Serializable}, since the singleton {@link toystore.Store} wants to serialize
 * everything it contains.
 */

public class Discount implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Name of the discount, can only be initialized once, in the constructor
     */
    private final String name;

    /**
     * Type of the discount, either a fixed one, or percentage one
     */
    private final DiscountType discountType;

    /**
     * How big is the discount
     */
    private double value;

    /**
     * Timestamp for when the discount was applied
     */
    private LocalDateTime lastDateApplied;

    /**
     * Map of discounts that takes a pair of discount types and discount values and returns the {@link Discount}
     * object with that type and value (The discount type and value determine an unique of the discount)
     * The {@link Pair} is used from the library {@link org.javatuples}
     */
    private static final Map<Pair<DiscountType, Double>, Discount> allDiscounts = new HashMap<>();

    /**
     * Initializes all non-static fields of the class, the last date it was applied is initialized to null,
     * because the discount hasn't been applied yet
     * @param name name of the discount
     * @param discountType enum type of the discount
     * @param value value of discount
     */
    public Discount(String name, DiscountType discountType, double value) {
        this.name = name;
        this.discountType = discountType;
        this.value = value;
        this.lastDateApplied = null;
    }

    /**
     * @return Name of discount
     */
    public String getName() {
        return name;
    }

    /**
     * @return type of discount (Fixed/Percentage)
     */
    public DiscountType getDiscountType() {
        return discountType;
    }

    /**
     * @return value of discount(any positive number for fixed/ 0-100 for percentage)
     */
    public double getValue() {
        return value;
    }

    /**
     * @return date when the discount was last applied
     */
    public LocalDateTime getLastDateApplied() {
        return lastDateApplied;
    }

    /**
     * @return return the map with all discounts added to this moment
     */
    public static Map<Pair<DiscountType, Double>, Discount> getAllDiscounts() {
        return allDiscounts;
    }

    /**
     * @param value new value for the discount
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Called when the discount is applied, so that the date is updated the current one
     * @param lastDateApplied date when the discount is applied (current date in this case)
     */
    public void setLastDateApplied(LocalDateTime lastDateApplied) {
        this.lastDateApplied = lastDateApplied;
    }

    /**
     * Set the date when the discount is applied as now
     */
    public void setAsAppliedNow() {
        setLastDateApplied(LocalDateTime.now());
    }

    /**
     * Adds a new discount to the collection of all possible discounts
     * @param discount discount to add to the map
     */
    public static void addDiscount(Discount discount) {
        allDiscounts.put(new Pair<>(discount.discountType, discount.value), discount);
    }

    /**
     * Retrieve the discount by it's type and value, combined they call for an unique discount
     * @param discountType type of discount
     * @param value value of discount
     * @return the {@link Discount} that has the fields described above if it exists in the map,
     *         <em>null</em> otherwise
     */
    public static Discount getDiscount(DiscountType discountType, Double value) {
        return allDiscounts.get(new Pair<>(discountType, value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return discountType.name() + " " +
                String.format("%,.3f", value) + " " +
                name + " " +
                ((lastDateApplied == null) ? "Not applied yet" : lastDateApplied.toString());
    }
}
