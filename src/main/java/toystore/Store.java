package toystore;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Store implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;
    private final String name;
    private Currency currency;
    private final List<Product> products;
    private final List<Manufacturer> manufacturers;
    private static Store instance;

    private Store(String name, List<Product> products, List<Manufacturer> manufacturers) {
        this.name = name;
        this.products = products;
        this.manufacturers = manufacturers;
    }

    public static Store getInstance() {
        if (instance == null)
            instance = new Store("POO Store", new ArrayList<>(), new ArrayList<>());
        return instance;
    }

    public List<Product> readCSV(String filename) throws CsvReadingException {
        ParserCSV parserCSV = new ParserCSV(filename);
        List<List<String>> listCsv = parserCSV.readCSV();
        // allocate an array with the size equal to the number of lines in the list
        int size = listCsv.size();
        List<Product> productCsv = new ArrayList<>(size);
        for (List<String> strings : listCsv) {
            Product product = new ProductBuilder()
                    .withUniqueId(strings.get(0))
                    .withName(strings.get(1))
                    .withManufacturer(new Manufacturer(strings.get(2),
                            Integer.parseInt(strings.get(4))))
                    .withPrice(Double.parseDouble(strings.get(3)))
                    .withDiscount(null)  // no initial discount
                    .build();
            productCsv.add(product);
        }
        return productCsv;
    }

    public void addProduct(Product product) throws DuplicateProductException {
        if (products.contains(product))
            throw new DuplicateProductException("Product already exists in the list"    );
        else
            products.add(product);
    }

    public void addManufacturer(Manufacturer manufacturer) throws DuplicateManufacturerException {
        if (manufacturers.contains(manufacturer))
            throw new DuplicateManufacturerException("Manufacturer already exists in the list");
        else manufacturers.add(manufacturer);
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

    void createCurrency() {

    }

    void changeCurrency(Currency currency) throws CurrencyNotFoundException {
        this.currency = currency;

    }

    void createDiscount() {

    }

    void applyDiscount(Discount discount) throws DiscountNotFoundException,
            NegativePriceException {
        discount.setAsAppliedNow();
    }

    public List<Product> getProductsByManufacturer(Manufacturer manufacturer) {
        return products.stream().filter(product -> product.getManufacturer().equals(manufacturer))
                .collect(Collectors.toList());
    }

    public double calculateTotal(List<Product> products) {
        return products.stream().mapToDouble(Product::getCost).sum();
    }

}
