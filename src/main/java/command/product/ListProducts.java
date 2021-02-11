package command.product;

import command.Command;
import toystore.Store;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will list all {@link Store}'s products, receives no parameters
 * </p>
 */

public class ListProducts implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Store.getInstance().getProducts().forEach(out::println);
    }
}
