package command;

import java.util.List;

public class Console {

    List<Command> commands;

    public void addRequest(Command command) {
        commands.add(command);
    }

    public void executeCommands() {
        commands.forEach(Command::execute);
    }
}
