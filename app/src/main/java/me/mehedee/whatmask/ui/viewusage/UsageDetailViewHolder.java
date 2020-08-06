package me.mehedee.whatmask.ui.viewusage;

import android.view.ContextMenu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.mehedee.whatmask.databinding.RowUsageBinding;

public class UsageDetailViewHolder extends RecyclerView.ViewHolder {

    RowUsageBinding binding;

    public UsageDetailViewHolder(@NonNull RowUsageBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        binding.getRoot().setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(getAdapterPosition(), 2, 0, "Edit");
                menu.add(getAdapterPosition(), 3, 0, "Delete");
            }
        });
    }

    public void setUsageDetail(UsageDetail usageDetail) {
        binding.setUd(usageDetail);
    }
}
