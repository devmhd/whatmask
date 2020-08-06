package me.mehedee.whatmask.storage.repo;

import android.content.Context;

import java.util.List;
import java.util.stream.Collectors;

import me.mehedee.whatmask.model.MaskDetail;
import me.mehedee.whatmask.storage.db.DB;
import me.mehedee.whatmask.storage.db.Mask;

public class Repository {

    public static List<MaskDetail> getMaskDetails(Context context) {
        List<Mask> allMasks = DB.getDao(context).getAllMasks();

        return allMasks.stream()
                .map(mask -> new MaskDetail(mask, DB.getDao(context).getMaskHistory(mask.uid)))
                .sorted((m1, m2) -> m1.effectiveLastUsageTime.compareTo(m2.effectiveLastUsageTime))
                .collect(Collectors.toList());

    }

}
