package me.mehedee.whatmask;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.mehedee.whatmask.databinding.RowMaskdetailBinding;
import me.mehedee.whatmask.model.MaskDetail;

public class MaskDetailAdapter extends RecyclerView.Adapter<MaskDetailViewHolder> {

    private List<MaskDetail> list;

    public MaskDetailAdapter(List<MaskDetail> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MaskDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MaskDetailViewHolder(
                RowMaskdetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MaskDetailViewHolder holder, int position) {
        holder.setMaskDetail(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<MaskDetail> newMaskDetails){
        list.clear();
        list.addAll(newMaskDetails);
        notifyDataSetChanged();
    }
}
