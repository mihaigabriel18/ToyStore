package command.currency;

import command.Command;
import toystore.financial.Currency;

import static java.lang.System.*;

public class ListCurrencies implements Command {

    private void printCurrencies(Currency currency) {
        out.println(currency.getName() + " " + currency.getParityToEur());
    }

    @Override
    public void execute() {
        Currency.getAllCurrencies().values().forEach(this::printCurrencies);
    }
}
