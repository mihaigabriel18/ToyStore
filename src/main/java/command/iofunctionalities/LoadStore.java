package command.iofunctionalities;

import command.Command;
import toystore.Store;

import java.io.IOException;

import static java.lang.System.*;

/**
 * Concrete command, implementing abstract {@link Command} interface from command design pattern
 * <p>
 *     This specific command will load a store state from a binary file (de-serialize),
 *     receives one parameter listed in the constructor
 * </p>
 */

public class LoadStore implements Command {

    /**
     * first parameter of command
     */
    private final String filename;

    /**
     * Initialize all class's fields
     * @param filename name of binary file
     */
    public LoadStore(String filename) {
        this.filename = filename;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        try {
            Store.loadStore(filename);
        } catch (IOException e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }
}
