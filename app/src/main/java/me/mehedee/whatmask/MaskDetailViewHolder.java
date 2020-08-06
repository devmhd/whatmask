package me.mehedee.whatmask;

import android.view.ContextMenu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.mehedee.whatmask.databinding.RowMaskdetailBinding;
import me.mehedee.whatmask.model.MaskDetail;

public class MaskDetailViewHolder extends RecyclerView.ViewHolder {

    RowMaskdetailBinding binding;

    public MaskDetailViewHolder(@NonNull RowMaskdetailBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        binding.getRoot().setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(getAdapterPosition(), 1, 0, "Add usage");
                menu.add(getAdapterPosition(), 2, 0, "View usages");
                menu.add(getAdapterPosition(), 3, 0, "Edit");
                menu.add(getAdapterPosition(), 4, 0, "Delete");
            }
        });
    }

    public void setMaskDetail(MaskDetail maskDetail) {
        binding.setMaskdetail(maskDetail);
    }


}
