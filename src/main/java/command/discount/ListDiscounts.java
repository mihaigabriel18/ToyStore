package command.discount;

import command.Command;
import toystore.financial.Discount;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will list all store's discounts, receives no parameters
 * </p>
 */

public class ListDiscounts implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Discount.getAllDiscounts().values().forEach(out::println);
    }
}
