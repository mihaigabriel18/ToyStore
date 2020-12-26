package toystore;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Store implements Serializable {

    private String name;
    private Currency currency;
    private List<Product> products;
    private List<Manufacturer> manufacturers;
    private static Store INSTANCE;

    private Store() {
    }

    public static Store getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Store();
        return INSTANCE;
    }

    public List<Product> readCSV(String filename) throws Exception {
        ParserCSV parserCSV = new ParserCSV(filename);
        // allocate an array with the size equal to the number of lines in the list
        int size = parserCSV.list.size();
        List<Product> products = new ArrayList<>(size);
        for (int index = 0; index < size; index++) {
            Product product = new ProductBuilder()
                    .withUniqueId(parserCSV.list.get(index).get(0))
                    .withName(parserCSV.list.get(index).get(1))
                    .withManufacturer(new Manufacturer(parserCSV.list.get(index).get(2),
                            Integer.parseInt(parserCSV.list.get(index).get(4))))
                    .withPrice(Double.parseDouble(parserCSV.list.get(index).get(3)))
                    .withDiscount(null)  // no initial discount
                    .build();
            products.add(product);
        }
        return products;
    }

    public void addProduct(Product product) throws DuplicateProductException {
        if (products.contains(product))
            throw new DuplicateProductException("Product already exists in the list");
        else
            products.add(product);
    }

    public void addManufacturer(Manufacturer manufacturer) throws DuplicateManufacturerException {
        if (manufacturers.contains(manufacturer))
            throw new DuplicateManufacturerException("Manufacturer already exists in the list");
        else manufacturers.add(manufacturer);
    }

    public void loadStore(String filename) throws IOException {
        // open in streams
        var inFile = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(inFile);
        // read object
        try {
            INSTANCE  = (Store) in.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("Class was not found");
            e.printStackTrace();
        }
        // close streams
        in.close();
        inFile.close();
    }

    public void saveStore(String filename) throws IOException {
        // open out stream
        var outFile = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(outFile);
        // write object
        out.writeObject(this);
        // close streams
        out.close();
        outFile.close();
    }

    void changeCurrency(Currency currency) throws CurrencyNotFoundException {
        this.currency = currency;

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
