package testing;

import command.currency.*;
import command.discount.*;
import command.iofunctionalities.*;
import command.product.*;
import toystore.Store;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static command.Console.*;

/**
 * Testing class, reads the input from a file, and after registering all commands, using the
 * command design pattern, it will after execute each one, one by one.
 * <p>
 *     This class is the entry point of the program
 * </p>
 */

public class Main {

    /**
     * Method that checks which method needs to be added to the queue of commands,
     * and adds it there.
     * @param line input line
     */
    private static void executeMethod(String line) {
        List<String> words = Arrays.asList(line.split(" "));
        // test possible commands
        switch (AllCommands.valueOf(words.get(0))) { // first word determines which command we call
            case addcurrency -> addRequest(new AddCurrency(words.get(1), words.get(2), words.get(3)));
            case getstorecurrency -> addRequest(new GetStoreCurrency());
            case listcurrencies -> addRequest(new ListCurrencies());
            case setstorecurrency -> addRequest(new SetStoreCurrency(words.get(1)));
            case updateparity -> addRequest(new UpdateParity(words.get(1), words.get(2)));
            case adddiscount -> addRequest(new AddDiscount(words.get(1), words.get(2),
                    words.stream().skip(3).collect(Collectors.joining(" "))));
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
            case exit, quit -> finalActions();
            // not a valid command
            default -> throw new IllegalArgumentException();
        }
    }

    /**
     * When "quit" or "exit" is called, execute all commands so far and exit the program
     */
    private static void finalActions() {
        executeCommands();
        System.exit(0);
    }

    /**
     * Reads input from the input stream
     */
    private static void readInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                executeMethod(line);
            }
        }
        executeCommands();
    }

    /**
     * Entry point of program, reading input commands
     * Redirects output to a file called "commandResults.out", and errors to "commandErrors.err"
     * @param args main args
     */
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(new FileOutputStream("commandResults.out")));
            System.setErr(new PrintStream(new FileOutputStream("commandErrors.err")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Store.welcomeMessage();
        readInput();
    }
}
