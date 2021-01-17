package toystore.productline;

/**
 * Checked exception, used to assure uniqueness of {@link Product} objects, not allowing multiple
 * products of the same kind to exits in the singleton {@link toystore.Store}
 * Uniqueness is determined by the {@code uniq_id} field.
 */

public class DuplicateProductException extends Exception {

    /**
     * Calls superclass constructor
     * @param message error message
     */
    public DuplicateProductException(String message) {
        super(message);
    }
}
