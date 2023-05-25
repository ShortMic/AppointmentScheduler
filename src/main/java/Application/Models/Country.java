package Application.Models;

public class Country {
    private int countryId;
    private String country;

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
