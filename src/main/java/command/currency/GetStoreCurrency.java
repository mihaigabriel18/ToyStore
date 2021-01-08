package command.currency;

import command.Command;
import toystore.Currency;
import toystore.Store;

import static java.lang.System.*;

public class GetStoreCurrency implements Command {

    @Override
    public void execute() {
        Currency currentCurrency = Store.getInstance().getCurrency();
        out.println(currentCurrency.getName());
    }
}
