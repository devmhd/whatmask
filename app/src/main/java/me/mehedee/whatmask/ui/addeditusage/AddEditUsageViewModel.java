package me.mehedee.whatmask.ui.addeditusage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.mehedee.whatmask.DateUtil;
import me.mehedee.whatmask.storage.db.DB;
import me.mehedee.whatmask.storage.db.Mask;
import me.mehedee.whatmask.storage.db.UsageHistory;

public class AddEditUsageViewModel extends AndroidViewModel {

    public MutableLiveData<Date> startDate;
    public MutableLiveData<Date> endDate;
    public MutableLiveData<String> startDateString;
    public MutableLiveData<String> endDateString;

    public Mask mask;
    public UsageHistory existingUsageHistory;
    public Boolean isEditingMode;

    public MutableLiveData<Boolean> kamShesh;

    public AddEditUsageViewModel(@NonNull Application application, Mask mask, UsageHistory usageHistory) {
        super(application);

        this.mask = mask;
        this.existingUsageHistory = usageHistory;

        kamShesh = new MutableLiveData<>(false);

        if (usageHistory == null) {

            Date s = new Date(System.currentTimeMillis() - 9 * 3600 * 1000L);
            Date e = new Date();

            startDate = new MutableLiveData<>(s);
            endDate = new MutableLiveData<>(e);

            startDateString = new MutableLiveData<>(DateUtil.getRelativeDayString(s, LocalDate.now()));
            endDateString = new MutableLiveData<>(DateUtil.getRelativeDayString(e, LocalDate.now()));

            isEditingMode = false;

        } else {

            Date s = usageHistory.startTime;
            Date e = usageHistory.endTime;

            startDate = new MutableLiveData<>(s);
            endDate = new MutableLiveData<>(e);

            startDateString = new MutableLiveData<>(DateUtil.getRelativeDayString(s, LocalDate.now()));
            endDateString = new MutableLiveData<>(DateUtil.getRelativeDayString(e, LocalDate.now()));

            isEditingMode = true;
        }


    }

    public void setStartTime(Date d) {
        startDate.setValue(d);
        startDateString.setValue(DateUtil.getRelativeDayString(d, LocalDate.now()));

    }

    public void setEndTime(Date d) {
        endDate.setValue(d);
        endDateString.setValue(DateUtil.getRelativeDayString(d, LocalDate.now()));
    }

    public void mainAction() {
        if (isEditingMode) {

            existingUsageHistory.startTime = startDate.getValue();
            existingUsageHistory.endTime = endDate.getValue();

            DB.getDao(getApplication()).updateUsage(existingUsageHistory)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(l -> {
                        kamShesh.setValue(true);
                    });

        } else { // new

            UsageHistory u = new UsageHistory();
            u.maskId = this.mask.uid;
            u.startTime = startDate.getValue();
            u.endTime = endDate.getValue();

            DB.getDao(getApplication()).insertHistory(u)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(l -> {
                        kamShesh.setValue(true);
                    });

        }
    }


}
