package toystore.financial;

/**
 * Checked exception, thrown when trying to apply a {@link Discount} that is not yet added in the
 * map with all of the discounts. A discount is supposed to first be added in the map, then applied,
 * using a discount without adding it first is an unwanted behaviour
 */

public class DiscountNotFoundException extends Exception {

    /**
     * Calls superclass constructor
     * @param message error message
     */
    public DiscountNotFoundException(String message) {
        super(message);
    }
}
