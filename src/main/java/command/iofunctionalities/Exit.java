package command.iofunctionalities;

import command.Command;

public class Exit implements Command {

    @Override
    public void execute() {
        System.exit(0);
    }
}
