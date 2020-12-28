package toystore;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Currency implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    private String name;
    private String symbol;
    private double parityToEur;
    private static Map<String, Currency> allCurrencies = new HashMap<>();

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getParityToEur() {
        return parityToEur;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setParityToEur(double parityToEur) {
        this.parityToEur = parityToEur;
    }

    void updateParity(double parityToEur) {
        this.parityToEur = parityToEur;
    }

    public static void addCurrency(Currency currency) {
        allCurrencies.put(currency.getName(), currency);
    }
}
