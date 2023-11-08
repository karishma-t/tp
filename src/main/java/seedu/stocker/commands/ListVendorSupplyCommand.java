package seedu.stocker.commands;

import seedu.stocker.vendors.VendorSupplyList;

import java.util.List;

/**
 * Lists the drugs supplied by a specific vendor in a case-insensitive manner.
 */
public class ListVendorSupplyCommand extends Command {
    public static final String COMMAND_WORD = "listVendorSupply";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the drugs supplied by a specific vendor. "
            + "Parameters: VENDOR_NAME" + System.lineSeparator()
            + "Example: " + COMMAND_WORD + " /v Moderna";

    private final String vendorName;

    /**
     * Creates a ListVendorSupplyCommand to list the drugs supplied by a specific vendor.
     *
     * @param vendorName The name of the vendor.
     */
    public ListVendorSupplyCommand(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * Executes the command to list the drugs supplied by the specified vendor.
     *
     * @return CommandResult containing the list of supplied drugs or a message indicating none were supplied.
     */
    @Override
    public CommandResult execute() {
        String lowercaseVendorName = vendorName.toLowerCase();

        if (lowercaseVendorName.isEmpty()) {
            return new CommandResult<>(MESSAGE_USAGE);
        }

        List<String> suppliedDrugs = VendorSupplyList.getDrugsSuppliedByVendor(lowercaseVendorName);

        if (suppliedDrugs.isEmpty()) {
            return new CommandResult<>("No drugs supplied by " + vendorName);
        } else {
            return new CommandResult<>("Drugs supplied by " + vendorName + ": "
                    + String.join(", ", suppliedDrugs));
        }
    }
}
