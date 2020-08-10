package me.mehedee.whatmask;

import java.util.Comparator;

import me.mehedee.whatmask.model.MaskDetail;

public class SafetyAndActiveWiseMaskComparator implements Comparator<MaskDetail> {
    @Override
    public int compare(MaskDetail m1, MaskDetail m2) {

        if ((m1.isActive && m2.isActive) || (!m1.isActive && !m2.isActive))
            return m1.effectiveLastUsageTime.compareTo(m2.effectiveLastUsageTime);

        if (m1.isActive)
            return -1;
        else return 1;

    }
}
