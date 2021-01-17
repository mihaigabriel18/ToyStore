package toystore.financial;

/**
 * Enumeration of all possible discount types
 */
public enum DiscountType {

    /**
     * used to decrease the price with a percentage, for example: <em>"-15%"</em>, <em>"-50%"</em>
     */
    PERCENTAGE_DISCOUNT,

    /**
     * used to subtract a fixed value from a price, for example: <em>"-5 RON"</em>, <em>"-10 EURO" </em>
     */
    FIXED_DISCOUNT
}
