package cn.haier.bio.medical.bioauthlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class Utils {
    public static final String urlPath = "http://47.104.235.239:28089";
    public static final String action = "/api/appVersionInfo/installationPerm";


    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static synchronized String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getDeviceUUId(Context context) {
        String androidId = getAndroidID(context);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)androidId.hashCode() << 32));
        return deviceUuid.toString().toUpperCase();
    }

    @SuppressLint("HardwareIds")
    private static String getAndroidID(Context context) {
        String id = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        return id == null ? "" : id;
    }

    public static void post(String data, BioAuthWebCallBack callBack){
        WeakReference<BioAuthWebCallBack> listener = new WeakReference<>(callBack);
        URL httpUrl = null;
        try {
            httpUrl = new URL(urlPath+action);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(AES.encode(data));
            bw.flush();
            final InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            listener.get().success(sb.toString());
        } catch (ConnectException e){
            Log.d("TAG","connect timeout");
        }
        catch (Exception e) {
            listener.get().error(e.getMessage());
        }
    }




}
