package command;

import java.util.ArrayList;
import java.util.List;

public class Console {

    private static final List<Command> commands = new ArrayList<>();

    private Console() {}

    public static void addRequest(Command command) {
        commands.add(command);
    }

    public static void executeCommands() {
        commands.forEach(Command::execute);
    }
}
