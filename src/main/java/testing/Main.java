package testing;

import command.iofunctionalities.Exit;
import command.iofunctionalities.Quit;
import command.currency.*;
import command.discount.AddDiscount;
import command.discount.ApplyDiscount;
import command.discount.CalculateTotal;
import command.discount.ListDiscounts;
import command.iofunctionalities.LoadCSV;
import command.iofunctionalities.LoadStore;
import command.iofunctionalities.SaveCSV;
import command.iofunctionalities.SaveStore;
import command.product.ListManufacturers;
import command.product.ListProducts;
import command.product.ListProductsByManufacturer;
import command.product.ShowProduct;
import toystore.Store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;

import static command.Console.*;

public class Main {

    private static void executeMethod(String line) {
        List<String> words = Arrays.asList(line.split(" "));
        switch (AllCommands.valueOf(words.get(0))) {// first word determines which command we call
            case addcurrency -> addRequest(new AddCurrency(words.get(1), words.get(2), words.get(3)));
            case getstorecurrency -> addRequest(new GetStoreCurrency());
            case listcurrencies -> addRequest(new ListCurrencies());
            case setstorecurrency -> addRequest(new SetStoreCurrency(words.get(1)));
            case updateparity -> addRequest(new UpdateParity(words.get(1), words.get(2)));
            case adddiscount -> addRequest(new AddDiscount(words.get(1), words.get(2),
                    words.stream().skip(3).collect(Collectors.joining())));
            case applydiscount -> addRequest(new ApplyDiscount(words.get(1), words.get(2)));
            case calculatetotal -> addRequest(new CalculateTotal(
                    words.stream().skip(1).collect(Collectors.joining(" "))));
            case listdiscounts -> addRequest(new ListDiscounts());
            case loadcsv -> addRequest(new LoadCSV(words.get(1)));
            case loadstore -> addRequest(new LoadStore(words.get(1)));
            case savestore -> addRequest(new SaveStore(words.get(1)));
            case savecsv -> addRequest(new SaveCSV(words.get(1)));
            case listmanufacturers -> addRequest(new ListManufacturers());
            case listproducts -> addRequest(new ListProducts());
            case listproductsbymanufacturer -> addRequest(new ListProductsByManufacturer(
                    words.stream().skip(1).collect(Collectors.joining())));
            case showproduct -> addRequest(new ShowProduct(words.get(1)));
            case exit -> addRequest(new Exit());
            case quit -> addRequest(new Quit());
            default -> throw new IllegalArgumentException();
        }
    }

    private static void readInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                executeMethod(line);
            }
        }
        executeCommands();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Store.welcomeMessage();
        readInput();
    }
}
