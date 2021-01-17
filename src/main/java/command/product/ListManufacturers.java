package command.product;

import command.Command;
import toystore.productline.*;
import toystore.Store;

import java.util.List;

import static java.lang.System.*;

public class ListManufacturers implements Command {

    @Override
    public void execute() {
        List<Manufacturer> manufacturersList = Store.getInstance().getListOfOrderedManufacturers();
        manufacturersList.forEach(out::println);
    }
}
