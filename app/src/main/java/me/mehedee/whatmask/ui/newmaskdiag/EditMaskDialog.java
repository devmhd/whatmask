package me.mehedee.whatmask.ui.newmaskdiag;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.function.Consumer;

import me.mehedee.whatmask.databinding.DialogEditmaskBinding;
import me.mehedee.whatmask.databinding.DialogNewmaskBinding;
import me.mehedee.whatmask.storage.db.Mask;


public class EditMaskDialog extends DialogFragment {

    private Consumer<Mask> onEditMask;
    private Mask mask;

    public static EditMaskDialog newInstance(Mask initMask, Consumer<Mask> onUpdateMask) {
        EditMaskDialog dialog = new EditMaskDialog();
        Bundle bundle = new Bundle();
        bundle.putString("mask", initMask.toJson());
        dialog.setArguments(bundle);
        dialog.onEditMask = onUpdateMask;
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mask = Mask.fromJson(getArguments().getString("mask"));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DialogEditmaskBinding binding = DialogEditmaskBinding.inflate(LayoutInflater.from(getContext()), null, false);

        binding.setMask(mask);
        return new AlertDialog.Builder(requireActivity())
                .setTitle("Change mask name/model")
                .setView(binding.getRoot())
                .setPositiveButton("Update", (dialog, which) -> {
                    Log.d(this.getClass().getName(), "onCreateDialog: Trying to update: " + binding.tilName.getEditText().getText().toString() + " " + binding.tilModel.getEditText().getText().toString());

                    mask.name = binding.tilName.getEditText().getText().toString();
                    mask.model = binding.tilModel.getEditText().getText().toString();

                    onEditMask.accept(mask);
                    dialog.cancel();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                })
                .create();
    }

}
