package com.hemendra.citiessearch.view;

import android.app.AlertDialog;
import android.content.Context;

public class MessageBox {

    /**
     * Show the message to user using {@link AlertDialog}
     * @param context The application context or activity instance.
     * @param msg The message to show.
     * @param onOkClicked Task to run when user clicked 'OK' or when dialog gets cancelled.
     */
    public static void showMessage(Context context, String msg, Runnable onOkClicked) {
        AlertDialog dialog = new AlertDialog.Builder(context).setCancelable(true)
                .setMessage(msg)
                .setPositiveButton("OK", (d, which) -> {
                    if(onOkClicked != null) onOkClicked.run();
                }).setOnCancelListener(d -> {
                    if(onOkClicked != null) onOkClicked.run();
                }).create();
        dialog.show();
    }

}
