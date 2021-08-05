package cn.haier.bio.medical.bioauthlibrary;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class BioAuthManager implements BioAuthWebCallBack {
    private volatile static BioAuthManager manager;
    private Context mContext;
    private static final String AUTH_CODE = "";
    private WeakReference<BioAuthListener> listener;
    private String appName;                 //应用名
    private String appEdition;              //版本号
    private String packageName;                //应用包名
    private String screenCode;              //屏幕的唯一识别码

    public static BioAuthManager getInstance() {
        if (manager == null) {
            synchronized (BioAuthManager.class) {
                if (manager == null) {
                    manager = new BioAuthManager();
                }
            }
        }
        return manager;
    }

    public void init(Context mContext, BioAuthListener listener) {
        this.mContext = mContext;
        this.listener = new WeakReference<>(listener);
        this.appName = Utils.getAppName(mContext);
        this.appEdition = Utils.getVersionName(mContext);
        this.packageName = Utils.getPackageName(mContext);
        this.screenCode = Utils.getDeviceUUId(mContext);
    }

    /**
     * 是否已经授权
     * @return
     */
    public boolean isAuthorized(){
        String data = PreferenceUtils.getString(mContext,AUTH_CODE,"");
        if(TextUtils.isEmpty(data)) return false;
        BioAuthModel model = BioAuthModel.parseData(data);
        if(model == null){
            return false;
        }else{
            return model.getScreenCode().equals(getScreenCode());
        }
    }

    /**
     *
     * @return 屏幕唯一标识码
     */
    public String getScreenCode(){
        return screenCode;
    }

    /**
     * @param screenCode 屏幕唯一标识吗
     */
    public void setScreenCode(String screenCode){
        this.screenCode = screenCode;
    }

    /**
     * 有机编设备认证
     *
     * @param deviceCode   设备机编
     * @param authCode     授权码
     * @param appCode      app唯一标识
    */
    public void checkAuthWithSN(String deviceCode, String authCode,String appCode) {
        if(TextUtils.isEmpty(authCode) || TextUtils.isEmpty(deviceCode) || TextUtils.isEmpty(appCode)) return;
        checkAuth(deviceCode,authCode,screenCode,appCode);
    }

    /**
     * 无机编设备认证
     *
     * @param authCode 授权码
     * @param appCode  app唯一标识码
     */
    public void checkAuthNoSN(String authCode,String appCode) {
        if(TextUtils.isEmpty(authCode) || TextUtils.isEmpty(appCode)) return;
        checkAuth(screenCode,authCode,screenCode,appCode);
    }

    /**
     * 无机编设备认证（自定义屏幕唯一标识码）
     * @param screenCode   屏幕唯一标识码
     * @param authCode     授权码
     * @param appCode      app唯一标识码
     */
    public void checkAuthNoSN(String screenCode,String authCode,String appCode) {
        if(TextUtils.isEmpty(authCode) || TextUtils.isEmpty(screenCode) || TextUtils.isEmpty(appCode)) return;
        this.screenCode = screenCode;
        checkAuth(screenCode,authCode,screenCode,appCode);
    }

    private void checkAuth(String deviceCode,String authCode,String screenCode,String appCode){
        final String json = BioAuthModel.toJson(deviceCode,appName,appEdition,packageName,screenCode,authCode,appCode);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Utils.post(json, BioAuthManager.this);
            }
        }).start();

    }

    @Override
    public void success(String response) {
        JSONObject json = null;
        try {
            json = new JSONObject(response);
            if(json.getInt("code") == 0){
                PreferenceUtils.putString(mContext,AUTH_CODE,json.getString("data"));
                listener.get().authSuccess(json.getString("data"));
            } else{
                listener.get().authFailure(json.getString("msg"));
            }
        } catch (JSONException e) {
            if(listener != null){
                listener.get().authError("数据解析失败");
            }
        }

    }

    @Override
    public void error(String msg) {
        if(listener != null){
            listener.get().authError(msg);
        }
    }
}
