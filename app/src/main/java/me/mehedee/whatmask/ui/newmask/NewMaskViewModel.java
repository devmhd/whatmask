package me.mehedee.whatmask.ui.newmask;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.github.marlonlom.utilities.timeago.TimeAgo;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.mehedee.whatmask.storage.db.DB;
import me.mehedee.whatmask.storage.db.Mask;

public class NewMaskViewModel extends AndroidViewModel {
    public static final SimpleDateFormat df = new SimpleDateFormat("yyyy MMM dd hh:mm a", Locale.US);

    Disposable d;

    public boolean isEditingMode;
    public int maskId;

    public MutableLiveData<String> maskName;
    public MutableLiveData<String> maskModel;
    public MutableLiveData<Date> purchasedAt;
    public MutableLiveData<Boolean> isSafeAfterPurchase;
    public MutableLiveData<String> dateString;
    public MutableLiveData<Boolean> kamShesh;

    public NewMaskViewModel(@NonNull Application application, Mask maskToEdit) {
        super(application);

        kamShesh = new MutableLiveData<>(false);

        if (maskToEdit == null) {
            maskName = new MutableLiveData<>("");
            maskModel = new MutableLiveData<>("");
            purchasedAt = new MutableLiveData<>(new Date());
            isSafeAfterPurchase = new MutableLiveData<>(false);

            isEditingMode = false;
        } else {
            maskName = new MutableLiveData<>(maskToEdit.name);
            maskModel = new MutableLiveData<>(maskToEdit.model);
            purchasedAt = new MutableLiveData<>(new Date(maskToEdit.purchasedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            isSafeAfterPurchase = new MutableLiveData<>(maskToEdit.isSafeRightAfterPurchase);
            maskId = maskToEdit.uid;
            isEditingMode = true;
        }

        dateString = new MutableLiveData<>(TimeAgo.using(purchasedAt.getValue().getTime()));
    }

    public void setPurchasedAt(Date d) {
        purchasedAt.setValue(d);
        dateString.setValue(TimeAgo.using(d.getTime()));
    }


    public void save() {
        Log.d(this.getClass().getName(), "save: " + toString());

        Mask mask = new Mask();
        mask.name = this.maskName.getValue();
        mask.model = this.maskModel.getValue();
        mask.purchasedAt = this.purchasedAt.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        mask.isSafeRightAfterPurchase = this.isSafeAfterPurchase.getValue();

        if (isEditingMode){

            mask.uid = maskId;
            d = DB.getDao(getApplication()).updateMask(mask)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(l -> {
                        kamShesh.setValue(true);
                    });

        } else {

            d = DB.getDao(getApplication()).insertMask(mask)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(l -> {
                        kamShesh.setValue(true);
                    });
        }
    }


    @Override
    public String toString() {
        return "NewMaskViewModel{" +
                "d=" + d +
                ", isEditingMode=" + isEditingMode +
                ", maskId=" + maskId +
                ", maskName=" + maskName +
                ", maskModel=" + maskModel +
                ", purchasedAt=" + purchasedAt +
                ", isSafeAfterPurchase=" + isSafeAfterPurchase +
                ", dateString=" + dateString +
                ", kamShesh=" + kamShesh +
                '}';
    }

    @Override
    protected void onCleared() {

        if (d != null)
            d.dispose();

        super.onCleared();
    }
}
