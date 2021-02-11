package command.discount;

import command.Command;
import org.apache.commons.lang3.EnumUtils;
import toystore.Store;
import toystore.financial.DiscountType;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will add a discount to the store, receives three parameters listed in the constructor
 * </p>
 */

public class AddDiscount implements Command {

    /**
     * first parameter of command
     */
    private final DiscountType discountType;

    /**
     * second parameter of command
     */
    private final double value;

    /**
     * third parameter of command
     */
    private final String name;

    /**
     * Initialize all class's fields
     * @param discountType type of discount
     * @param value string value of discount
     * @param name name of discount
     */
    public AddDiscount(String discountType, String value, String name) {
        // get enum item by name
        this.discountType = EnumUtils.getEnum(DiscountType.class, discountType + "_DISCOUNT");
        this.value = Double.parseDouble(value);
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Store.getInstance().createDiscount(discountType, name, value);
    }
}
