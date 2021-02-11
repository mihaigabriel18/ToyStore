package command.currency;

import command.Command;
import toystore.Store;
import toystore.financial.Currency;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will print store currency's name, receives no parameters
 * </p>
 */

public class GetStoreCurrency implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Currency currentCurrency = Store.getInstance().getCurrency();
        out.println(currentCurrency.getName());
    }
}
