package me.mehedee.whatmask.ui.viewusage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import me.mehedee.whatmask.storage.db.DB;
import me.mehedee.whatmask.storage.db.Mask;
import me.mehedee.whatmask.storage.db.UsageHistory;

public class UsageActivityViewModel extends AndroidViewModel {

    public Mask mask;
    public LiveData<List<UsageHistory>> ldActivity;

    public UsageActivityViewModel(@NonNull Application application, Mask mask) {
        super(application);
        this.mask = mask;

        ldActivity = DB.getDao(getApplication()).getMaskHistoryLd(mask.uid);

    }
}
