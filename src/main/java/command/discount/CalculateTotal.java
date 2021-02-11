package command.discount;

import command.Command;
import toystore.Store;
import toystore.productline.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will calculate the total price of products with id's given parameter,
 *     receives one parameters listed in the constructor, that is going to be split into an array.
 *     This command basically has a variable number of parameters.
 * </p>
 */

public class CalculateTotal implements Command {

    /**
     * first parameter of command
     */
    private final List<String> uniqIds;

    /**
     * Initialize all class's fields, by splitting the string by space
     * @param ids String with multiple discounts, separated by a space
     */
    public CalculateTotal(String ids) {
        this.uniqIds = Arrays.asList(ids.split(" "));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Store ourStore = Store.getInstance();
        List<Product> productList = uniqIds.stream().map(ourStore::getProductById).collect(Collectors.toList());
        try {
            out.println(ourStore.getCurrency().getSymbol() + String.format("%,.3f", ourStore.calculateTotal(productList)));
        } catch (RequestTooManyProducts e) {
            e.printStackTrace();
            out.println("Requested too many products of the same type");
        }
    }
}
