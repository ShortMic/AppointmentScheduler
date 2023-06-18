package Application.Models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The AppointmentTable Model meant to represent and store the actual displayed records in the MainMenuView Appointment Table
 * inherits and extends Appointment functionality & fields with the addition of the contact name required for display.
 *
 * @author Michael Short
 * @version 1.0
 */
public class AppointmentTable extends Appointment{

    private String contactName;

    public AppointmentTable(int appointmentId, String title, String description, String location, String contactName, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        super(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
        this.contactName = contactName;
    }

    /**
     * This Constructor is used when initializing both the Appointment and its indirect contactName field at once.
     *
     * @param appointmentId The associated and unique Appointment Id
     * @param title The associated Appointment title
     * @param description The associated Appointment description
     * @param location The associated Appointment location
     * @param contactName The associated Appointment contact name
     * @param type The associated Appointment type
     * @param start The associated Appointment start time
     * @param end The associated Appointment end time
     * @param customerId The associated & unique reference to the Appointment's customer id
     * @param userId The associated & unique reference to the Appointment's user id
     * @param contactId The associated & unique reference to the Appointment's contact id
     */
    public AppointmentTable(int appointmentId, String title, String description, String location, String contactName, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId) {
        super(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
        this.contactName = contactName;
    }

    /**
     * This Constructor is used specifically when the appointment type object is already generated and needs to be
     * converted to an AppointmentTable with the additional contact name field info.
     * @param appointment The pre-generated appointment
     * @param contactName The attached contactName associated with the appointment
     */
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
