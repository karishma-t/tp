package seedu.stocker.commands;

import seedu.stocker.authentication.LoginSystem;

import java.io.IOException;

public class RegisterCommand extends Command {

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Register new user into system. "
            + System.lineSeparator()
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "New User Created.";

    public CommandResult execute() throws IOException {
        LoginSystem system = new LoginSystem();
        system.loadExistingUsers();
        system.newUserCreator();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
