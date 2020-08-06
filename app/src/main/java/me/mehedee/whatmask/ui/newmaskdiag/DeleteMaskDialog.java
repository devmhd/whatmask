package me.mehedee.whatmask.ui.newmaskdiag;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.function.Consumer;

import me.mehedee.whatmask.storage.db.Mask;


public class DeleteMaskDialog extends DialogFragment {

    private Consumer<Mask> onDelete;
    private Mask mask;

    public static DeleteMaskDialog newInstance(Mask initMask, Consumer<Mask> onDelete) {
        DeleteMaskDialog dialog = new DeleteMaskDialog();
        Bundle bundle = new Bundle();
        bundle.putString("mask", initMask.toJson());
        dialog.setArguments(bundle);
        dialog.onDelete = onDelete;
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mask = Mask.fromJson(getArguments().getString("mask"));
        Log.d(this.getClass().getName(), "onCreate: ");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new AlertDialog.Builder(requireActivity())
                .setTitle("DELETE ?")
                .setMessage("Sure you want to delete " + mask.name + " ?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    Log.d(this.getClass().getName(), "onCreateDialog: Trying to delete: " + mask.toString());

                    onDelete.accept(mask);
                    dialog.cancel();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                })
                .create();
    }

}
