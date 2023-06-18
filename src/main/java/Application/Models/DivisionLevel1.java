package Application.Models;

/**
 * The DivisionLevel1 Model meant to represent and store records from the MySQL Database for reference in the local cache collection.
 * Ties together both country and 1st level division state/provinces.
 *
 * @author Michael Short
 * @version 1.0
 */
public class DivisionLevel1 {

    private int divisionId;
    private String division;
    private int countryId;

    /**
     * This Constructor is used when initializing the DivisionLevel1 object.
     *
     * @param divisionId The associated and unique division id
     * @param division The associated division (common name)
     * @param countryId The associated and country id
     */
    public DivisionLevel1(int divisionId, String division, int countryId){
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
