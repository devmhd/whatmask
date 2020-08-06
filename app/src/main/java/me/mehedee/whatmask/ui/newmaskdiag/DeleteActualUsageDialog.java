package me.mehedee.whatmask.ui.newmaskdiag;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.function.Consumer;

import me.mehedee.whatmask.storage.db.UsageHistory;


public class DeleteActualUsageDialog extends DialogFragment {

    private Consumer<UsageHistory> onDelete;
    private UsageHistory deleteCandidateUH;

    public static DeleteActualUsageDialog newInstance(UsageHistory uh, Consumer<UsageHistory> onDelete) {
        DeleteActualUsageDialog dialog = new DeleteActualUsageDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("uh", uh);
        dialog.setArguments(bundle);
        dialog.onDelete = onDelete;
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteCandidateUH = getArguments().getParcelable("uh");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new AlertDialog.Builder(requireActivity())
                .setTitle("DELETE ?")
                .setMessage("Sure you want to delete the usage?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    Log.d(this.getClass().getName(), "onCreateDialog: Trying to delete: " + deleteCandidateUH.toString());

                    onDelete.accept(deleteCandidateUH);
                    dialog.cancel();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                })
                .create();
    }

}
