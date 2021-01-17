package toystore.financial;

/**
 * Checked exception, is caused by {@link Currency}'s method {@code changeParity}, thrown when, for example
 * euro's parity is change relative to itself(1 euro can be only worth 1 euro, nothing less or more)
 */

public class CannotChangeParityException extends Exception {

    /**
     * Calls superclass constructor
     * @param message error message
     */
    public CannotChangeParityException(String message) {
        super(message);
    }
}
