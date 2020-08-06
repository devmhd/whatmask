package me.mehedee.whatmask.ui.viewusage;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import me.mehedee.whatmask.databinding.RowUsageBinding;
import me.mehedee.whatmask.storage.db.UsageHistory;

public class UsageDetailAdapter extends RecyclerView.Adapter<UsageDetailViewHolder> {

    private List<UsageDetail> list;

    public UsageDetailAdapter(List<UsageDetail> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public UsageDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsageDetailViewHolder(
                RowUsageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UsageDetailViewHolder holder, int position) {
        holder.setUsageDetail(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<UsageHistory> usageHistoryList) {
        list.clear();
        list.addAll(usageHistoryList.stream().map(UsageDetail::new).collect(Collectors.toList()));
        notifyDataSetChanged();
    }
}
