package me.mehedee.whatmask.ui.viewusage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

import me.mehedee.whatmask.DateUtil;
import me.mehedee.whatmask.storage.db.UsageHistory;

public class UsageDetail {

    public String relativeDateString;
    public String exactDateString;
    public String startTime;
    public String endTime;
    public long durationHours;

    public UsageDetail(UsageHistory u) {
        this.relativeDateString = DateUtil.getRelativeDayString2(u.startTime, LocalDate.now());
        this.exactDateString = new SimpleDateFormat("MMM dd", Locale.US).format(u.startTime);
        this.startTime = new SimpleDateFormat("ha", Locale.US).format(u.startTime);
        this.endTime = new SimpleDateFormat("ha", Locale.US).format(u.endTime);
        this.durationHours = (u.endTime.getTime() - u.startTime.getTime()) / (3600 * 1000L);
    }
}
