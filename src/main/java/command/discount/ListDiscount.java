package command.discount;

import command.Command;
import toystore.Discount;

import static java.lang.System.*;

public class ListDiscount implements Command {

    @Override
    public void execute() {
        Discount.getAllDiscounts().values().forEach(out::println);
    }
}
