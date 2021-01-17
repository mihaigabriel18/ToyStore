package command.iofunctionalities;

import command.Command;
import toystore.parser.*;
import toystore.productline.*;
import toystore.Store;
import toystore.financial.CurrencyNotFoundException;

import java.util.List;

public class LoadCSV implements Command {

    private final String filename;

    public LoadCSV(String filename) {
        this.filename = filename;
    }

    @Override
    public void execute() {
        Store.getInstance().renewStore();
        Store ourStore = Store.getInstance();
        List<Product> storeProducts = ourStore.getProducts();
        // list of products obtained by reading the csv
        List<Product> csvProducts;
        try {
            csvProducts = ourStore.readCSV(filename);
        } catch (CurrencyNotFoundException e) {
            e.printStackTrace();
            throw new CsvReadingException("Currency written in csv not added yet, exiting...", e);
        }
        storeProducts.addAll(csvProducts);
    }
}
