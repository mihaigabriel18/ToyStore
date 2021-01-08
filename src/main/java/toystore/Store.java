package toystore;

import java.io.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Store implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;
    private final String name;
    private Currency currency;
    private final List<Product> products;
    private final Map<String, Manufacturer> manufacturers;
    private static Store instance;

    private Store(String name, List<Product> products, Map<String, Manufacturer> manufacturers) {
        this.name = name;
        this.products = products;
        this.manufacturers = manufacturers;
    }

    public static Store getInstance() {
        if (instance == null)
            instance = new Store("POO Store", new ArrayList<>(), new HashMap<>());
        return instance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> readCSV(String fileName) throws CsvReadingException {
        ParserCSV parserCSV = new ParserCSV();
        List<List<String>> listCsv = parserCSV.readCSV(fileName);
        // add EURO as starting default currency
        createCurrency("EUR", "€", 1d);
        // assume there is only one starting currency in the csv file
        // DO NOT KNOW IF IT IS REQUIRED OR NOT
        // createCurrency("GBP", listCsv.get(0).get(3).substring(0, 1), "1.1");
        // allocate an array with the size equal to the number of lines in the list
        int size = listCsv.size();
        List<Product> productCsv = new ArrayList<>(size);
        for (List<String> strings : listCsv) {
            String nameManufacturer = strings.get(2);
            Manufacturer currManufacturer;
            if (manufacturers.containsKey(nameManufacturer))
                currManufacturer = manufacturers.get(nameManufacturer);
            else {
                currManufacturer = new Manufacturer(nameManufacturer);
                manufacturers.put(nameManufacturer, currManufacturer);
            }
            currManufacturer.appendProduct();

            Product product = new ProductBuilder()
                    .withUniqueId(strings.get(0))
                    .withName(strings.get(1))
                    .withManufacturer(currManufacturer)
                    // substring because we eliminate the symbol
                    // if there's ever a symbol with 2 characters, "substring(1)" will throw
                    // NumberFormatException, not a parsable double
                    .withPrice(Double.parseDouble(strings.get(3).substring(1)))
                    .withDiscount(null)  // no initial discount
                    .withQuantity(Integer.parseInt(strings.get(4)))
                    .build();
            productCsv.add(product);
        }
        return productCsv;
    }

//    public static void main(String[] args) {
//        Manufacturer man1 = new Manufacturer("12");
//        Manufacturer man2 = new Manufacturer("b");
//        Manufacturer man3 = new Manufacturer("11");
//        Store a = Store.getInstance();
//        a.manufacturers.put("12", man1);
//        a.manufacturers.put("b", man2);
//        a.manufacturers.put("13", man3);
//        List<Manufacturer> mdap = Store.getInstance().getListOfOrderedManufacturers();
//        System.out.println(mdap);
//    }

    private List<List<String>> transformStoreToCollection() {
        List<List<String>> csvList = new ArrayList<>();
        for (Product product : products) {
            List<String> line = new ArrayList<>();
            line.add(product.getUniqueId());
            line.add(product.getName());
            line.add(product.getManufacturer().getName());
            line.add("\u00A0" + currency.getSymbol() + product.getPrice());  // starts with non-breaking space
            line.add(((Integer) product.getQuantity()).toString() + "\u00A0" + "NEW");
            csvList.add(line);
        }
        return csvList;
    }

    public void saveCSV(String filename) throws CsvWritingException {
        new ParserCSV().writeCSV(transformStoreToCollection(), filename);
    }

    public void addProduct(Product product) throws DuplicateProductException {
        if (products.stream().anyMatch(prod -> prod.equals(product)))
            throw new DuplicateProductException("Product already exists in the list"    );
        else
            products.add(product);
    }

    public Product getProductById(String id) {
        return products.stream().filter(product -> product.getUniqueId().equals(id)).findFirst().orElse(null);
    }

    public void addManufacturer(Manufacturer manufacturer) throws DuplicateManufacturerException {
        if (manufacturers.containsKey(manufacturer.getName()))
            throw new DuplicateManufacturerException("Manufacturer already exists in the list");
        else manufacturers.put(manufacturer.getName(), manufacturer);
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
        Map<String, Manufacturer> collected = manufacturers.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey,
                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
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
            product.setPrice(product.getPrice() / this.currency.getParityToEur() * currCurrency.getParityToEur());
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

        List<Product> deepCopyList = new ArrayList<>();
        for (Product prodCopy : products) {
            try {
                deepCopyList.add((Product) prodCopy.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        if (discount.getDiscountType() == DiscountType.FIXED_DISCOUNT) {
            products.forEach(product -> product.setPrice(product.getPrice() - discount.getValue()));
        }
        else {
            products.forEach(product -> product.setPrice(product.getPrice() * (1 - discount.getValue())));
        }

        if (products.stream().anyMatch(product -> product.getPrice() < 0)) {
            products.clear();
            products.addAll(deepCopyList);
            throw new NegativePriceException("One of the products has a negative price");
        }
    }

    public List<Product> getProductsByManufacturer(Manufacturer manufacturer) {
        return products.stream().filter(product -> product.getManufacturer().equals(manufacturer))
                .collect(Collectors.toList());
    }

    public double calculateTotal(List<Product> products) {
        return products.stream().mapToDouble(Product::getCost).sum();
    }
}
