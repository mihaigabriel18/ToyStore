package command.currency;

import command.Command;
import toystore.Store;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will add a currency to the store, receives three parameters listed in the constructor
 * </p>
 */

public class AddCurrency implements Command {

    /**
     * first parameter of command
     */
    private final String name;

    /**
     * second parameter of command
     */
    private final String symbol;

    /**
     * third parameter of command
     */
    private final double parityToEur;

    /**
     * Initialize all class's fields
     * @param name name of currency
     * @param symbol currency's symbol
     * @param parityToEur parity relative to euro
     */
    public AddCurrency(String name, String symbol, String parityToEur) {
        this.name = name;
        this.symbol = symbol;
        this.parityToEur = Double.parseDouble(parityToEur);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Store.getInstance().createCurrency(name, symbol, parityToEur);
    }
}
