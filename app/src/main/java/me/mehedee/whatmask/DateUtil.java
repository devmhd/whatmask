package me.mehedee.whatmask;

import com.github.marlonlom.utilities.timeago.TimeAgo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static final SimpleDateFormat df = new SimpleDateFormat("hh:mm a", Locale.US);

    public static String getRelativeDayString(Date date, LocalDate today) {
        long then = date.getTime();

        long todayZeroHour = today.atTime(LocalTime.MIN).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
        long yesterdayZeroHour = today.minusDays(1).atTime(LocalTime.MIN).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
        long porshuZeroHour = today.minusDays(2).atTime(LocalTime.MIN).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();

        if (then < porshuZeroHour)
            return TimeAgo.using(then);
        else if (then < yesterdayZeroHour)
            return "Porshu " + df.format(date);
        else if (then < todayZeroHour)
            return "Yesterday " + df.format(date);
        else
            return "Today " + df.format(date);
    }

    public static String getRelativeDayString2(Date date, LocalDate today) {
        long then = date.getTime();

        long todayZeroHour = today.atTime(LocalTime.MIN).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
        long yesterdayZeroHour = today.minusDays(1).atTime(LocalTime.MIN).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
        long porshuZeroHour = today.minusDays(2).atTime(LocalTime.MIN).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();

        if (then < porshuZeroHour)
            return TimeAgo.using(then);
        else if (then < yesterdayZeroHour)
            return "Porshu";
        else if (then < todayZeroHour)
            return "Yesterday";
        else
            return "Today";
    }

    public static long hourAgo(LocalDateTime time){
        return ChronoUnit.HOURS.between(time, LocalDateTime.now());
    }
}
