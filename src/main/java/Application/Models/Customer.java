package Application.Models;

/**
 * The Customer Model meant to represent and store records from the MySQL Database for reference in the local cache collection.
 *
 * @author Michael Short
 * @version 1.0
 */
public class Customer {
    protected int customerId;
    protected String customerName;
    protected String address;
    protected String postalCode;
    protected String phone;
    protected int divisionId;

    /**
     * This Constructor is used when initializing the Customer object.
     * @param customerId The associated and unique Customer Id
     * @param customerName The associated Customer name
     * @param address The associated Customer address
     * @param postalCode The associated Customer postal code
     * @param phone The associated Customer phone number
     * @param divisionId The associated and unique Customer state/province
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId){
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
