package command.iofunctionalities;

import command.Command;
import toystore.parser.*;
import toystore.Store;

public class SaveCSV implements Command {

    private final String filename;

    public SaveCSV(String filename) {
        this.filename = filename;
    }

    @Override
    public void execute() {
        Store ourStore = Store.getInstance();
        ourStore.saveCSV(filename);
    }
}
