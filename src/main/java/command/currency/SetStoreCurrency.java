package command.currency;

import command.Command;
import toystore.CurrencyNotFoundException;
import toystore.Store;

public class SetStoreCurrency implements Command {

    private final String currencyName;

    public SetStoreCurrency(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public void execute() {
        try {
            Store.getInstance().changeCurrency(currencyName);
        } catch (CurrencyNotFoundException e) {
            e.printStackTrace();
            System.out.println("Currency was not found");
        }
    }
}