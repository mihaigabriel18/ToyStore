package command.product;

import command.Command;
import toystore.Store;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will print a product characteristics based on it's id,
 *     receives one parameter listed in the constructor
 * </p>
 */

public class ShowProduct implements Command {

    /**
     * first parameter of command
     */
    private final String uniqId;

    /**
     * Initialize all class's fields
     * @param uniqId unique identifier for each product
     */
    public ShowProduct(String uniqId) {
        this.uniqId = uniqId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        out.println(Store.getInstance().getProductById(uniqId));
    }
}