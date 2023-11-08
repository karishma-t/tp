package seedu.stocker.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.stocker.commands.AddCommand;
import seedu.stocker.commands.Command;
import seedu.stocker.commands.DeleteCommand;
import seedu.stocker.commands.ExitCommand;
import seedu.stocker.commands.FindCommand;
import seedu.stocker.commands.HelpCommand;
import seedu.stocker.commands.IncorrectCommand;
import seedu.stocker.commands.ListCommand;
import seedu.stocker.commands.LoginCommand;
import seedu.stocker.commands.RegisterCommand;
import seedu.stocker.commands.SaveCommand;
import seedu.stocker.commands.AddVendorCommand;
import seedu.stocker.commands.ShowStockLevelCommand;
import seedu.stocker.commands.ViewCartCommand;
import seedu.stocker.commands.AddToCartCommand;
import seedu.stocker.commands.CheckOutCommand;
import seedu.stocker.commands.ListVendorCommand;
import seedu.stocker.commands.SetThresholdCommand;
import seedu.stocker.commands.ListThresholdCommand;
import seedu.stocker.commands.AddDescriptionCommand;
import seedu.stocker.commands.GetDescriptionCommand;
import seedu.stocker.commands.ListDescriptionsCommand;
import seedu.stocker.commands.AddVendorSupplyCommand;
import seedu.stocker.commands.FindVendorSupplyCommand;
import seedu.stocker.commands.ListVendorSupplyCommand;

import static seedu.stocker.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.stocker.common.Messages.MESSAGE_INVALID_QUANTITY;
import static seedu.stocker.common.Messages.MESSAGE_INVALID_NAME;
import static seedu.stocker.common.Messages.MESSAGE_INVALID_DATE;
import static seedu.stocker.common.Messages.MESSAGE_INVALID_PRICE;


public class Parser {
    public Parser() {
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        String[] words = userInput.trim().split(" ", 2);  // split the input into command and arguments
        if (words.length == 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = words[0];
        final String arguments = userInput.replaceFirst(commandWord, "").trim();

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return prepareAddCommand(arguments);

        case FindCommand.COMMAND_WORD:
            return prepareFindCommand(arguments);

        case AddToCartCommand.COMMAND_WORD:
            return prepareAddToCartCommand(arguments);

        case AddDescriptionCommand.COMMAND_WORD:
            return prepareAddDescriptionCommand(arguments);

        case GetDescriptionCommand.COMMAND_WORD:
            return prepareGetDescriptionCommand(arguments);

        case AddVendorSupplyCommand.COMMAND_WORD:
            return prepareAddVendorSupplyCommand(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDeleteCommand(arguments);

        case ListVendorSupplyCommand.COMMAND_WORD:
            return prepareListVendorSupplyCommand(arguments);

        case FindVendorSupplyCommand.COMMAND_WORD:
            return prepareFindVendorSupplyCommand(arguments);

        case CheckOutCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return new CheckOutCommand();
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        CheckOutCommand.MESSAGE_USAGE));
            }

        case ExitCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return new ExitCommand();
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE));
            }

        case HelpCommand.COMMAND_WORD:
            return prepareHelpCommand(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareListCommand(arguments);

        case ViewCartCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return new ViewCartCommand();
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ViewCartCommand.MESSAGE_USAGE));
            }

        case RegisterCommand.COMMAND_WORD:
            return new RegisterCommand();

        case LoginCommand.COMMAND_WORD:
            return new LoginCommand();

        case SaveCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return new SaveCommand();
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SaveCommand.MESSAGE_USAGE));
            }

        case AddVendorCommand.COMMAND_WORD:
            return prepareAddVendorCommand(arguments);

        case ShowStockLevelCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return new ShowStockLevelCommand();
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ShowStockLevelCommand.MESSAGE_USAGE));
            }

        case ListVendorCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return new ListVendorCommand();
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListVendorCommand.MESSAGE_USAGE));
            }

        case SetThresholdCommand.COMMAND_WORD:
            return prepareSetThresholdCommand(arguments);

        case ListThresholdCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return new ListThresholdCommand();
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListThresholdCommand.MESSAGE_USAGE));
            }

        case ListDescriptionsCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return new ListDescriptionsCommand();
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListDescriptionsCommand.MESSAGE_USAGE));
            }

        default:
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the add drug command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAddCommand(String args) {
        try {
            Pattern pattern = Pattern.compile("/n (.*) /d (.*) /s (.*) /q (.*) /p (.*)");
            Matcher matcher = pattern.matcher(args);
            if (matcher.matches() && matcher.groupCount() == 5) {
                String name = matcher.group(1).trim();
                String expiryDate = matcher.group(2).trim();
                String serialNumber = matcher.group(3).trim();
                Long quantity = Long.parseLong(matcher.group(4));
                double sellingPrice = Double.parseDouble(matcher.group(5));

                if (quantity < 1 || quantity > 999999999) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_QUANTITY, AddCommand.MESSAGE_USAGE));
                }
                if (name.isEmpty()) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_NAME, AddCommand.MESSAGE_USAGE));
                }
                if (serialNumber.isEmpty()) {
                    return new IncorrectCommand("Serial number cannot be empty.");
                }
                // Check if the expiry date has a valid format
                if (!isValidDateFormat(expiryDate)) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_DATE, AddCommand.MESSAGE_USAGE));
                }
                // Check if sellingPrice is in the valid range (0.01 to 1000.00) and has up to 2 decimal places
                if (sellingPrice < 0.01 || sellingPrice > 1000.00) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_PRICE, AddCommand.MESSAGE_USAGE));
                }

                return new AddCommand(name, expiryDate, serialNumber, quantity, sellingPrice);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }



    /**
     * Checks if the given date string has a valid "dd/mm/yyyy" format.
     *
     * @param date The date string to be validated.
     * @return True if the date has a valid format, false otherwise.
     */
    private boolean isValidDateFormat(String date) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
        return date.matches(regex);
    }


    /**
     * Parses arguments in the context of the delete command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDeleteCommand(String args) {
        Pattern pattern = Pattern.compile("/s (.*)");
        Matcher matcher = pattern.matcher(args);
        if (matcher.matches() && matcher.groupCount() == 1) {
            String name = matcher.group(1);
            return new DeleteCommand(name);
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the add drug to cart command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAddToCartCommand(String args) {
        try {
            Pattern pattern = Pattern.compile("/s (.*) /q (.*)");
            Matcher matcher = pattern.matcher(args);
            if (matcher.matches() && matcher.groupCount() == 2) {
                String name = matcher.group(1);
                long quantity = Long.parseLong(matcher.group(2));
                if (quantity < 1) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_QUANTITY, AddCommand.MESSAGE_USAGE));
                }
                return new AddToCartCommand(name, quantity);
            } else {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToCartCommand.MESSAGE_USAGE));
            }
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    private Command prepareFindCommand(String args) {
        String[] findArgs = args.split(" ", 2);
        if (findArgs.length == 2) {
            String criterion = findArgs[0];
            String keyword = findArgs[1];
            if (criterion.equals("/n") || criterion.equals("/s")) {
                return new FindCommand(keyword, criterion);
            } else if (criterion.equals("/d")){
                if (isValidDateFormat(keyword)) {
                    return new FindCommand(keyword, criterion);
                } else {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_DATE, FindCommand.MESSAGE_USAGE));
                }
            } else{
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    private Command prepareAddVendorCommand(String args) {
        String[] vendorArgs = args.split(" ", 1);
        String vendorName = vendorArgs[0];
        if (vendorName.equals(null) || vendorName.equals("") || vendorName.equals(" ")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVendorCommand.MESSAGE_USAGE));
        }
        return new AddVendorCommand(vendorName);
    }

    private Command prepareSetThresholdCommand(String args) {
        try {
            Pattern pattern = Pattern.compile("/s (.*) /tq (.*)");
            Matcher matcher = pattern.matcher(args);
            if (matcher.matches() && matcher.groupCount() == 2) {
                String name = matcher.group(1);
                Long threshold = Long.parseLong(matcher.group(2));
                return new SetThresholdCommand(name, threshold);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SetThresholdCommand.MESSAGE_USAGE));
            }
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetThresholdCommand.MESSAGE_USAGE));
        }
    }

    private Command prepareAddDescriptionCommand(String args) {
        Pattern pattern = Pattern.compile("/n (.*) /desc (.*)");
        Matcher matcher = pattern.matcher(args);
        if (matcher.matches() && matcher.groupCount() == 2) {
            String name = matcher.group(1);
            String description = matcher.group(2);
            if (name != null && !name.isEmpty() && description != null && !description.isEmpty()) {
                return new AddDescriptionCommand(name, description);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddDescriptionCommand.MESSAGE_USAGE));
            }
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddDescriptionCommand.MESSAGE_USAGE));
        }
    }

    private Command prepareGetDescriptionCommand(String args) {
        Pattern pattern = Pattern.compile("/n (.*)");
        Matcher matcher = pattern.matcher(args);
        if (matcher.matches() && matcher.groupCount() == 1) {
            String name = matcher.group(1);
            if (name != null && !name.isEmpty()) {
                return new GetDescriptionCommand(name);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        GetDescriptionCommand.MESSAGE_USAGE));
            }
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GetDescriptionCommand.MESSAGE_USAGE));
        }
    }

    private Command prepareAddVendorSupplyCommand(String args) {
        Pattern pattern = Pattern.compile("/v (.*) /n (.*)");
        Matcher matcher = pattern.matcher(args);
        if (matcher.matches() && matcher.groupCount() == 2) {
            String vendor = matcher.group(1).trim();
            String drug = matcher.group(2).trim();
            if (!vendor.isEmpty() && !drug.isEmpty()) {
                return new AddVendorSupplyCommand(vendor, drug);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddVendorSupplyCommand.MESSAGE_USAGE));
            }
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddVendorSupplyCommand.MESSAGE_USAGE));
        }
    }

    private Command prepareListVendorSupplyCommand(String args) {
        Pattern pattern = Pattern.compile("/v (.*)");
        Matcher matcher = pattern.matcher(args);
        if (matcher.matches() && matcher.groupCount() == 1) {
            String vendor = matcher.group(1).trim();
            if (!vendor.isEmpty()) {
                return new ListVendorSupplyCommand(vendor);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListVendorSupplyCommand.MESSAGE_USAGE));
            }
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListVendorSupplyCommand.MESSAGE_USAGE));
        }
    }

    private Command prepareFindVendorSupplyCommand(String args) {
        Pattern pattern = Pattern.compile("/n (.*)");
        Matcher matcher = pattern.matcher(args);
        if (matcher.matches() && matcher.groupCount() == 1) {
            String drug = matcher.group(1).trim();
            if (!drug.isEmpty()) {
                return new FindVendorSupplyCommand(drug);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        FindVendorSupplyCommand.MESSAGE_USAGE));
            }
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindVendorSupplyCommand.MESSAGE_USAGE));
        }
    }

    private Command prepareListCommand(String args) {
        // Check if there are no arguments for the "list" command
        if (args.isEmpty()) {
            return new ListCommand();
        } else {
            // Handle the case where extra arguments are provided for "list"
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

    private Command prepareHelpCommand(String args) {
        // Check if there are no arguments for the "list" command
        if (args.isEmpty()) {
            return new HelpCommand();
        } else {
            // Handle the case where extra arguments are provided for "help"
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }
}
