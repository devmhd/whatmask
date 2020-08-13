package me.mehedee.whatmask.ui.stylepicker;

import androidx.recyclerview.widget.RecyclerView;

import me.mehedee.whatmask.databinding.CellMaskstyleBinding;

public class CellMaskStyleViewHolder extends RecyclerView.ViewHolder {

    CellMaskstyleBinding binding;

    public CellMaskStyleViewHolder(CellMaskstyleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setImgResId(int resId) {
        binding.setDrawableResId(resId);
    }

}
