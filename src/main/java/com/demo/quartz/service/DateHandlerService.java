package com.demo.quartz.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateHandlerService {

    public static Date addHours(long value) {
        return addHours(new Date(), value);
    }

    private static ZonedDateTime dateToZonedDateTime(Date date) {
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    public static Date addHours(Date date, long value) {
        ZonedDateTime zonedDateTime = dateToZonedDateTime(date);
        zonedDateTime = zonedDateTime.plusHours(value);
        return localDateTimeToDate(zonedDateTime.toLocalDateTime());
    }
}
