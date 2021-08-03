package cn.haier.bio.medical.bioauth;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils
{
	private static final String SP_NAME	= "BioAuth";

	private static final String SP_DEVICE_CODE	= "DEVICE_CACHE";//要求恢复出厂设置后，不被清空

	private static SharedPreferences sp;

	private static SharedPreferences codeSp;
	private static Editor mEditor;
	private static Editor mCacheEditor;
	public static SharedPreferences getPreferences(Context context) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
		return sp;
	}

	public static SharedPreferences getCacheSharedPreference(Context context) {
		if (codeSp == null)
			codeSp = context.getSharedPreferences(SP_DEVICE_CODE, Context.MODE_MULTI_PROCESS);
		return codeSp;
	}

	public static Editor getEditor(Context context){
		SharedPreferences sp = getPreferences(context);
		if(mEditor==null){
			mEditor = sp.edit();
		}
		return mEditor;
	}
	public static Editor getCacheEditor(Context context){
		SharedPreferences sp = getCacheSharedPreference(context);
		if(mCacheEditor ==null){
			mCacheEditor = sp.edit();
		}
		return mCacheEditor;
	}


	/**
	 * 恢复出厂设置无需清零
	 *
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putCacheString(Context context, String key, String value) {
		Editor editor = getCacheEditor(context);
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 取字符串值
	 *
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getCacheString(Context context, String key) {
		SharedPreferences sp = getCacheSharedPreference(context);
		return sp.getString(key, "");
	}

	/**
	 * 取字符串值，键值对
	 *
	 * @param context
	 * @param key
	 * @param defValue
	 *
	 * @return
	 */
	public static String getCacheString(Context context, String key, String defValue)
	{
		SharedPreferences sp = getCacheSharedPreference(context);
		return sp.getString(key, defValue);
	}
	public static int getCacheInt(Context context, String key, int defValue)
	{
		SharedPreferences sp = getCacheSharedPreference(context);
		return sp.getInt(key, defValue);
	}
	public static void putCacheInteger(Context context, String key, Integer value) {
		Editor editor = getCacheEditor(context);
		editor.putInt(key, value);
		editor.commit();
	}
	public static void putCacheBoolean(Context context, String key, boolean value) {
		Editor editor = getCacheEditor(context);
		editor.putBoolean(key, value);
		editor.commit();
	}
	public static void putCacheLong(Context context, String key, long value){
		Editor editor = getCacheEditor(context);
		editor.putLong(key, value);
		editor.commit();
	}
	public static long getCacheLong(Context context, String key){
		SharedPreferences sp = getCacheSharedPreference(context);
		return sp.getLong(key, 0);
	}
	public static boolean getCacheBoolean(Context context, String key){
		SharedPreferences sp = getCacheSharedPreference(context);
		return sp.getBoolean(key, false);
	}
	public static boolean getCacheBoolean(Context context, String key, boolean defValue){
		SharedPreferences sp = getCacheSharedPreference(context);
		return sp.getBoolean(key, defValue);
	}

	public static void putCacheFloat(Context context, String key, float value){
		Editor editor = getCacheEditor(context);
		editor.putFloat(key, value);
		editor.commit();
	}

	public static float getCacheFloat(Context context, String key){
		SharedPreferences sp = getCacheSharedPreference(context);
		return sp.getFloat(key, 0);
	}

	/**
     * 取布尔值
     * 
     * @param context
     * @param key
     * @return
     */
	public static boolean getBoolean(Context context, String key) {
		SharedPreferences sp = getPreferences(context);
		return sp.getBoolean(key, false);
	}

	public static boolean getBoolean(Context context, String key, Boolean defaultValue) {
		SharedPreferences sp = getPreferences(context);
		return sp.getBoolean(key, defaultValue);
	}

	public static boolean getBoolean(Context context, String key, boolean defValue) {
		SharedPreferences sp = getPreferences(context);
		return sp.getBoolean(key, defValue);
	}

	public static int getInt(Context context, String key, int defValue) {
		SharedPreferences sp = getPreferences(context);
		return sp.getInt(key, defValue);
	}

	public static void putFloat(Context context, String key, float value) {
		Editor editor = getEditor(context);
		editor.putFloat(key, value);
		editor.commit();
	}

	public static int getInt(Context context, String key) {
		SharedPreferences sp = getPreferences(context);
		return sp.getInt(key, 0);
	}

	public static float getFloat(Context context, String key) {
		SharedPreferences sp = getPreferences(context);
		return sp.getFloat(key, 0);
	}


	/**
     * 设置布尔值
     * 
     * @param context
     * @param key
     * @param value
     */
	public static void putBoolean(Context context, String key, boolean value) {
		Editor editor = getEditor(context);
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void deleteKey(Context context, String key) {
		SharedPreferences sp = getPreferences(context);
		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}


	/**
	 * 设置整型值
	 *
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putInteger(Context context, String key, Integer value) {
		Editor editor = getEditor(context);
		editor.putInt(key, value);
		editor.commit();
	}

	/**
     * 取字符串值
     * 
     * @param context
     * @param key
     * @return
     */
	public static String getString(Context context, String key) {
		SharedPreferences sp = getPreferences(context);
		return sp.getString(key, "");
	}

	/**
     * 取字符串值，键值对
     * 
     * @param context
     * @param key
     * @param defValue
     *           
     * @return
     */
	public static String getString(Context context, String key, String defValue) {
		SharedPreferences sp = getPreferences(context);
		return sp.getString(key, defValue);
	}

	/**
     * 键值对获取
     * 
     * @param context
     * @param key
     * @param value
     */
	public static void putString(Context context, String key, String value) {
		Editor editor = getEditor(context);
		editor.putString(key, value);
		editor.commit();
	}

	public static void putLong(Context context, String key, long value){
		Editor editor = getEditor(context);
		editor.putLong(key, value);
		editor.commit();
	}

	public static long getLong(Context context, String key){
		SharedPreferences sp = getPreferences(context);
		return sp.getLong(key, 0);
	}

	public static void clearAllData(Context context){
		sp = getPreferences(context);
		sp.edit().clear().commit();
	}
}
