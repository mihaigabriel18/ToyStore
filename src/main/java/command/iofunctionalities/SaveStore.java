package command.iofunctionalities;

import command.Command;
import toystore.Store;

import java.io.IOException;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will save a store state(serialize) into a binary file,
 *     receives one parameter listed in the constructor
 * </p>
 */

public class SaveStore implements Command {

    /**
     * first parameter of command
     */
    private final String filename;

    /**
     * Initialize all class's fields
     * @param filename name of csv file
     */
    public SaveStore(String filename) {
        this.filename = filename;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        try {
            Store.getInstance().saveStore(filename);
        } catch (IOException e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }
}