package toystore.productline;

/**
 * Checked exception that is thrown when the user requests more products than the quantity
 * the {@link toystore.Store} has of that product. For example if the {@code calculateTotal}
 * method requests 6 products with id <em>eac7efa5dbd3d667f26eb3d3ab504464</em>, and the store
 * has only 5 of those, the exception will be thrown
 */

public class RequestTooManyProducts extends Exception {

    /**
     * Calls superclass constructor
     * @param message error message
     */
    public RequestTooManyProducts(String message) {
        super(message);
    }
}
