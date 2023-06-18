package Application.Models;

/**
 * The CustomerTable Model meant to represent and store the actual displayed records in the MainMenuView Customer Table
 * inherits and extends Customer functionality & fields with the addition of the country name and state/province name
 * required for display.
 *
 * @author Michael Short
 * @version 1.0
 */
public class CustomerTable extends Customer{

    private String country;
    private String stateProvince;

    public CustomerTable(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) {
        super(customerId, customerName, address, postalCode, phone, divisionId);
        country = getCountry();
    }

    /**
     * This Constructor is used specifically when the Customer type object is already generated and needs to be
     * converted to an CustomerTable with the additional country name and state/province name field info.
     *
     * @param customer The pre-generated customer
     * @param country The attached country name associated with the customer
     * @param stateProvince The attached state/province name associated with the customer
     */
    public CustomerTable(Customer customer, String country, String stateProvince) {
        super(customer.getCustomerId(), customer.getCustomerName(), customer.getAddress(), customer.getPostalCode(),
                customer.getPhone(), customer.customerId);
        this.country = country;
        this.stateProvince = stateProvince;
    }

    /**
     * This Constructor is used when initializing both the Customer and its indirect country name and state/province
     * name field info field at once.
     *
     * @param customerId The associated and unique customer id
     * @param customerName The associated customer name
     * @param address The associated customer address
     * @param postalCode The associated customer postal code
     * @param phone The associated customer phone number
     * @param country The associated customer country
     * @param stateProvince The associated customer state/province
     * @param divisionId The associated customer country division id
     */
    public CustomerTable(int customerId, String customerName, String address, String postalCode, String phone, String country, String stateProvince, int divisionId) {
        super(customerId, customerName, address, postalCode, phone, divisionId);
        this.country = country;
        this.stateProvince = stateProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }
}
