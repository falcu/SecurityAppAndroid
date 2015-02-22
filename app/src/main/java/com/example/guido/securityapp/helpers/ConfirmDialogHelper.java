package com.example.guido.securityapp.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.interfaces.OnYesClickListener;

/**
 * Created by guido on 2/16/15.
 */
public class ConfirmDialogHelper {

    protected OnYesClickListener onYesClickListener = new YesClickListenerNull();

    public void setOnYesClickListener(OnYesClickListener handler)
    {
        this.onYesClickListener = handler;
    }

    public void showYesNoDialog(Context context,String title,String message)
    {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onYesClickListener.onYesClicked();
                    }

                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    public class YesClickListenerNull implements OnYesClickListener
    {

        @Override
        public void onYesClicked() {
            //Nothing to do
        }
    }
}
