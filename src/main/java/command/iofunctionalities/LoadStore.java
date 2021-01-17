package command.iofunctionalities;

import command.Command;
import toystore.Store;

import java.io.IOException;

import static java.lang.System.*;


public class LoadStore implements Command {

    private  final String filename;

    public LoadStore(String filename) {
        this.filename = filename;
    }

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
