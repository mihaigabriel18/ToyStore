package toystore;

import toystore.financial.*;
import toystore.financial.Currency;
import toystore.parser.*;
import toystore.productline.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.*;

public class Store implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Name of store
     */
    private final String name;

    /**
     * Name of currency associated with the store at a certain point in time
     */
    private Currency currency;

    /**
     * List of products in our store
     */
    private final List<Product> products;

    /**
     * Collection of all manufacturers which have products in our store
     */
    private final Map<String, Manufacturer> manufacturers;

    /**
     * Private instance of Store, since it is a singleton, this is going to be the only instance ever created.
     */
    private static Store instance;

    /**
     * Initialize fields of class
     * @param name name of store
     * @param products list of {@link Product}s
     * @param manufacturers map of manufacturers
     */
    private Store(String name, List<Product> products, Map<String, Manufacturer> manufacturers) {
        this.name = name;
        this.products = products;
        this.manufacturers = manufacturers;
        createCurrency("EUR", "€", 1d);
        this.currency = Currency.getCurrencyByName("EUR");
    }

    /**
     * Static method to retrieve the instance of singleton {@link Store}
     * @return {@link Store} instance
     */
    public static Store getInstance() {
        if (instance == null)
            instance = new Store("POO Store", new ArrayList<>(), new HashMap<>());
        return instance;
    }

    /**
     * Used to display a welcoming message when entering our store
     */
    public static void welcomeMessage() {
        out.println("Welcome to " + Store.getInstance().name);
    }

    /**
     * If a csv is read once more, the Store is supposed to be overwritten, so the Collections
     * of products and manufacturers must be emptied.
     */
    public void renewStore() {
        products.clear();
        manufacturers.clear();
        currency = null;
    }

    /**
     * @return current currency of the store
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @return the list of products from store
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Read the csv and save the result in a list of products.
     * Uses the {@link ParserCSV} class to transform the csv file into a {@link List} of lists of strings
     * and transforms it into a list of products.
     * @param fileName name of csv file
     * @return list of products
     * @throws CurrencyNotFoundException if the currency used in the csv file (GBP in our case) is not
     *                                   a valid currency (is not in the map of currencies)
     */
    public List<Product> readCSV(String fileName) throws CurrencyNotFoundException {
        ParserCSV parserCSV = new ParserCSV();
        List<List<String>> listCsv = parserCSV.readCSV(fileName);
        // if csv currency not valid, throw exception
        Character startingSymbol = listCsv.get(0).get(3).charAt(0);
        Currency startingCurrency = Currency.getCurrencyBySymbol(startingSymbol);
        if (startingCurrency == null)
            throw new CurrencyNotFoundException("Starting currency of csv not found");
        // allocate an array with the size equal to the number of lines in the list
        int size = listCsv.size();
        List<Product> productCsv = new ArrayList<>(size);
        // add each product
        for (List<String> strings : listCsv) {
            Manufacturer currManufacturer = addManufacturer(strings);

            Product product = new ProductBuilder()
                    .withUniqueId(strings.get(0))
                    .withName(strings.get(1))
                    .withManufacturer(currManufacturer)
                    // substring because we eliminate the symbol
                    // if there's ever a symbol with 2 characters, "substring(1)" will throw
                    // NumberFormatException, not a parsable double
                    .withPrice(currency.convertPrice(strings.get(3).substring(1), startingCurrency))
                    .withDiscount(null)  // no initial discount
                    .withQuantity(Integer.parseInt(strings.get(4)))
                    .build();
            // if product duplicated no not add it, just skip it
            try {
                addProduct(product);
            } catch (DuplicateProductException e) {
                e.printStackTrace();
                out.println("Duplicate product, skipping...");
            }
            // add products to manufacturer counter
            currManufacturer.appendProducts(product.getQuantity());
        }
        return productCsv;
    }

    /**
     * Before writing the csv we have to transform our data type (a list of products)
     * to the data typ required by {@link ParserCSV} (a list of lists of strings)
     * @return list of csv lines (which are also lists of strings)
     */
    private List<List<String>> transformStoreToCollection() {
        List<List<String>> csvList = new ArrayList<>();
        for (Product product : products) {
            List<String> line = new ArrayList<>();
            line.add(product.getUniqueId());
            line.add(product.getName());
            line.add(product.getManufacturer().getName());
            // keep only 3 digits of price
            line.add(currency.getSymbol() + String.format("%,.3f", product.getPrice()));  // starts with non-breaking space
            // append the " NEW" string to the quantity
            line.add(((Integer) product.getQuantity()).toString() + " NEW");
            csvList.add(line);
        }
        return csvList;
    }

    /**
     * Save list of products into a csv file
     * @param filename name of csv file
     */
    public void saveCSV(String filename) {
        new ParserCSV().writeCSV(transformStoreToCollection(), filename);
    }

    /**
     *
     * @param product
     * @throws DuplicateProductException
     */
    public void addProduct(Product product) throws DuplicateProductException {
        if (products.stream().anyMatch(prod -> prod.equals(product)))
            throw new DuplicateProductException("Product already exists in the list"    );
        else
            products.add(product);
    }

    public Product getProductById(String id) {
        return products.stream().filter(product -> product.getUniqueId().equals(id)).findFirst().orElse(null);
    }

    private Manufacturer addManufacturer(List<String> strings) {
        String nameManufacturer = strings.get(2);
        Manufacturer currManufacturer;
        if (manufacturers.containsKey(nameManufacturer))
            currManufacturer = manufacturers.get(nameManufacturer);
        else {
            currManufacturer = new Manufacturer(nameManufacturer);
            manufacturers.put(nameManufacturer, currManufacturer);
        }
        return currManufacturer;
    }

    /**
     * Query the map and retrieve the object based on the given key
     * @param name Name of the manufacturer, key to the Map
     * @return Value in the Map
     */
    public Manufacturer getManufacturerByName(String name) {
        return manufacturers.get(name);
    }

    /**
     * Transform current collection of manufacturers (which is a Map) into a list
     * of ordered Manufacturers
     * @return A List containing values of the map
     */
    public List<Manufacturer> getListOfOrderedManufacturers() {
        // next line sorts the map into another map
        Map<String, Manufacturer> collected = manufacturers.entrySet().stream().sorted(Map.Entry.comparingByValue()).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return new ArrayList<>(collected.values());
    }

    public static void loadStore(String filename) throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            // read object
            try {
                instance = (Store) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveStore(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    public void createCurrency(String name, String symbol, double parityToEur) {
        Currency.addCurrency(new Currency(name, symbol, parityToEur));
    }

    /**
     * this should have as parameter a String with the name, to a currency
     * @param currencyName
     * @throws CurrencyNotFoundException
     */
    public void changeCurrency(String currencyName) throws CurrencyNotFoundException {
        if (!Currency.isValid(currencyName))
            throw new CurrencyNotFoundException("Currency not valid, create it first!");

        Currency currCurrency = Currency.getCurrencyByName(currencyName);
        for (Product product : products) {
            product.setPrice(product.getPrice() / currCurrency.getParityToEur() * this.currency.getParityToEur());
        }

        this.currency = currCurrency;
    }

    public void createDiscount(DiscountType discountType, String name, double value) {
        Discount.addDiscount(new Discount(name, discountType, value));
    }

    public void applyDiscount(Discount discount) throws DiscountNotFoundException,
            NegativePriceException {
        if (discount == null)
            throw new DiscountNotFoundException("Discount not found");

        double minPrice = products.stream().map(Product::getPrice)
                            .mapToDouble(Double::doubleValue).min().orElse(Double.MAX_VALUE);

        discount.setAsAppliedNow();
        products.forEach(product -> product.setDiscount(discount));
        if (discount.getDiscountType() == DiscountType.FIXED_DISCOUNT) {
            if (minPrice < discount.getValue())
                throw new NegativePriceException("One of the products has a negative price");
            products.forEach(product -> product.setPrice(product.getPrice() - discount.getValue()));
        }
        else {
            if (discount.getValue() > 100 || discount.getValue() < 0)
                throw new NegativePriceException("The discount " + discount.getName() +
                        " exceeds maximum value and prices will be negative");
            products.forEach(product -> product.setPrice(product.getPrice() * (100 - discount.getValue()) / 100));
        }
    }

    public List<Product> getProductsByManufacturer(Manufacturer manufacturer) {
        return products.stream().filter(product -> product.getManufacturer().equals(manufacturer))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param products
     * @return
     */
    public double calculateTotal(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}
