package command.currency;

import command.Command;
import toystore.Store;
import toystore.financial.CurrencyNotFoundException;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will replace current store's currency with the one specified, receives one parameter
 * </p>
 */

public class SetStoreCurrency implements Command {

    /**
     * first parameter of command
     */
    private final String currencyName;

    /**
     * Initialize all class's fields
     * @param currencyName name of currency
     */
    public SetStoreCurrency(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        try {
            Store.getInstance().changeCurrency(currencyName);
        } catch (CurrencyNotFoundException e) {
            e.printStackTrace();
            out.println("Currency was not found");
        }
    }
}
