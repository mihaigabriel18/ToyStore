package command.iofunctionalities;

import command.Command;
import toystore.CsvReadingException;
import toystore.Product;
import toystore.Store;

import java.util.List;

public class LoadCSV implements Command {

    private final String filename;

    public LoadCSV(String filename) {
        this.filename = filename;
    }

    @Override
    public void execute() {
        Store ourStore = Store.getInstance();
        List<Product> storeProducts = ourStore.getProducts();
        // list of products obtained by reading the csv
        List<Product> csvProducts;
        try {
            csvProducts = ourStore.readCSV(filename);
        } catch (CsvReadingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading the input file", e);
        }
        storeProducts.addAll(csvProducts);
    }
}
