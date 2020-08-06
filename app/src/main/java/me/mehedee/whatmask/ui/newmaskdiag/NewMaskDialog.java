package me.mehedee.whatmask.ui.newmaskdiag;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.function.Consumer;

import me.mehedee.whatmask.databinding.DialogNewmaskBinding;
import me.mehedee.whatmask.storage.db.Mask;


public class NewMaskDialog extends DialogFragment {

    Consumer<Mask> onCreateMask;

    public NewMaskDialog(Consumer<Mask> onCreateMask) {
        this.onCreateMask = onCreateMask;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DialogNewmaskBinding binding = DialogNewmaskBinding.inflate(LayoutInflater.from(getContext()), null, false);

        return new AlertDialog.Builder(requireActivity())
                .setTitle("Add a new mask")
                .setView(binding.getRoot())
                .setPositiveButton("Add", (dialog, which) -> {
                    Log.d(this.getClass().getName(), "onCreateDialog: Trying to add: " + binding.tilName.getEditText().getText().toString()+ " " + binding.tilModel.getEditText().getText().toString());
                    Mask m = new Mask();
                    m.name = binding.tilName.getEditText().getText().toString();
                    m.model = binding.tilModel.getEditText().getText().toString();

                    onCreateMask.accept(m);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                })
                .create();
    }

}
