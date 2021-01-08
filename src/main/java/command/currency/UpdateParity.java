package command.currency;

import command.Command;
import toystore.Currency;

public class UpdateParity implements Command {

    private final String currencyName;
    private final double parityToEur;

    public UpdateParity(String currencyName, String parityToEur) {
        this.currencyName = currencyName;
        this.parityToEur = Double.parseDouble(parityToEur);
    }

    @Override
    public void execute() {
        Currency.getCurrencyByName(currencyName).updateParity(parityToEur);
    }
}
