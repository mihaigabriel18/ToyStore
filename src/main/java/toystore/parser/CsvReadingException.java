package toystore.parser;

/**
 * Unchecked exception used to generify csv reading exceptions, in the {@linkplain ParserCSV} class
 */

public class CsvReadingException extends RuntimeException {

    /**
     * Calls superclass constructor
     * @param message error message
     * @param cause actual cause of the exception
     */
    public CsvReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
