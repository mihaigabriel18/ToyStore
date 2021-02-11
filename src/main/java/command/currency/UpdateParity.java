package command.currency;

import command.Command;
import toystore.financial.CannotChangeParityException;
import toystore.financial.Currency;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will update a currency's parity, receives two parameters listed in the constructor
 * </p>
 */

public class UpdateParity implements Command {

    /**
     * first parameter of command
     */
    private final String currencyName;

    /**
     * second parameter of command
     */
    private final double parityToEur;

    /**
     * Initialize all class's fields
     * @param currencyName name of currency
     * @param parityToEur string value for parity
     */
    public UpdateParity(String currencyName, String parityToEur) {
        this.currencyName = currencyName;
        this.parityToEur = Double.parseDouble(parityToEur);
    }

    /**
     * {@inheritDoc}
     */
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
