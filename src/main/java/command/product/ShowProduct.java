package command.product;

import command.Command;
import toystore.Store;

import static java.lang.System.*;

public class ShowProduct implements Command {

    private final String uniqId;

    public ShowProduct(String uniqId) {
        this.uniqId = uniqId;
    }

    @Override
    public void execute() {
        out.println(Store.getInstance().getProductById(uniqId));
    }
}