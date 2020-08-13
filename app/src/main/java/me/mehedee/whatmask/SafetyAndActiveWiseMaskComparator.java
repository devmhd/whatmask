package me.mehedee.whatmask;

import java.util.Comparator;

import me.mehedee.whatmask.model.MaskDetail;

public class SafetyAndActiveWiseMaskComparator implements Comparator<MaskDetail> {
    @Override
    public int compare(MaskDetail m1, MaskDetail m2) {

        if ((m1.mask.isActive && m2.mask.isActive) || (!m1.mask.isActive && !m2.mask.isActive))
            return m1.effectiveLastUsageTime.compareTo(m2.effectiveLastUsageTime);

        if (m1.mask.isActive)
            return -1;
        else return 1;

    }
}
