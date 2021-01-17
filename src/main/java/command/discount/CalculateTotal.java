package command.discount;

import command.Command;

import toystore.Store;
import toystore.productline.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.*;

public class CalculateTotal implements Command {

    private final List<String> uniqIds;

    public CalculateTotal(String ids) {
        this.uniqIds = Arrays.asList(ids.split(" "));
    }

    @Override
    public void execute() {
        Store ourStore = Store.getInstance();
        List<Product> productList = uniqIds.stream().map(ourStore::getProductById).collect(Collectors.toList());
        out.println(ourStore.getCurrency().getSymbol() + String.format("%,.3f", ourStore.calculateTotal(productList)));
    }
}
