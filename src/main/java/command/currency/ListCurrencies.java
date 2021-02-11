package command.currency;

import command.Command;
import toystore.financial.Currency;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will list all store's currencies, receives no parameters
 * </p>
 */

public class ListCurrencies implements Command {

    /**
     * Custom method to print currencies
     * @param currency object to print
     */
    private void printCurrencies(Currency currency) {
        out.println(currency.getName() + " " + currency.getParityToEur());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Currency.getAllCurrencies().values().forEach(this::printCurrencies);
    }
}
