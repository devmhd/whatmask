package me.mehedee.whatmask.storage.db;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class Converters {

    @TypeConverter
    public static LocalDateTime fromTimestamp(Long value) {
        return value == null ? null : LocalDateTime.ofInstant(Instant.ofEpochSecond(value), ZoneId.of("UTC"));
    }

    @TypeConverter
    public static Long dateToTimestamp(LocalDateTime date) {
        return date == null ? null : date.toInstant(ZoneOffset.UTC).getEpochSecond();
    }

    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Boolean fromInteger(Integer value) {
        return value == null ? null : value != 0;
    }

    @TypeConverter
    public static Integer fromBoolean(Boolean value) {
        return value == null ? null : value ? 1 : 0;
    }

}
