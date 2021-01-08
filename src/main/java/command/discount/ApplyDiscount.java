package command.discount;

import command.Command;
import org.apache.commons.lang3.EnumUtils;
import toystore.*;

import static java.lang.System.*;

public class ApplyDiscount implements Command {

    private final DiscountType type;
    private final double value;

    public ApplyDiscount(String type, String value) {
        this.type = EnumUtils.getEnum(DiscountType.class, type);
        this.value = Double.parseDouble(value);
    }

    @Override
    public void execute() {
        Discount discount = Discount.getDiscount(type, value);
        try {
            Store.getInstance().applyDiscount(discount);
        } catch (DiscountNotFoundException | NegativePriceException e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }
}
