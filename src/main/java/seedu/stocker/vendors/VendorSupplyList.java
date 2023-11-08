package seedu.stocker.vendors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Represents a class to manage the association between vendors and the drugs they supply.
 */
public class VendorSupplyList {
    private static final Map<String, List<String>> vendorSuppliedDrugs = new HashMap<>();

    /**
     * Adds a drug to a vendor's supply list.
     *
     * @param vendorName The name of the vendor.
     * @param drugName   The name of the drug to be added to the vendor's supply list.
     */
    public static void addDrugToVendor(String vendorName, String drugName) {
        vendorSuppliedDrugs.computeIfAbsent(vendorName, k -> new ArrayList<>()).add(drugName);
    }

    /**
     * Retrieves the list of drugs supplied by a specific vendor.
     *
     * @param vendorName The name of the vendor.
     * @return A list of drug names supplied by the vendor.
     */
    public static List<String> getDrugsSuppliedByVendor(String vendorName) {
        return vendorSuppliedDrugs.getOrDefault(vendorName, new ArrayList<>());
    }

    /**
     * Checks if a specific drug is supplied by a vendor.
     *
     * @param vendorName The name of the vendor.
     * @param drugName The name of the drug to check.
     * @return true if the drug is supplied by the vendor, false otherwise.
     */
    public static boolean containsDrug(String vendorName, String drugName) {
        List<String> suppliedDrugs = vendorSuppliedDrugs.get(vendorName);
        if (suppliedDrugs != null) {
            return suppliedDrugs.contains(drugName);
        } else {
            return false; // Vendor not found
        }
    }

    /**
     * Returns the mapping of vendors to the drugs they supply.
     *
     * @return A map where each key (vendor name) is associated with a list of drugs they supply.
     */
    public static Map<String, List<String>> getVendorSuppliedDrugs() {
        return vendorSuppliedDrugs;
    }
}
