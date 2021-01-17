package command.currency;

import command.Command;
import toystore.financial.CannotChangeParityException;
import toystore.financial.Currency;

import static java.lang.System.*;

public class UpdateParity implements Command {

    private final String currencyName;
    private final double parityToEur;

    public UpdateParity(String currencyName, String parityToEur) {
        this.currencyName = currencyName;
        this.parityToEur = Double.parseDouble(parityToEur);
    }

    @Override
    public void execute() {
        try {
            Currency.getCurrencyByName(currencyName).updateParity(parityToEur);
        } catch (CannotChangeParityException e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }
}
