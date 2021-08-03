package cn.haier.bio.medical.bioauth.dialog;

import android.app.Dialog;

public interface CustomDialogListener {
    void onCancelClicked(Dialog dialog, Object object);
    void onConfirmClicked(Dialog dialog, Object object);
}
