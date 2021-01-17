package toystore.financial;

/**
 * Checked exception, thrown when trying to change {@link toystore.Store}'s field {@code currency} with a value
 * that is not in the map with all currencies from {@link Currency}. A currency is supposed to first be added in
 * the map, then used, using without adding will result in an unwanted behaviour.
 */

public class CurrencyNotFoundException extends Exception {

    /**
     * Calls superclass constructor
     * @param message error message
     */
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
