package toystore;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Store {

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

}
