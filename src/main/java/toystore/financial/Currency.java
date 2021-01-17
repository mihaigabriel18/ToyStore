package toystore.financial;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import toystore.*;
import toystore.productline.*;

/**
 * Used to describe a currency, with a name(RON, EUR), symbol($, £), and parity relative to EURO (GBP has 1.1 parity
 * because a GBP is worth 1.1 EURO). Controls operations between prices and currencies, such as: changing prices
 * based on parity, updating parity and changing prices of {@link Product} objects from the singleton {@link Store}
 * <p>
 * Holds a static map that keeps track of all valid currencies, making querying and insertion fast operations. It is
 * done by mapping each currency by it's name, since there cannot be 2 currencies with the same name.
 * Implements methods for retrieving, adding and existence checking since the map is private.
 * <p>
 * Class implements {@linkplain Serializable}, since the singleton {@link Store} wants to serialize everything
 * it contains.
 */

public class Currency implements Serializable {
    @Serial
    private static final long serialVersionUID = 4363577714363577717L;

    /**
     * Name of the currency (Ex: RON, EUR, etc.)
     */
    private String name;

    /**
     * Symbol of the currency (Ex: $, £, etc.)
     */
    private String symbol;

    /**
     * The value of the currency when compared to euro, for example 1 GBP is worth 1.1 Euro, so it's parity is 1.1
     */
    private double parityToEur;

    /**
     * Map of currencies that takes as key the currency's name and returns a {@link Currency} object that has
     * that name
     */
    private static final Map<String, Currency> allCurrencies = new HashMap<>();

    /**
     * Initializes all non-static fields of the class
     * @param name name of the currency
     * @param symbol symbol of the currency
     * @param parityToEur how much this currency is worth compared to an euro
     */
    public Currency(String name, String symbol, double parityToEur) {
        this.name = name;
        this.symbol = symbol;
        this.parityToEur = parityToEur;
    }

    /**
     * @return name of the currency
     */
    public String getName() {
        return name;
    }

    /**
     * @return symbol of the currency
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @return get the parity of the currency
     */
    public double getParityToEur() {
        return parityToEur;
    }

    /**
     * @param name name of the currency
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param symbol symbol of the currency
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Changes the currency's parity and adjusts the prices of the products
     * in {@link Store} if this is also the current currency.
     * @param parityToEur new value for the parity to euro
     * @throws CannotChangeParityException if the currency it is applied on it's EURO
     */
    public void updateParity(double parityToEur) throws CannotChangeParityException {
        // corner case of changing the parity of euro relative to itself
        if (this.equals(allCurrencies.get("EUR")))
            throw new CannotChangeParityException("Cannot change parity of Euro relative to itself");
        // also change the prices of products if it's the current currency
        if (Store.getInstance().getCurrency().equals(this))
            Store.getInstance().getProducts().forEach(product ->
                updatePriceForProduct(product, parityToEur, this.parityToEur));
        this.parityToEur = parityToEur;
    }

    /**
     * Changes the price of a {@link Product} based on the old parity and a new one we give as parameter
     * @param product product it is applied on
     * @param newParity new parity, the one we change <strong>TO</strong>
     * @param oldParity old parity, the on we change <strong>FROM</strong>
     */
    public void updatePriceForProduct(Product product, double newParity, double oldParity) {
        product.setPrice(product.getPrice() / oldParity * newParity);
    }

    /**
     * Converts the price from a string to a double
     * @param price {@link String} with the value of a price
     * @return a {@link Double} with the value of the price
     */
    private double convertPrice(String price) {
        return Double.parseDouble(price);
    }

    /**
     * Converts the price from the currency given as parameter to the current {@link Currency} object
     * @param price {@link String} holding the value of the price
     * @param currency currency to change the price <strong>FROM</strong>
     * @return The converted price as a {@link Double}
     */
    public double convertPrice(String price, Currency currency) {
        return convertPrice(price) / this.getParityToEur() * currency.getParityToEur();
    }

    /**
     * Getter for the map of {@link Currency}
     * @return map of currencies with the name as a key and the currency object as value
     */
    public static Map<String, Currency> getAllCurrencies() {
        return allCurrencies;
    }

    /**
     * Retrieves the currency with the symbol given as parameter from the map
     * @param symbol string with the symbol of a {@link Currency}
     * @return currency with the symbol given parameter, otherwise <em>null</em>
     */
    public static Currency getCurrencyBySymbol(Character symbol) {
        // since the map takes the name as a key, we have to iterate through it this time
        return allCurrencies.values().stream().filter(currency -> currency.symbol.equals(symbol.toString()))
                .findAny().orElse(null);
    }

    /**
     * Insert a currency into the map that holds all the currencies
     * @param currency object to add into the map
     */
    public static void addCurrency(Currency currency) {
        allCurrencies.put(currency.getName(), currency);
    }

    /**
     * Checks if a {@link String} name is a valid currency, by checking the collection that holds all currencies
     * @param name value to check for
     * @return <em>True</em> if there is a currency with this name in the map, <em>False</em> otherwise
     */
    public static boolean isValid(String name) {
        return allCurrencies.containsKey(name);
    }

    /**
     * Returns the currency that has the name given parameter by checking the collection with all the currencies
     * @param name name of the currency
     * @return the {@link Currency} object if it exists in the map, <em>null</em> otherwise
     */
    public static Currency getCurrencyByName(String name) {
        return allCurrencies.get(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        return new EqualsBuilder()
                .append(name, currency.name)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }
}