package command.iofunctionalities;

import command.Command;
import toystore.CsvWritingException;
import toystore.Store;

public class SaveCSV implements Command {

    private final String filename;

    public SaveCSV(String filename) {
        this.filename = filename;
    }

    @Override
    public void execute() {
        Store ourStore = Store.getInstance();
        try {
            ourStore.saveCSV(filename);
        } catch (CsvWritingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error writing to file", e);
        }
    }
}
