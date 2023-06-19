package Utilities;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;

/**
 * The Abstract TimeConverter class is a utility that converts different timezones (e.g. the business EST time, local
 * machine time, and database UTC time), stores business hours of operation and determines other time/date calculations.
 *
 * @author Michael Short
 * @version 1.0
 */
public abstract class TimeConverter {
    private static final ZoneId userTimeZone = ZoneId.systemDefault();
    //private static final ZoneId userTimeZone = ZoneId.of("Pacific/Midway");
    private static final ZoneId businessTimeZone = ZoneId.of("America/New_York");
    private static final ZoneId universalTimeZone = ZoneId.of("UTC");
    private static final ZoneOffset userOffset = ZonedDateTime.now(userTimeZone).getOffset();
    private static final ZoneOffset estOffset = businessTimeZone.getRules().getOffset(LocalDateTime.now());
    private static final ZoneOffset offset = ZoneOffset.ofTotalSeconds(userOffset.getTotalSeconds() - estOffset.getTotalSeconds());
    private static final LocalTime businessOpeningTime = LocalTime.of(8,0);
    private static final LocalTime businessClosingTime = LocalTime.of(22,0);
    private static final int hoursOpen = (int) businessOpeningTime.until(businessClosingTime, ChronoUnit.HOURS);

    public static int getHoursOpen(){
        return hoursOpen;
    }

    /**
     * Utility function that determines whether a LocalDateTime range is within business hours. Converts and compares
     * from business EST to UTC vs local machine time to UTC.
     * @param selectedStartDateTime start date and time range
     * @param selectedEndDateTime end date and time range
     * @return a bool of whether the range is within business hours
     */
    public static boolean duringBusinessHours(LocalDateTime selectedStartDateTime, LocalDateTime selectedEndDateTime){
        ZonedDateTime convertedStartTime = convertToUTC(ZonedDateTime.of(selectedStartDateTime, userTimeZone));
        ZonedDateTime convertedEndTime = convertToUTC(ZonedDateTime.of(selectedEndDateTime, userTimeZone));
        ZonedDateTime utcOpeningDateTime = convertToUTC(ZonedDateTime.of(LocalDateTime.of(convertedStartTime.toLocalDate(),
                businessOpeningTime),businessTimeZone));
        ZonedDateTime utcClosingDateTime = utcOpeningDateTime.plusHours((long)hoursOpen);
                //convertToUTC(ZonedDateTime.of(LocalDateTime.of(convertedEndTime.toLocalDate(), businessClosingTime),businessTimeZone));
        boolean startIsWithinRange = ((convertedStartTime.isEqual(utcOpeningDateTime) || convertedStartTime.isAfter(utcOpeningDateTime))
                && convertedStartTime.isBefore(utcClosingDateTime));
        boolean endIsWithinRange = ((convertedEndTime.isBefore(utcClosingDateTime) || convertedEndTime.isEqual(utcClosingDateTime)) && convertedEndTime.isAfter(utcOpeningDateTime));
        return (startIsWithinRange && endIsWithinRange);
    }

    public static ZoneOffset getOffset(){
        return offset;
    }

    public static int getOffsetHour(int businessHour){
        LocalTime localTime = LocalTime.of(businessHour,0).plusSeconds(offset.getTotalSeconds());
        return localTime.getHour();
    }

    public static ZonedDateTime convertToUTC(ZonedDateTime zonedDateTime){
        return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
    }

    public static ZonedDateTime convertToUTC(LocalDateTime localDateTime){
        return ZonedDateTime.of(localDateTime, userTimeZone).withZoneSameInstant(ZoneOffset.UTC);
    }

    public static LocalDateTime convertFromUTC(LocalDateTime utcLocalDateTime){
        return utcLocalDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(userTimeZone).toLocalDateTime();
    }

    public static LocalTime getBusinessOpeningTime() {
        return businessOpeningTime;
    }

    public static LocalTime getBusinessClosingTime() {
        return businessClosingTime;
    }

    public static ZoneId getUserTimeZone() {
        return userTimeZone;
    }

    public static ZoneId getBusinessTimeZone() {
        return businessTimeZone;
    }

    public static ZoneId getUniversalTimeZone() {
        return universalTimeZone;
    }

    public static int getWeekNumber(LocalDateTime localDateTime){
        WeekFields weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1);
        return localDateTime.get(weekFields.weekOfWeekBasedYear());
    }

}
