package me.mehedee.whatmask.ui.masks;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Single;
import me.mehedee.whatmask.storage.db.DB;
import me.mehedee.whatmask.storage.db.Mask;

public class MasksActivityViewModel extends AndroidViewModel {

    public LiveData<List<Mask>> ldAllMask;
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);

    public MasksActivityViewModel(Application app) {
        super(app);

        ldAllMask = DB.getDao(getApplication()).getAllMasksLd();

    }

    public LiveData<List<Mask>> getLdAllMask() {
        return ldAllMask;
    }

    public Single<Long> addMask(Mask m) {
        return DB.getDao(getApplication()).insertMask(m);
    }

    public Single<Integer> updateMask(Mask m) {
        return DB.getDao(getApplication()).updateMask(m);
    }
    public Single<Integer> deleteMask(Mask m) {
        return DB.getDao(getApplication()).deleteMask(m);
    }
}
