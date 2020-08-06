package me.mehedee.whatmask;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;

public class DateUtilsTests {

    @Test
    public void today1105() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2020);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DATE, 1);

        c.set(Calendar.HOUR_OF_DAY, 11);
        c.set(Calendar.MINUTE, 5);
        c.set(Calendar.SECOND, 0);


        String s = DateUtil.getRelativeDayString(c.getTime(), LocalDate.of(2020, 1, 1));

        Assert.assertEquals("Today 11:05 AM", s);
    }

    @Test
    public void today1610() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2020);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DATE, 1);

        c.set(Calendar.HOUR_OF_DAY, 16);
        c.set(Calendar.MINUTE, 10);
        c.set(Calendar.SECOND, 56);


        String s = DateUtil.getRelativeDayString(c.getTime(), LocalDate.of(2020, 1, 1));

        Assert.assertEquals("Today 04:10 PM", s);
    }

    @Test
    public void yesterday1610() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2020);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DATE, 1);

        c.set(Calendar.HOUR_OF_DAY, 16);
        c.set(Calendar.MINUTE, 10);
        c.set(Calendar.SECOND, 56);


        String s = DateUtil.getRelativeDayString(c.getTime(), LocalDate.of(2020, 1, 2));

        Assert.assertEquals("Yesterday 04:10 PM", s);
    }

    @Test
    public void beforeYesterday1610() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2020);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DATE, 1);

        c.set(Calendar.HOUR_OF_DAY, 16);
        c.set(Calendar.MINUTE, 10);
        c.set(Calendar.SECOND, 56);


        String s = DateUtil.getRelativeDayString(c.getTime(), LocalDate.of(2020, 1, 3));

        Assert.assertEquals("Porshu 04:10 PM", s);
    }
}
