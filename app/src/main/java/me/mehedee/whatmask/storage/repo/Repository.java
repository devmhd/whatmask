package me.mehedee.whatmask.storage.repo;

import android.content.Context;

import java.util.List;
import java.util.stream.Collectors;

import me.mehedee.whatmask.SafetyAndActiveWiseMaskComparator;
import me.mehedee.whatmask.model.MaskDetail;
import me.mehedee.whatmask.storage.db.DB;
import me.mehedee.whatmask.storage.db.Mask;

public class Repository {

    public static List<MaskDetail> getMaskDetails(Context context) {
        List<Mask> allMasks = DB.getDao(context).getAllMasks();

        return allMasks.stream()
                .map(mask -> new MaskDetail(mask, DB.getDao(context).getMaskHistory(mask.uid)))
                .sorted(new SafetyAndActiveWiseMaskComparator())
                .collect(Collectors.toList());

    }

}
