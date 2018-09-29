package com.hemendra.citiessearch.view;

import android.app.AlertDialog;
import android.content.Context;

public class MessageBox {

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
