package command.iofunctionalities;

import command.Command;
import toystore.parser.*;
import toystore.Store;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will save the store into a csv file, receives one parameter listed in the constructor
 * </p>
 */

public class SaveCSV implements Command {

    /**
     * first parameter of command
     */
    private final String filename;

    /**
     * Initialize all class's fields
     * @param filename name of csv file
     */
    public SaveCSV(String filename) {
        this.filename = filename;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Store ourStore = Store.getInstance();
        ourStore.saveCSV(filename);
    }
}
