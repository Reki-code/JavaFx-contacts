package main.java.util;

import data.model.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final String PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public static boolean isDefaultDate(LocalDate date) {
        return date != null && date.equals(Person.getDefaultDate());
    }

    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return FORMATTER.format(date);
    }

    public static LocalDate parse(String date) {
        try {
            return FORMATTER.parse(date, LocalDate::from);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean validDate(String date) {
        return parse(date) != null;
    }
}
