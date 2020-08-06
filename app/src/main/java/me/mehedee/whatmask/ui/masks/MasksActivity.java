package me.mehedee.whatmask.ui.masks;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import me.mehedee.whatmask.R;
import me.mehedee.whatmask.databinding.ActivityMasksBinding;
import me.mehedee.whatmask.storage.db.Mask;
import me.mehedee.whatmask.ui.newmaskdiag.EditMaskDialog;
import me.mehedee.whatmask.ui.newmaskdiag.NewMaskDialog;

public class MasksActivity extends AppCompatActivity {

    List<Mask> maskList;
    MasksActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMasksBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_masks);
        binding.setLifecycleOwner(this);

        vm = new ViewModelProvider(this).get(MasksActivityViewModel.class);


        MaskAdapter adapter = new MaskAdapter(Collections.emptyList());

        binding.rvMasks.setHasFixedSize(true);
        binding.rvMasks.setAdapter(adapter);
        binding.rvMasks.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvMasks.getContext(),
                RecyclerView.VERTICAL);
        binding.rvMasks.addItemDecoration(dividerItemDecoration);

        vm.getLdAllMask().observe(this, masks -> {
            adapter.setMasks(masks);
            this.maskList = masks;
        });

        binding.fabAdd.setOnClickListener(v -> {
            Log.d(this.getClass().getName(), "FAB CLICKED");
            NewMaskDialog d = new NewMaskDialog(mask -> {
                vm.addMask(mask)
                        .subscribeOn(Schedulers.io())
                        .subscribe();
            });
            d.show(getSupportFragmentManager(), "DIAG_NEW_MASK");
        });

        //registerForContextMenu(binding.rvMasks);
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.add("Edit");
//        menu.add("Delete");
//    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, item.getTitle() + " " + item.getGroupId(), Toast.LENGTH_SHORT).show();

        Mask candidateMask = maskList.get(item.getGroupId());

        switch (item.getTitle().toString()) {
            case "Edit":
                showUpdateDialog(candidateMask);
                break;

            case "Delete":
                showDeleteDialog(candidateMask);
        }


        return true;
    }

    private void showUpdateDialog(Mask candidateMask) {
        EditMaskDialog editMaskDialog = EditMaskDialog.newInstance(candidateMask, updatedMask -> {

            Log.d(this.getClass().getName(), "onContextItemSelected: Updated mask: " + updatedMask.toString());

            vm.updateMask(updatedMask)
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        });

        editMaskDialog.show(getSupportFragmentManager(), "DIAG_EDIT_MASK");
    }

    private void showDeleteDialog(Mask candidateMask) {
//        DeleteUsageDialog editMaskDialog = DeleteUsageDialog.newInstance(candidateMask, (convict) -> {
//
//            Log.d(this.getClass().getName(), "onContextItemSelected: Deleting mask: " + convict.toString());
//
//            vm.deleteMask(convict)
//                    .subscribeOn(Schedulers.io())
//                    .subscribe();
//        });
//
//        editMaskDialog.show(getSupportFragmentManager(), "DIAG_EDIT_MASK");
    }
}