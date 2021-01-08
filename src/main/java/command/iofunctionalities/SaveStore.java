package command.iofunctionalities;

import command.Command;
import toystore.Store;

import java.io.IOException;

import static java.lang.System.*;

public class SaveStore implements Command {

    private static final String FILENAME = "toyStoreSerialized";

    @Override
    public void execute() {
        try {
            Store.getInstance().saveStore(FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }
}