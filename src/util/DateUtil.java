package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final String PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    // TODO: default time
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
