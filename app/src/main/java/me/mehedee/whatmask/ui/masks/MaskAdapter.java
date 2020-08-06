package me.mehedee.whatmask.ui.masks;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.mehedee.whatmask.databinding.RowMaskBinding;
import me.mehedee.whatmask.storage.db.Mask;

public class MaskAdapter extends RecyclerView.Adapter<MaskViewHolder> {

    private List<Mask> masks;

    public MaskAdapter(List<Mask> masks) {
        this.masks = masks;
    }

    public void setMasks(List<Mask> masks) {
        Log.d("TADA", "MASKS ARE BEING SET..." + masks.toString());
        this.masks = masks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowMaskBinding rowMaskBinding = RowMaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MaskViewHolder(rowMaskBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MaskViewHolder holder, int position) {
        holder.setMast(masks.get(position));
    }

    @Override
    public int getItemCount() {
        return masks.size();
    }
}
