package Application.Models;

/**
 * The Country Model meant to represent and store records from the MySQL Database for reference in the local cache collection.
 *
 * @author Michael Short
 * @version 1.0
 */
public class Country {
    private int countryId;
    private String country;

    /**
     * This Constructor is used when initializing the Country object.
     * @param countryId The associated and unique Country Id
     * @param country The associated Country name
     */
    public Country(int countryId, String country){
        this.countryId = countryId;
        this.country = country;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
