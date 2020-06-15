package info.devram.tizori.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import info.devram.tizori.R;

public class ConfirmDialog extends DialogFragment {

    public interface ConfirmDialogListener {
        public void onCancelClick(DialogFragment dialogFragment);
        public void onOkClick(DialogFragment dialogFragment);
    }

    private AlertDialog.Builder builder;
    //private static final String TAG = "ConfirmDialog";
    private ConfirmDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.confirm_dialog,null);

        TextView titleTextView = view.findViewById(R.id.title_txt_view);
        TextView messageTextView = view.findViewById(R.id.message_txt_view);

        Button cancelButton = view.findViewById(R.id.cancel_btn);
        Button okButton = view.findViewById(R.id.ok_btn);

        titleTextView.setText("Confirm Delete");
        messageTextView.setText("Are You Sure!");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancelClick(ConfirmDialog.this);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOkClick(ConfirmDialog.this);
            }
        });

        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ConfirmDialogListener) context;

    }
}
