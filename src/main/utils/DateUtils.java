package utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    private static ZoneId zone = ZoneId.systemDefault();

    public static Date DateFromLocalDate(LocalDate localDate){
        return localDate == null?null:Date.from(localDate.atStartOfDay().atZone(zone).toInstant());
    }
}
