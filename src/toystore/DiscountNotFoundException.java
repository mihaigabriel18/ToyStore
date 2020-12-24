package toystore;

public class DiscountNotFoundException extends Exception {

    public DiscountNotFoundException() {
        super();
    }

    public DiscountNotFoundException(String message) {
        super(message);
    }
}
