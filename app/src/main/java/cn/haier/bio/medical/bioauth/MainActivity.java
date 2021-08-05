package cn.haier.bio.medical.bioauth;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.haier.bio.medical.bioauth.dialog.CustomDialogListener;
import cn.haier.bio.medical.bioauth.dialog.MyDialog;
import cn.haier.bio.medical.bioauthlibrary.BioAuthListener;
import cn.haier.bio.medical.bioauthlibrary.BioAuthManager;

public class MainActivity extends AppCompatActivity implements BioAuthListener, View.OnClickListener, CustomDialogListener {
    private final int AUTH_ERROR = 0x11;
    private final int AUTH_SUCCESS = 0x12;
    private final int AUTH_FAILURE = 0x13;

    private MyDialog mSnDialog;
    private MyDialog mNoSnDialog;
    private Button mSnBtn;
    private Button mNoSnBtn;
    private MyHandler handler;
    private HandlerThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createHandler();
        BioAuthManager.getInstance().init(this,this);
        mSnBtn = findViewById(R.id.btn_have_sn);
        mSnBtn.setOnClickListener(this);
        mNoSnBtn = findViewById(R.id.btn_no_sn);
        mNoSnBtn.setOnClickListener(this);

    }

    private void createHandler() {
        this.thread = new HandlerThread("MyHandler");
        this.thread.start();
        this.handler = new MyHandler(this.thread.getLooper());

    }

    @Override
    public void authSuccess(String response) {
        Log.d("Main-TAG-success",response);
        sendMessage(AUTH_SUCCESS,response);
    }

    @Override
    public void authFailure(String msg) {
        Log.d("Main-TAG-failure",msg);
        sendMessage(AUTH_FAILURE,msg);
    }

    @Override
    public void authError(String msg) {
        Log.d("Main-TAG-error",msg+"");
        sendMessage(AUTH_ERROR,msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_have_sn:
//                if(BioAuthManager.getInstance().isAuthorized()){
//                    Toast.makeText(MainActivity.this,"已授权",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(mSnDialog == null){
                    mSnDialog = new MyDialog(this,this,"",R.style.custom_dialog);
                }
                if(!mSnDialog.isShowing()){
                    mSnDialog.show();
                }
                break;
            case R.id.btn_no_sn:
//                if(BioAuthManager.getInstance().isAuthorized()){
//                    Toast.makeText(MainActivity.this,"已授权",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(mNoSnDialog == null){
                    mNoSnDialog = new MyDialog(this,this,BioAuthManager.getInstance().getScreenCode(),R.style.custom_dialog);
                }
                if(!mNoSnDialog.isShowing()){
                    mNoSnDialog.show();
                }
                break;
        }
    }

    @Override
    public void onCancelClicked(Dialog dialog, Object object) {
        dialog.dismiss();
    }

    @Override
    public void onConfirmClicked(Dialog dialog, Object object) {

    }

    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AUTH_SUCCESS:
                    Toast.makeText(MainActivity.this,"授权成功",Toast.LENGTH_SHORT).show();
                    break;
                case AUTH_FAILURE:
                    Toast.makeText(MainActivity.this,"授权失败,"+msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case AUTH_ERROR:
                    Toast.makeText(MainActivity.this,"授权出错,"+msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void sendMessage(int what,String msg){
        Message message = new Message();
        message.what = what;
        message.obj = msg;
        handler.sendMessage(message);
    }

}