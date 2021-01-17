package toystore.productline;

/**
 * Checked exception, used to assure uniqueness of {@link Manufacturer} objects, not allowing multiple
 * manufacturers of the same kind to exits in the singleton {@link toystore.Store}
 * Uniqueness is determined by the {@code name} of the manufacturer.
 */

public class DuplicateManufacturerException extends Exception {

    /**
     * Calls superclass constructor
     * @param message error message
     */
    public DuplicateManufacturerException(String message) {
        super(message);
    }
}
