package Utilities;

import java.time.*;
import java.time.temporal.ChronoUnit;

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

    public static boolean duringBusinessHours(LocalDateTime selectedStartDateTime, LocalDateTime selectedEndDateTime){
        ZonedDateTime convertedStartTime = convertToUTC(ZonedDateTime.of(selectedStartDateTime, userTimeZone));
        ZonedDateTime convertedEndTime = convertToUTC(ZonedDateTime.of(selectedEndDateTime, userTimeZone));
        ZonedDateTime utcOpeningDateTime = convertToUTC(ZonedDateTime.of(LocalDateTime.of(convertedStartTime.toLocalDate(),businessOpeningTime),businessTimeZone));
        ZonedDateTime utcClosingDateTime = convertToUTC(ZonedDateTime.of(LocalDateTime.of(convertedEndTime.toLocalDate(),businessClosingTime),businessTimeZone));
        return (((convertedStartTime.isEqual(utcOpeningDateTime) || convertedStartTime.isAfter(utcOpeningDateTime))
                && convertedStartTime.isBefore(utcClosingDateTime))
        && (convertedEndTime.isBefore(utcClosingDateTime) || convertedEndTime.isEqual(utcClosingDateTime)));
    }

    public static ZoneOffset getOffset(){
        return offset;
    }

    public static int getOffsetHour(int businessHour){
        //OffsetTime offsetTime = OffsetTime.of(businessHour, 0, 0, 0, offset);
        LocalTime localTime = LocalTime.of(businessHour,0).plusSeconds(offset.getTotalSeconds());
        return localTime.getHour();
    }

    public static ZonedDateTime convertToUTC(ZonedDateTime zonedDateTime){
        return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
    }

    public static ZonedDateTime convertToUTC(LocalDateTime localDateTime){
        return ZonedDateTime.of(localDateTime, userTimeZone).withZoneSameInstant(ZoneOffset.UTC);
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

}
