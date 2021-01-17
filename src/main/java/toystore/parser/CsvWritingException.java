package toystore.parser;

/**
 * Unchecked exception used to generify csv writing exceptions, in the {@linkplain ParserCSV} class
 */

public class CsvWritingException extends RuntimeException {

    /**
     * Calls superclass constructor
     * @param message error message
     * @param cause actual cause of the exception
     */
    public CsvWritingException(String message, Throwable cause) {
        super(message, cause);
    }
}
