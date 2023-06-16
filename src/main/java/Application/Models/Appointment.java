package Application.Models;

import Utilities.TimeConverter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;
    private Month month;

    /**
     * This constructor is used primarily for updating and adding to the local cache for converting POJOs to a MySQL DB
     * record(s) in the appointment table. Note that the start and end parameter are LocalDateTime types that are converted
     * to UTC via an offset from the local machine time zone to comply with the DB storage format.
     *
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @param contactId
     */
    public Appointment(int appointmentId, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId){
        LocalDateTime utcDateTimeStart = TimeConverter.convertToUTC(start).toLocalDateTime();
        LocalDateTime utcDateTimeEnd = TimeConverter.convertToUTC(end).toLocalDateTime();
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = utcDateTimeStart;
        this.end = utcDateTimeEnd;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * This constructor is used primarily for building local cache POJOs when pulling from the MySQL DB appointment table.
     * Specifically note the start and end parameter are Timestamps types that are converted to LocalDateTime objects
     * but retain UTC zone standard so updating and adding to the DB does require conversion as it is already stored in
     * its correct format.
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @param contactId
     */
    public Appointment(int appointmentId, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, int customerId, int userId, int contactId){
        LocalDateTime convertedStart = start.toLocalDateTime();
        LocalDateTime convertedEnd = end.toLocalDateTime();
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = convertedStart;
        this.end = convertedEnd;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return TimeConverter.convertFromUTC(start);
    }

    public LocalDateTime getUTCStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = TimeConverter.convertToUTC(start).toLocalDateTime();
    }

    public LocalDateTime getEnd() {
        return TimeConverter.convertFromUTC(end);
    }

    public LocalDateTime getUTCEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = TimeConverter.convertToUTC(end).toLocalDateTime();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public Month getMonth(){
        return start.getMonth();
    }
}
