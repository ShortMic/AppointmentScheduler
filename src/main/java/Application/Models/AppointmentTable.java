package Application.Models;

import java.util.Date;

public class AppointmentTable extends Appointment{

    private String contactName;

    public AppointmentTable(int appointmentId, String title, String description, String location, String contactName, String type, Date start, Date end, int customerId, int userId, int contactId) {
        super(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
        this.contactName = contactName;
    }

    public AppointmentTable(Appointment appointment, String contactName){
        super(appointment.getAppointmentId(), appointment.getTitle(), appointment.getDescription(), appointment.getLocation(), appointment.getType(), appointment.getStart(), appointment.getEnd(), appointment.getCustomerId(), appointment.getUserId(), appointment.getContactId());
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}