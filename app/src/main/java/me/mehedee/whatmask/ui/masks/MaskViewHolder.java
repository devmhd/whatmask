package me.mehedee.whatmask.ui.masks;

import android.view.ContextMenu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.mehedee.whatmask.databinding.RowMaskBinding;
import me.mehedee.whatmask.storage.db.Mask;

public class MaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    RowMaskBinding binding;

    public MaskViewHolder(@NonNull RowMaskBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        binding.getRoot().setOnCreateContextMenuListener(this);
    }

    public void setMast(Mask mask) {
        binding.setMask(mask);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(getAdapterPosition(), 0, 0, "Edit");
        menu.add(getAdapterPosition(), 2, 0, "Delete");
    }
}
