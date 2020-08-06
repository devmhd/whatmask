package me.mehedee.whatmask;

import com.github.marlonlom.utilities.timeago.TimeAgo;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void timeAgo_today() {

        LocalDateTime l = LocalDate.now().atTime(LocalTime.MIN);

        Assert.assertEquals("Today", TimeAgo.using(l.toInstant(ZoneOffset.ofHours(6)).toEpochMilli()));
    }
}