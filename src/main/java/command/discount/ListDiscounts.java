package command.discount;

import command.Command;
import toystore.financial.Discount;

import static java.lang.System.*;

public class ListDiscounts implements Command {

    @Override
    public void execute() {
        Discount.getAllDiscounts().values().forEach(out::println);
    }
}
