package toystore;

public class DuplicateProductException extends Exception {

    public DuplicateProductException() {
        super();
    }

    public DuplicateProductException(String message) {
        super(message);
    }
}
