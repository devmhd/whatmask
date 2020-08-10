package me.mehedee.whatmask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.github.marlonlom.utilities.timeago.TimeAgo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.mehedee.whatmask.model.MaskDetail;
import me.mehedee.whatmask.storage.db.DB;
import me.mehedee.whatmask.storage.repo.Repository;

public class MainActivityViewModel extends AndroidViewModel {

    Disposable d;

    public final MutableLiveData<List<MaskDetail>> maskDetails;
    public final MutableLiveData<Boolean> isLoading;
    public final MutableLiveData<String> lastUsageAgo;
    public final MutableLiveData<String> lastUsageDateString;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        maskDetails = new MutableLiveData<>(new ArrayList<>());
        isLoading = new MutableLiveData<>(true);
        lastUsageAgo = new MutableLiveData<>("");
        lastUsageDateString = new MutableLiveData<>("");

    }

    public void reloadMaskDetails() {
        isLoading.setValue(true);

        d = Observable.fromCallable(() -> Repository.getMaskDetails(getApplication()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    maskDetails.setValue(list);
                    isLoading.setValue(false);
                });

        DB.getDao(getApplication()).getMaxUsageTime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultDate -> {

                    lastUsageAgo.setValue(TimeAgo.using(resultDate.getTime()));
                    lastUsageDateString.setValue(new SimpleDateFormat("MMM dd, hh:mm a").format(resultDate));

                });
    }

    @Override
    protected void onCleared() {

        if (d != null)
            d.dispose();

        super.onCleared();
    }
}
