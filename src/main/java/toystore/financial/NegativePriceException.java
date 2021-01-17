package toystore.financial;

/**
 * Checked exception used when applying a discount to a product and it's price becomes negative.
 * It can happen when applying a fixed discount (<em>"-10 RON"</em> on a product with <em>5 RON</em> price),
 * or when applying a percentage discount if the percentage value is not between 0 and 100 (<em>"-5%", "120%"</em>)
 */

public class NegativePriceException extends Exception {

    /**
     * Calls superclass constructor
     * @param message error message
     */
    public NegativePriceException(String message) {
        super(message);
    }
}
