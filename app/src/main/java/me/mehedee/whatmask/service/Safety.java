package me.mehedee.whatmask.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import me.mehedee.whatmask.model.MaskDetail;

import static me.mehedee.whatmask.DateUtil.hourAgo;

public class Safety {
    public static final int RISKY_GAP_HOURS = 72;
    public static final int SAFE_GAP_HOURS = 96;


    public static int determine(long hourDiff){
        if (hourDiff >= SAFE_GAP_HOURS)
            return MaskDetail.SAFETY_LEVEL_SAFE;
        if (hourDiff >= RISKY_GAP_HOURS)
            return MaskDetail.SAFETY_LEVEL_RISKY;

        return MaskDetail.SAFETY_LEVEL_DANGEROUS;
    }

    public static int determine(LocalDateTime lastWornAt){
        return determine(hourAgo(lastWornAt));
    }

}
