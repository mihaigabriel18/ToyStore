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
    private static final Map<String, Currency> allCurrencies = new HashMap<>();

    public Currency(String name, String symbol, double parityToEur) {
        this.name = name;
        this.symbol = symbol;
        this.parityToEur = parityToEur;
    }

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

    public void updateParity(double parityToEur) {
        this.parityToEur = parityToEur;
    }

    public static Map<String, Currency> getAllCurrencies() {
        return allCurrencies;
    }

    public double convertPrice(String price) {
        return Double.parseDouble(price);
    }

    public double convertPrice(String price, Currency currency) {
        return convertPrice(price) / this.getParityToEur() * currency.getParityToEur();
    }

    public static void addCurrency(Currency currency) {
        allCurrencies.put(currency.getName(), currency);
    }

    public static boolean isValid(String name) {
        return allCurrencies.containsKey(name);
    }

    public static Currency getCurrencyByName(String name) {
        return allCurrencies.get(name);
    }

}
