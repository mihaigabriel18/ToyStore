package command.iofunctionalities;

import command.Command;
import toystore.Store;

import java.io.IOException;

import static java.lang.System.*;

public class SaveStore implements Command {

    private final String filename;

    public SaveStore(String filename) {
        this.filename = filename;
    }

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