package command.currency;

import command.Command;
import toystore.Store;
import toystore.financial.Currency;

import static java.lang.System.*;

public class GetStoreCurrency implements Command {

    @Override
    public void execute() {
        Currency currentCurrency = Store.getInstance().getCurrency();
        out.println(currentCurrency.getName());
    }
}
