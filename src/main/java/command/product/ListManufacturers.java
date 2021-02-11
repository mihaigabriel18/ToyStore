package command.product;

import command.Command;
import toystore.productline.*;
import toystore.Store;
import java.util.List;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will list all {@link Store}'s manufacturers, receives no parameters
 * </p>
 */

public class ListManufacturers implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        List<Manufacturer> manufacturersList = Store.getInstance().getListOfOrderedManufacturers();
        manufacturersList.forEach(out::println);
    }
}
