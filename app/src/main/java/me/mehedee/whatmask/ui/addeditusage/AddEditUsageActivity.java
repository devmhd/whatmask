package me.mehedee.whatmask.ui.addeditusage;

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
import me.mehedee.whatmask.databinding.ActivityAddEditUsageBinding;
import me.mehedee.whatmask.storage.db.Mask;
import me.mehedee.whatmask.storage.db.UsageHistory;

public class AddEditUsageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAddEditUsageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_usage);

        Mask m = getIntent().getExtras().getParcelable("MASK");
        UsageHistory u = getIntent().getExtras().getParcelable("UH");

        if (u == null)
            getSupportActionBar().setTitle("Add new usage entry");
        else
            getSupportActionBar().setTitle("Update usage entry");


        AddEditUsageViewModel vm = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new AddEditUsageViewModel(getApplication(), m, u);
            }
        }).get(AddEditUsageViewModel.class);

        binding.setVm(vm);
        binding.setLifecycleOwner(this);

        vm.kamShesh.observe(this, kamShesh -> {
            if (kamShesh)
                this.finish();
        });

        addStartTimePicker(binding, vm);
        addEndTimePicker(binding, vm);
    }

    private void addStartTimePicker(me.mehedee.whatmask.databinding.ActivityAddEditUsageBinding binding, AddEditUsageViewModel vm) {
        binding.btnStart.setOnClickListener(v -> {
            // Initialize
            SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                    "Select mask usage start time",
                    "OK",
                    "Cancel"
            );

            dateTimeDialogFragment.startAtTimeView();
            dateTimeDialogFragment.set24HoursMode(false);
            dateTimeDialogFragment.setMinimumDateTime(new Date(System.currentTimeMillis() - 365 * 86400 * 1000L));
            dateTimeDialogFragment.setMaximumDateTime(new Date());
            dateTimeDialogFragment.setDefaultDateTime(vm.startDate.getValue());

            try {
                dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
            } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
                Log.e(getClass().getName(), e.getMessage(), e);
            }


            dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
                @Override
                public void onPositiveButtonClick(Date date) {
                    vm.setStartTime(date);
                }

                @Override
                public void onNegativeButtonClick(Date date) {

                }
            });


            dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");
        });
    }

    private void addEndTimePicker(me.mehedee.whatmask.databinding.ActivityAddEditUsageBinding binding, AddEditUsageViewModel vm) {
        binding.btnEnd.setOnClickListener(v -> {
            // Initialize
            SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                    "Select mask usage end time",
                    "OK",
                    "Cancel"
            );

            dateTimeDialogFragment.startAtTimeView();
            dateTimeDialogFragment.set24HoursMode(false);
            dateTimeDialogFragment.setMinimumDateTime(new Date(System.currentTimeMillis() - 365 * 86400 * 1000L));
            dateTimeDialogFragment.setMaximumDateTime(new Date());
            dateTimeDialogFragment.setDefaultDateTime(vm.endDate.getValue());

            try {
                dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
            } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
                Log.e(getClass().getName(), e.getMessage(), e);
            }


            dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
                @Override
                public void onPositiveButtonClick(Date date) {
                    vm.setEndTime(date);
                }

                @Override
                public void onNegativeButtonClick(Date date) {

                }
            });


            dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");
        });
    }
}