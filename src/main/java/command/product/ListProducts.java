package command.product;

import command.Command;
import toystore.Store;

import static java.lang.System.*;

public class ListProducts implements Command {

    @Override
    public void execute() {
        Store.getInstance().getProducts().forEach(out::println);
    }
}
