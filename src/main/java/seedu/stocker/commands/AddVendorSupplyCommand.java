package seedu.stocker.commands;

import seedu.stocker.vendors.VendorSupplyList;

/**
 * Adds a drug to a vendor's supply list.
 */
public class AddVendorSupplyCommand extends Command {

    public static final String COMMAND_WORD = "addVendorSupply";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a drug to a vendor's supply list. "
            + "Parameters: VENDOR_NAME, DRUG_NAME" + System.lineSeparator()
            + "Example: " + COMMAND_WORD + " /v Moderna /n Paracetamol";

    public static final String MESSAGE_SUCCESS = "New drug added to %1$s's supply list: %2$s";
    public static final String MESSAGE_VENDOR_NOT_FOUND = "Vendor not found: %1$s";

    private final String vendorName;
    private final String drugName;

    /**
     * Creates an AddVendorSupplyCommand to add a drug to a vendor's supply list.
     *
     * @param vendorName The name of the vendor.
     * @param drugName   The name of the drug to be added to the vendor's supply list.
     */
    public AddVendorSupplyCommand(String vendorName, String drugName) {
        this.vendorName = vendorName;
        this.drugName = drugName;
    }

    /**
     * Executes the command to add a drug to the specified vendor's supply list.
     *
     * @return CommandResult indicating the success of adding the drug or a message indicating the vendor was not found.
     */
    @Override
    public <T> CommandResult<T> execute() {
        String lowercaseVendorName = vendorName.toLowerCase();
        String lowercaseDrugName = drugName.toLowerCase();

        if (this.vendorsList.getVendorEntries().stream().anyMatch(vendor ->
                vendor.getName().equalsIgnoreCase(lowercaseVendorName))) {

            if (!VendorSupplyList.containsDrug(lowercaseVendorName, lowercaseDrugName)) {
                VendorSupplyList.addDrugToVendor(lowercaseVendorName, lowercaseDrugName);
                return new CommandResult<>(String.format(MESSAGE_SUCCESS, lowercaseVendorName, lowercaseDrugName));
            } else {
                return new CommandResult<>("The drug already exists in the vendor's supply list.");
            }
        } else {
            return new CommandResult<>(String.format(MESSAGE_VENDOR_NOT_FOUND, lowercaseVendorName));
        }
    }
}
