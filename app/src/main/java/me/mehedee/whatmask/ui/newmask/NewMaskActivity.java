package me.mehedee.whatmask.ui.newmask;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.mehedee.whatmask.R;
import me.mehedee.whatmask.databinding.ActivityNewMaskBinding;
import me.mehedee.whatmask.storage.db.Mask;

public class NewMaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityNewMaskBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_new_mask);
        binding.setLifecycleOwner(this);

        Mask mask = null;
        if (getIntent().hasExtra("MASK")){
            mask = getIntent().getExtras().getParcelable("MASK");
        }

        if (mask!=null)
            getSupportActionBar().setTitle("Change mask properties");
        else
            getSupportActionBar().setTitle("Add new mask");

        Mask finalMask = mask;
        NewMaskViewModel vm = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new NewMaskViewModel(getApplication(), finalMask);
            }
        }).get(NewMaskViewModel.class);
        binding.setVm(vm);

        vm.kamShesh.observe(this, kamShesh ->{
            if (kamShesh)
                this.finish();
        });

        binding.btnPurchaseTimePicker.setOnClickListener(v -> {
            // Initialize
            SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                    "Select mask purchase time",
                    "OK",
                    "Cancel"
            );

            dateTimeDialogFragment.startAtTimeView();
            dateTimeDialogFragment.set24HoursMode(false);
            dateTimeDialogFragment.setMinimumDateTime(new Date(System.currentTimeMillis() - 365 * 86400 * 1000L));
            dateTimeDialogFragment.setMaximumDateTime(new Date());
            dateTimeDialogFragment.setDefaultDateTime(vm.purchasedAt.getValue());

            try {
                dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
            } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
                Log.e(getClass().getName(), e.getMessage(), e);
            }

// Set listener
            dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
                @Override
                public void onPositiveButtonClick(Date date) {
                    vm.setPurchasedAt(date);
                }

                @Override
                public void onNegativeButtonClick(Date date) {

                }
            });


            dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");
        });

    }
}