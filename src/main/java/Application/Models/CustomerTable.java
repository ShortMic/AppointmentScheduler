package Application.Models;

public class CustomerTable extends Customer{

    private String country;
    private String stateProvince;

    public CustomerTable(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) {
        super(customerId, customerName, address, postalCode, phone, divisionId);
        country = getCountry();
    }

    public CustomerTable(Customer customer, String country, String stateProvince) {
        super(customer.getCustomerId(), customer.getCustomerName(), customer.getAddress(), customer.getPostalCode(),
                customer.getPhone(), customer.customerId);
        this.country = country;
        this.stateProvince = stateProvince;
    }

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
