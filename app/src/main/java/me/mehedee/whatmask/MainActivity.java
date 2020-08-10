package me.mehedee.whatmask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.mehedee.whatmask.databinding.ActivityMainBinding;
import me.mehedee.whatmask.model.MaskDetail;
import me.mehedee.whatmask.storage.db.DB;
import me.mehedee.whatmask.storage.db.Mask;
import me.mehedee.whatmask.ui.addeditusage.AddEditUsageActivity;
import me.mehedee.whatmask.ui.newmask.NewMaskActivity;
import me.mehedee.whatmask.ui.newmaskdiag.DeleteMaskDialog;
import me.mehedee.whatmask.ui.viewusage.UsageActivity;

public class MainActivity extends AppCompatActivity {

    private List<MaskDetail> gList;
    MainActivityViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding.setVm(vm);

        MaskDetailAdapter adapter = new MaskDetailAdapter(vm.maskDetails.getValue());

        binding.rvMaskDetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rvMaskDetails.setHasFixedSize(true);
        binding.rvMaskDetails.setAdapter(adapter);

        vm.maskDetails.observe(this, list -> {
            Log.d(this.getClass().getName(), "onCreate: Setting data to rv: " + list.toString());
            adapter.setData(list);
            gList = list;
        });

        binding.fabAdd.setOnClickListener(v -> {
            Log.d(this.getClass().getName(), "FAB CLICKED");

            startActivity(new Intent(MainActivity.this, NewMaskActivity.class));

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.reloadMaskDetails();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Mask candidateMask = gList.get(item.getGroupId()).mask;

        switch (item.getTitle().toString()) {

            case "Add usage":
                gotoAddUsageActivity(candidateMask);
                break;

            case "View usages":
                gotoViewUsageActivity(candidateMask);
                break;

            case "Edit":
                gotoMaskEditActivity(candidateMask);
                break;

            case "Delete":
                showDeleteDialog(candidateMask);
        }


        return true;
    }

    private void gotoMaskEditActivity(Mask candidateMask) {

        Intent i = new Intent(MainActivity.this, NewMaskActivity.class);
        i.putExtra("MASK", candidateMask);
        startActivity(i);

    }

    private void gotoAddUsageActivity(Mask candidateMask) {

        Intent i = new Intent(MainActivity.this, AddEditUsageActivity.class);
        i.putExtra("MASK", candidateMask);
        startActivity(i);

    }
    private void gotoViewUsageActivity(Mask candidateMask) {

        Intent i = new Intent(MainActivity.this, UsageActivity.class);
        i.putExtra("MASK", candidateMask);
        startActivity(i);

    }

    private void showDeleteDialog(Mask candidateMask) {
        DeleteMaskDialog editMaskDialog = DeleteMaskDialog.newInstance(candidateMask, (convict) -> {

            Log.d(this.getClass().getName(), "onContextItemSelected: Deleting mask: " + convict.toString());

            DB.getDao(getApplicationContext())
                    .deleteMask(convict)
                    .mergeWith(DB.getDao(getApplicationContext())
                            .deleteAllUsagesOfMask(convict.uid))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        vm.reloadMaskDetails();
                    });
        });

        editMaskDialog.show(getSupportFragmentManager(), "DIAG_EDIT_MASK");
    }

}