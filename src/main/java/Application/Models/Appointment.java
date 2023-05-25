package Application.Models;

import java.util.Date;

public class Appointment {
    public int appointmentId;
    public String title;
    public String description;
    public String location;
    public String type;
    public Date start;
    public Date end;
    public int customerId;
    public int userId;
    public int contactId;

    public Appointment(int appointmentId, String title, String description, String location, String type, Date start, Date end, int customerId, int userId, int contactId){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

}
