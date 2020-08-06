package me.mehedee.whatmask.ui.viewusage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import me.mehedee.whatmask.R;
import me.mehedee.whatmask.databinding.ActivityUsageBinding;
import me.mehedee.whatmask.storage.db.DB;
import me.mehedee.whatmask.storage.db.Mask;
import me.mehedee.whatmask.storage.db.UsageHistory;
import me.mehedee.whatmask.ui.addeditusage.AddEditUsageActivity;
import me.mehedee.whatmask.ui.newmaskdiag.DeleteActualUsageDialog;
import me.mehedee.whatmask.ui.newmaskdiag.DeleteMaskDialog;

public class UsageActivity extends AppCompatActivity {

    Mask selectedMask;
    List<UsageHistory> currentlyShowingHistories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUsageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_usage);

        UsageActivityViewModel vm = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new UsageActivityViewModel(getApplication(), getIntent().getExtras().getParcelable("MASK"));
            }
        }).get(UsageActivityViewModel.class);

        binding.setVm(vm);
        selectedMask = vm.mask;

        binding.rvUsages.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUsages.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvUsages.getContext(),
                RecyclerView.VERTICAL);
        binding.rvUsages.addItemDecoration(dividerItemDecoration);
        UsageDetailAdapter adapter = new UsageDetailAdapter(new ArrayList<>());

        binding.rvUsages.setAdapter(adapter);

        vm.ldActivity.observe(this, usageHistoryList -> {
            currentlyShowingHistories = usageHistoryList;
            adapter.setData(usageHistoryList);
        });

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, item.getTitle() + " " + item.getGroupId(), Toast.LENGTH_SHORT).show();

        UsageHistory selectedUsageHistory = currentlyShowingHistories.get(item.getGroupId());

        switch (item.getTitle().toString()) {
            case "Edit":
                gotoUsageEditActivity(selectedUsageHistory);
                break;

            case "Delete":
                showDeleteDialog(selectedUsageHistory);
        }


        return true;
    }

    private void gotoUsageEditActivity(UsageHistory uh) {

        Intent i = new Intent(UsageActivity.this, AddEditUsageActivity.class);
        i.putExtra("MASK", selectedMask);
        i.putExtra("UH", uh);
        startActivity(i);

    }

    private void showDeleteDialog(UsageHistory deleteCandidateUH) {
        DeleteActualUsageDialog deleteUhDialog = DeleteActualUsageDialog.newInstance(deleteCandidateUH, (convict) -> {

            Log.d(this.getClass().getName(), "onContextItemSelected: Deleting UH: " + convict.toString());

            DB.getDao(getApplicationContext()).deleteUsageHistory(convict)
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        });

        deleteUhDialog.show(getSupportFragmentManager(), "DIAG_DELETE_UH");
    }
}