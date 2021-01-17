package command.discount;

import command.Command;
import org.apache.commons.lang3.EnumUtils;
import toystore.Store;
import toystore.financial.DiscountType;

public class AddDiscount implements Command {

    private final DiscountType discountType;
    private final double value;
    private final String name;

    public AddDiscount(String discountType, String value, String name) {
        // get enum item by name
        this.discountType = EnumUtils.getEnum(DiscountType.class, discountType + "_DISCOUNT");
        this.value = Double.parseDouble(value);
        this.name = name;
    }

    @Override
    public void execute() {
        Store.getInstance().createDiscount(discountType, name, value);
    }
}
