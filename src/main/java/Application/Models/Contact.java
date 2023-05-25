package Application.Models;

public class Contact {
    private int contactId;
    private String contactName;
    private String email;

    public Contact(int countryId, String contactName, String email){
        this.contactId = countryId;
        this.contactName = contactName;
        this.email = email;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

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
