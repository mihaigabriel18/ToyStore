package command.product;

import command.Command;
import toystore.Store;

public class ListProductsByManufacturer implements Command {

    private final String manufacturerName;

    public ListProductsByManufacturer(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @Override
    public void execute() {
        Store ourStore = Store.getInstance();
        ourStore.getProductsByManufacturer(ourStore.getManufacturerByName(manufacturerName));
    }
}
