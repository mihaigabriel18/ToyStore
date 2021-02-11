package command.iofunctionalities;

import command.Command;
import toystore.parser.*;
import toystore.productline.*;
import toystore.Store;
import toystore.financial.CurrencyNotFoundException;

import java.util.List;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will lead a store <strong>from</strong> a csv file,
 *     receives one parameter listed in the constructor
 * </p>
 */

public class LoadCSV implements Command {

    /**
     * first parameter of command
     */
    private final String filename;

    /**
     * Initialize all class's fields
     * @param filename name of csv file
     */
    public LoadCSV(String filename) {
        this.filename = filename;
    }

    /**
     * {@inheritDoc}
     */
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
