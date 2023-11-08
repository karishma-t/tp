package seedu.stocker.commands;

import seedu.stocker.vendors.VendorSupplyList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Lists the vendors that supply a specific drug.
 */
public class FindVendorSupplyCommand extends Command {
    public static final String COMMAND_WORD = "findVendorSupply";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the vendors that supply a specific drug. "
            + "Parameters: DRUG_NAME" + System.lineSeparator()
            + "Example: " + COMMAND_WORD + " /n Paracetamol";

    private final String drugName;

    /**
     * Creates a FindVendorSupplyCommand to list the vendors that supply a specific drug.
     *
     * @param drugName The name of the drug to search for.
     */
    public FindVendorSupplyCommand(String drugName) {
        this.drugName = drugName;
    }

    /**
     * Executes the command to list the vendors that supply the specified drug.
     *
     * @return CommandResult indicating the vendors that supply the drug or an error message.
     */
    @Override
    public CommandResult execute() {
        String lowercaseDrugName = drugName.toLowerCase();

        if (lowercaseDrugName.isEmpty()) {
            return new CommandResult<>(MESSAGE_USAGE);
        }

        List<String> supplyingVendors = VendorSupplyList.getVendorSuppliedDrugs()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(lowercaseDrugName))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (supplyingVendors.isEmpty()) {
            return new CommandResult<>("No vendors supply the drug: " + drugName);
        } else {
            return new CommandResult<>("Vendors supplying the drug " + drugName + ": "
                    + String.join(", ", supplyingVendors));
        }
    }

}
