package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable>  boolean isBetween(T lt, T startTime, T endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static LocalDate parseDate(String localDate){
        try {
            return LocalDate.parse(localDate);
        } catch (DateTimeParseException e){
            return null;
        } catch (NullPointerException e){
            return null;
        }
    }

    public static LocalTime parseTime(String localDate){
        try {
            return LocalTime.parse(localDate);
        } catch (DateTimeParseException e){
            return null;
        } catch (NullPointerException e){
            return null;
        }
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
