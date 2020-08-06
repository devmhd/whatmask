package me.mehedee.whatmask.storage.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MaskDao {

    @Insert
    Single<Long> insertMask(Mask mask);

    @Update
    Single<Integer> updateMask(Mask mask);

    @Update
    Single<Integer> updateUsage(UsageHistory u);

    @Delete
    Single<Integer> deleteMask(Mask mask);

    @Delete
    Single<Integer> deleteUsageHistory(UsageHistory uh);

    @Insert
    Single<Long> insertHistory(UsageHistory history);

    @Query("select * from UsageHistory WHERE mask_Id = :maskId ORDER BY end_time DESC")
    List<UsageHistory> getMaskHistory(int maskId);

    @Query("select * from UsageHistory WHERE mask_Id = :maskId ORDER BY end_time DESC")
    LiveData<List<UsageHistory>> getMaskHistoryLd(int maskId);

    @Query("SELECT * from Mask")
    LiveData<List<Mask>> getAllMasksLd();

    @Query("SELECT * from Mask")
    List<Mask> getAllMasks();
}
