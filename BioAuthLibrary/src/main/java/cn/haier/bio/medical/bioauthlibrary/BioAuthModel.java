package cn.haier.bio.medical.bioauthlibrary;

import org.json.JSONException;
import org.json.JSONObject;

public class BioAuthModel {
    private String deviceCode;
    private String screenCode;
    private String authCode;


    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getScreenCode() {
        return screenCode;
    }

    public void setScreenCode(String screenCode) {
        this.screenCode = screenCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public static String toJson(String deviceCode, String appName, String appEdition,
                              String packageName, String screenCode, String authCode,String appCode){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("deviceCode",deviceCode);
            jsonObject.put("appName",appName);
            jsonObject.put("appEdition",appEdition);
            jsonObject.put("packName",packageName);
            jsonObject.put("screenCode",screenCode);
            jsonObject.put("authCode",authCode);
            jsonObject.put("applicationCode",appCode);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static BioAuthModel parseData(String data){
        BioAuthModel model = new BioAuthModel();
        try {
//            JSONObject json = new JSONObject(data);
            JSONObject json1 = new JSONObject(AES.decode(data));
            model.setScreenCode(json1.getString("screenCode"));
            model.setAuthCode(json1.getString("authCode"));
            model.setDeviceCode(json1.getString("deviceCode"));
            return model;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
