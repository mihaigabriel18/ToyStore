package command.discount;

import command.Command;
import org.apache.commons.lang3.EnumUtils;
import org.javatuples.*;
import toystore.*;
import toystore.financial.*;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will apply a discount to the store's products,
 *     receives two parameters listed in the constructor
 * </p>
 */

public class ApplyDiscount implements Command {

    /**
     * first parameter of command
     */
    private final DiscountType type;

    /**
     * second parameter of command
     */
    private final double value;

    /**
     * Initialize all class's fields
     * @param type string type of discount
     * @param value string value of discount
     */
    public ApplyDiscount(String type, String value) {
        this.type = EnumUtils.getEnum(DiscountType.class, type + "_DISCOUNT");
        this.value = Double.parseDouble(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        try {
            Store.getInstance().applyDiscount(type, value);
        } catch (DiscountNotFoundException | NegativePriceException e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }
}