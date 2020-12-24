package toystore;

public class CurrencyNotFoundException extends Exception {

    public CurrencyNotFoundException() {
        super();
    }

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
