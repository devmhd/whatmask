package me.mehedee.whatmask.ui.stylepicker;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.function.Consumer;

import me.mehedee.whatmask.databinding.DialogPickstyleBinding;

public class MaskStylePickerDialog extends DialogFragment {

    Consumer<Integer> onClickStylePosition;

    public MaskStylePickerDialog(Consumer<Integer> onClickStylePosition) {
        super();
        this.onClickStylePosition = onClickStylePosition;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DialogPickstyleBinding binding = DialogPickstyleBinding.inflate(LayoutInflater.from(getContext()));

        binding.rvStyles.setHasFixedSize(true);
        binding.rvStyles.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvStyles.setAdapter(new MaskStylePickerListAdapter(i -> {
            onClickStylePosition.accept(i);
            dismiss();
        }));

        return new AlertDialog.Builder(requireActivity())
                .setTitle("Choose a style")
                .setView(binding.getRoot())
//                .setPositiveButton("Update", (dialog, which) -> {
//                    Log.d(this.getClass().getName(), "onCreateDialog: Trying to update: " + binding.tilName.getEditText().getText().toString() + " " + binding.tilModel.getEditText().getText().toString());
//
//                    mask.name = binding.tilName.getEditText().getText().toString();
//                    mask.model = binding.tilModel.getEditText().getText().toString();
//
//                    onEditMask.accept(mask);
//                    dialog.cancel();
//                })
//                .setNegativeButton("Cancel", (dialog, which) -> {
//                    dialog.cancel();
//                })
                .create();
    }
}
