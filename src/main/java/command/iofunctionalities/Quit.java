package command.iofunctionalities;

import command.Command;

public class Quit implements Command {

    @Override
    public void execute() {
        System.exit(0);
    }
}
