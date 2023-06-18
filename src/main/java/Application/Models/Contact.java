package Application.Models;

/**
 * The Contact Model meant to represent and store records from the MySQL Database for reference in the local cache collection.
 *
 * @author Michael Short
 * @version 1.0
 */
public class Contact {
    private int contactId;
    private String contactName;
    private String email;

    /**
     * This Constructor is used when initializing the Contact object.
     *
     * @param contactId The associated and unique Contact Id
     * @param contactName The associated Contact Name
     * @param email The associated Contact email
     */
    public Contact(int contactId, String contactName, String email){
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    public void setContactId(int contactId){ this.contactId = contactId; }

    public int getContactId() {
        return contactId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }
}
