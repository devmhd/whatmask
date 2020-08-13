package me.mehedee.whatmask.ui.stylepicker;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import me.mehedee.whatmask.databinding.CellMaskstyleBinding;
import me.mehedee.whatmask.storage.db.Mask;

public class MaskStylePickerListAdapter extends RecyclerView.Adapter<CellMaskStyleViewHolder> {

    private List<Integer> resourceIds;
    Consumer<Integer> onClickPosition;

    public MaskStylePickerListAdapter(Consumer<Integer> onClickPosition) {
        resourceIds = IntStream.range(0, 9)
                .boxed()
                .map(Mask::drawableFromStyle)
                .collect(Collectors.toList());

        this.onClickPosition = onClickPosition;
    }

    @NonNull
    @Override
    public CellMaskStyleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CellMaskStyleViewHolder(
                CellMaskstyleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CellMaskStyleViewHolder holder, int position) {
        holder.setImgResId(resourceIds.get(position));
        holder.itemView.setOnClickListener(v -> {
            onClickPosition.accept(position);
        });
    }

    @Override
    public int getItemCount() {
        return resourceIds.size();
    }
}
