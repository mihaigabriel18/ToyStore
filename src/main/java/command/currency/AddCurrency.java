package command.currency;

import command.Command;
import toystore.Store;

public class AddCurrency implements Command {

    private final String name;
    private final String symbol;
    private final double parityToEur;

    public AddCurrency(String name, String symbol, String parityToEur) {
        this.name = name;
        this.symbol = symbol;
        this.parityToEur = Double.parseDouble(parityToEur);
    }


    @Override
    public void execute() {
        Store.getInstance().createCurrency(name, symbol, parityToEur);
    }
}
