package me.mehedee.whatmask.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import me.mehedee.whatmask.DateUtil;
import me.mehedee.whatmask.service.Safety;
import me.mehedee.whatmask.storage.db.Mask;
import me.mehedee.whatmask.storage.db.UsageHistory;

public class MaskDetail {

    public static final int SAFETY_LEVEL_SAFE = 0;
    public static final int SAFETY_LEVEL_RISKY = 1;
    public static final int SAFETY_LEVEL_DANGEROUS = 2;

    public int safetyLevel;
    public String safetyLevelString;
    public long lastUsedAgoHour;
    public int usageCount;
    public boolean isNew;
    public LocalDateTime effectiveLastUsageTime;
    public Mask mask;


    public MaskDetail(Mask mask, List<UsageHistory> usages) {

        this.mask = mask;
        this.usageCount = usages.size();
        this.isNew = usages.isEmpty();


        if (this.isNew) {

            if (mask.isSafeRightAfterPurchase) {
                this.safetyLevel = SAFETY_LEVEL_SAFE;
                this.effectiveLastUsageTime = mask.purchasedAt.minusYears(1);
            } else {
                this.safetyLevel = Safety.determine(mask.purchasedAt);
                this.effectiveLastUsageTime = mask.purchasedAt;
            }
        } else {

            // Assumed usages is already sorted DESC by endTime
            LocalDateTime lastUsedAt = usages.get(0).endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            this.lastUsedAgoHour = DateUtil.hourAgo(lastUsedAt);
            this.safetyLevel = Safety.determine(lastUsedAt);

            this.effectiveLastUsageTime = lastUsedAt;

        }

        switch (this.safetyLevel) {
            case SAFETY_LEVEL_SAFE:
                this.safetyLevelString = "SAFE TO USE";
                break;
            case SAFETY_LEVEL_RISKY:
                this.safetyLevelString = "RISKY";
                break;
            default:
                this.safetyLevelString = "DANGEROUS";
        }
    }

    @Override
    public String toString() {
        return "MaskDetail{" +
                "safetyLevel=" + safetyLevel +
                ", safetyLevelString='" + safetyLevelString + '\'' +
                ", lastUsedAgoHour=" + lastUsedAgoHour +
                ", usageCount=" + usageCount +
                ", isNew=" + isNew +
                ", mask=" + mask +
                '}';
    }
}
