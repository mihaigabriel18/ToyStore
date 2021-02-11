package command.product;

import command.Command;
import toystore.Store;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will list all products created by the manufacturer given parameter,
 *     receives one parameter listed in the constructor
 * </p>
 */

public class ListProductsByManufacturer implements Command {

    /**
     * first parameter of command
     */
    private final String manufacturerName;

    /**
     * Initialize all class's fields
     * @param manufacturerName name of manufacturer
     */
    public ListProductsByManufacturer(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Store ourStore = Store.getInstance();
        ourStore.getProductsByManufacturer(ourStore.getManufacturerByName(manufacturerName));
    }
}
