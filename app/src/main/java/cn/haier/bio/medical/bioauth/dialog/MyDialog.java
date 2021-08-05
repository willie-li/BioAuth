package cn.haier.bio.medical.bioauth.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import cn.haier.bio.medical.bioauth.R;
import cn.haier.bio.medical.bioauthlibrary.BioAuthManager;

public class MyDialog extends Dialog implements View.OnClickListener {
    private String screenCode;
    private Context mContext;
    private CustomDialogListener listener;
    private EditText mSnCodeEt;
    private EditText mAuthCodeEt;
    private Button mConfirmBtn;
    private RelativeLayout mCloseBtn;


    public MyDialog(@NonNull Context context, CustomDialogListener listener, String screenCode,int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
        this.screenCode = screenCode;
    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_device);
        mSnCodeEt = findViewById(R.id.et_sn_code);
        mAuthCodeEt = findViewById(R.id.et_auth_code);
        mConfirmBtn = findViewById(R.id.confirm_btn);
        mCloseBtn = findViewById(R.id.ib_close_btn);
        mCloseBtn.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        if(!TextUtils.isEmpty(screenCode)){
            mSnCodeEt.setText(screenCode);
            mSnCodeEt.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_close_btn:
                this.dismiss();
                break;
            case R.id.confirm_btn:
                BioAuthManager.getInstance().checkAuthNoSN(mAuthCodeEt.getText().toString(),"vims_bigsmallscreen");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
