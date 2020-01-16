package utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import encoder.BASE64Encoder;


/**
 * Created by AsionReachel on 2016/2/24.
 * E-mail:AsionReachel@qq.com
 */
public class Util {
    public static final int MEDIA_TYPE_IMAGE = 1; // 图片类型
    public static final int MEDIA_TYPE_VIDEO = 2; // 适配类型
    public static final File FILE_SDCARD = Environment.getExternalStorageDirectory();
    //倒计时的时间
    public static final int TIMERTASK = 180;

    public static final int UNREALNAME = 0;
    public static final int UNBINDBANK = 1;
    public static final int UNLOGIN = 2;
    public static final int BINDBANK = -1;


    public static final File FILE_LOCAL = new File(FILE_SDCARD, "egonglu");


    public static String getSignature(String basestring, String secret) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        StringBuffer sb = new StringBuffer();
        // LogUtils.logiYq("排序后的:》》" + basestring);
        sb.append(basestring);
        sb.append(secret);

        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(sb.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }

        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

    /**
     * --------------------------------------------------------------------------
     */

    public static String mapToString(HashMap<String, Object> map) {
        StringBuffer baseString = new StringBuffer();
        List<Map.Entry<String, Object>> infoIds = new ArrayList<>(map.entrySet());
//Collections.sort(infoIds, (o1, o2) -> (o1.getKey()).toString().compareTo(o2.getKey().toString()));
        Comparator<? super Map.Entry<String, Object>> text = new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> stringObjectEntry, Map.Entry<String, Object> t1) {
                int date = (stringObjectEntry.getKey()).toString().compareTo(t1.getKey().toString());
                return date;
            }
        };
        Collections.sort(infoIds, text);
        for (int i = 0; i < infoIds.size(); i++) {
            baseString.append(infoIds.get(i).toString());
        }

        return baseString.toString();
    }

    /**
     * 科学计数法转换成普通计数法
     */


    public static String getNumber(String number) {
        BigDecimal db = new BigDecimal(number);
        String ii = db.toPlainString();
        return ii;
    }

	/*
     * 讲元转成万元
	 */

    public static String passWan(double number) {
        double wan = number / 10000;
        return ToDoubleString(wan);

    }

    /**
     * Base64 加密
     */
    public static String setBase64(String password) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        byte[] pass = new byte[0];
        try {
            pass = password.getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return base64Encoder.encode(pass);
    }

    /**
     * 将doule转换成String保留2位小数
     */
    public static String ToDoubleString(double money) {

        if (money == (int) money) {
            return (int) money + "";
        }
        if (String.format("%.2f", money).equals(String.format("%.1f", money) + "0")) {
            return String.format("%.1f", money);
        }
        return String.format("%.2f", money);
    }

    public static synchronized Bitmap decodeSampledBitmapFromFile(
            String filename, int reqWidth, int reqHeight) {


        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);


        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

            final float totalPixels = width * height;

            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    // scroll中放listView
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取listview 的高度
     */

    public static int getListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        return totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    }

    /**
     * 将一个List中的数据加到另一个list中去
     *
     * @param <T>
     */

    public static <T> List<T> addDatas(List<T> baselist, List<T> arrayDatas) {
        for (int i = 0; i < baselist.size(); i++) {
            arrayDatas.add(baselist.get(i));
        }
        return arrayDatas;
    }

    public static boolean isEmpty(String s) {
        if (s == null || "".equals(s)) {
            return true;
        } else {
            return false;
        }
    }

    private static Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = sdf.parse(dateStr);
        return date;
    }

    public static String parseStringDate(String dateStr) {
        Date date;
        if (isEmpty(dateStr)) {
            return "";
        }
        try {
            date = parseDate(dateStr);
        } catch (ParseException e) {
            //e.printStackTrace();
            return "错误的时间格式";
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf2.format(date);
    }


    public static String printpath() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + "-"
                + mins);

        return sbBuffer.toString();
    }

    /**
     * 获取多少天前的日期
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String paseDate(long time) {
        if (time == 0) {
            return "";
        }
        Date date;
        date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取多少天前的日期
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String paseDate(long time, boolean isSeconds) {
        SimpleDateFormat format = null;
        Date date;
        if (isSeconds) {
            date = new Date(time);
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            date = new Date(time);
            format = new SimpleDateFormat("yyyy-MM-dd");
        }
        return format.format(date);
    }

    /**
     * 获取多少天前的日期
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String paseDate(long time, String formats) {
        SimpleDateFormat format = null;
        Date date;

        date = new Date(time);
        format = new SimpleDateFormat(formats);

        return format.format(date);
    }

    /**
     * 获取昨天，今天日期的格式化
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String paseDate(boolean isSeconds, long create) {
        String ret = "";

        try {
            Calendar now = Calendar.getInstance();
            SimpleDateFormat format = null;
            Date date;
            long ms = 1000 * (now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));//毫秒数
            long ms_now = now.getTimeInMillis();
            if (ms_now - create < ms) {
                date = new Date(create);
                format = new SimpleDateFormat("HH:mm");
                ret = format.format(date);
            } else if (ms_now - create < (ms + 24 * 3600 * 1000)) {
                ret = "昨天";
            } else if (ms_now - create < (ms + 24 * 3600 * 1000 * 2)) {
                ret = "前天";
            } else {
                date = new Date(create);
                format = new SimpleDateFormat("yyyy-MM-dd");
                ret = format.format(date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String printTimeBeforePresent(String dateStr) {
        long now = System.currentTimeMillis();
        Date date;
        try {
            date = parseDate(dateStr);
        } catch (ParseException e) {

            return "错误的时间格式";
        }
        long time = date.getTime();

        long interval = now - time;

        int day = (int) (interval / (24 * 60 * 60 * 1000));
        int hour = (int) (interval / (60 * 60 * 1000));
        int min = (int) (interval / (60 * 1000));
        if (day > 0) {
            return day + "天前";
        } else if (hour > 0) {
            return hour + "小时前";
        } else if (min > 0) {
            return min + "分钟前";
        } else {
            return "1分钟前";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());

    }

    //把时间戳转换带文字时间格式
    public static String getTimeText(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String date = sdf.format(new Date(time));
        return date;
    }


    //把时间戳转换时间格式
    public static String getTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sdf.format(new Date(time));
        return date;
    }


    public static String getDateString(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = sdf.parse(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(date);
    }

    public static String getBeforeSpace(String data) {
        return data.split(" ")[0];
    }


    public static String getDate() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + "-"
                + mins);

        return sbBuffer.toString();
    }

    public static String getVersionName(Context c) {
        String version = "";
        // 获取packagemanager的实例
        PackageManager packageManager = c.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(c.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //e.printStackTrace();
        }
        return version;
    }

    public static int getVersionCode(Context c) {
        int version = 0;
        // 获取packagemanager的实例
        PackageManager packageManager = c.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(c.getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            //e.printStackTrace();
        }
        return version;
    }

    public static boolean checkUpdate(Context context, int versionCode) {
        if (getVersionCode(context) < versionCode) {
            return true;
        }
        return false;
    }


    /**
     * 拼接字符串 输入"-" "0" "a" null "c" 返回 a-0-c
     *
     * @param spliteBy   分隔符
     * @param defaultStr 为空时的默认值
     * @param str
     * @return
     */
    public static String concatenateString(String spliteBy, String defaultStr,
                                           String... str) {
        StringBuilder sb = new StringBuilder();
        for (String s : str) {
            if (s == null) {
                s = defaultStr;
            }
            sb.append(s + spliteBy);
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    // ===================================================

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 获取屏幕高度
     *
     * @param activity
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        return display.getHeight();
    }

    public static void deleteToSP(Context ctx, String title) {
        @SuppressWarnings("deprecation")
        SharedPreferences.Editor se = ctx.getSharedPreferences(title,
                Context.MODE_WORLD_WRITEABLE).edit();
        se.clear().apply();
    }

    @SuppressWarnings("deprecation")
    public static void saveToSP(Context ctx, String title, String key,
                                String value) {
        SharedPreferences.Editor se = ctx.getSharedPreferences(title,
                Context.MODE_WORLD_WRITEABLE).edit();
        se.putString(key, value);
        se.apply();
    }

    public static String getFromSP(Context ctx, String title, String key) {
        @SuppressWarnings("deprecation")
        SharedPreferences se = ctx.getSharedPreferences(title,
                Context.MODE_WORLD_READABLE);
        if (se == null) {
            return null;
        }
        String value = se.getString(key, "");
        if (value.equals("")) {
            return null;
        } else {
            return value;
        }
    }

    public static boolean isInstalled(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    packagename, 0);

        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;

        }

        return packageInfo == null ? false : true;
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String loadLocalRawDate(Context context, int resId) {
        // 可以把读出来的内容赋值给字符
        String ss = new String();
        String s;
        try {
            InputStream aa = context.getResources().openRawResource(resId);
            InputStreamReader inputStreamReader = new InputStreamReader(aa);
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            while ((s = bufferedReader.readLine()) != null) {
                ss += s;
            }
        } catch (IOException e) {
            Toast.makeText(context, "解析本地数据出错", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        }
        return ss.trim();
    }


    public static String formatDuring(long mss) {
        long ms = mss - (new Date().getTime());
        int day = (int) (ms / 1000 / 60 / 60 / 24);
        int hour = (int) (ms / 1000 / 60 / 60 % 24);
        int minute = (int) (ms / 1000 / 60 % 60);
        int second = (int) (ms / 1000 % 60);
        return day + "天" + hour + "时" + minute + "分" + second + "秒";
    }


    public static String numberFormat(double data) {
        return ToDoubleString(data);
    }

    public static String parseRant(double data) {
        if (data == (int) data)
            return (int) data + "";
        return numberFormat(data);

    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    public static boolean isNetworkAvailable(Context context) {
        try {
            //获取连接管理对象
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                //获取活动的网络连接
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }


    public static String NUMBERS = "0123456789";
    public static String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 检查是否同时包含数字和字母
     *
     * @param str
     * @return
     */
    public static boolean checkHasBothNumberAndLetter(String str) {
        if (checkHasNumber(str) && checkHasLetter(str)) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否包含数字
     *
     * @param str
     * @return
     */
    public static boolean checkHasNumber(String str) {
        if (str == null)
            return false;
        for (int i = 0; i < str.length(); i++) {
            String ch = str.charAt(i) + "";
            if (NUMBERS.indexOf(ch) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否包含字母
     *
     * @param str
     * @return
     */
    public static boolean checkHasLetter(String str) {
        if (str == null)
            return false;
        for (int i = 0; i < str.length(); i++) {
            String ch = str.charAt(i) + "";
            if (LETTERS.indexOf(ch) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验密码是否合规则
     *
     * @param pwd
     * @return
     */
    public static boolean checkPasswordLengthAndHasBothNumberAndLetter(String pwd) {
        if (pwd == null)
            return false;
        if (pwd.length() != pwd.trim().length())
            return false;
        if (pwd.length() < 8 || pwd.length() > 20)
            return false;
        if (checkHasBothNumberAndLetter(pwd))
            return false;
        return true;
    }

    /**
     * 获取资源文件
     *
     * @param id stringId
     * @return
     */
    public static String getStringFromResources(Context context, int id) {
        return context.getString(id);
    }

    public static int getAppVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;
            return versionCode;
        } catch (Exception e) {
            //e.printStackTrace();
            return 1;
        }
    }

    public static String getIMEI(Context context) {
        return android.os.Build.MODEL;
    }

    /**
     * 图片缩放
     *
     * @param bitmap 缩放图片
     * @param w      缩放宽
     * @param h      缩放高
     * @return
     */
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    public static boolean checkTextView(TextView editText) {
        if (editText.getText().toString().trim().equals(""))
            return true;
        return false;
    }


    public static boolean checkUserName(String username) {
//        (username.charAt(0))
        return !username.matches("[a-z,A-z][a-z,A-Z,0-9]{3,19}");
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
//                    LogUtils.logiYq(appProcess.importance + ">>>>>>" + ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND);
                    return true;
                } else {
//                    LogUtils.logiYq(appProcess.importance + ">>>>>>" + ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND);
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    public static String getPasswordfromBytes(byte[] data) {
        StringBuffer str = new StringBuffer();
        if (data != null && data.length != 0) {
            for (int i = 0; i < data.length; i++) {
                str.append(data[i]);
                if (i < data.length - 1)
                    str.append(",");
            }
        }
        return str.toString();
    }


    public static void writetoSDCard(byte[] bs) {
        try {
            FileOutputStream out = new FileOutputStream(new File(getSDPath() + "/gxylogo.png")); // 重新命名的图片为test.png.想要获取的图片的路径就是该图片的路径
            try {
                out.write(bs);
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }

    /**
     * @param is 把字节数组写到SDCard中,然后再读取该图片
     */
    private static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

    public static String mapTostring(HashMap<String, String> map) {
        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            sb.append(entry.getValue());
            if (it.hasNext()) {
                sb.append(",");
            }
//            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        return sb.toString();
    }

    public static void sendImgFriend(InputStream abpath) {

        try {
            writetoSDCard(InputStreamToByte(abpath));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }

    /**
     * 将Bitmap以指定格式保存到指定路径
     *
     * @param bitmap
     * @param name
     * @param format
     */
    public static void saveBitmap(Bitmap bitmap, String name, Bitmap.CompressFormat format) {
        // 创建一个位于SD卡上的文件
        File file = new File(Util.getSDPath());
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {

            }
        }
        file = new File(Util.getSDPath(), name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        FileOutputStream out = null;
        try {
            // 打开指定文件输出流
            out = new FileOutputStream(file);
            // 将位图输出到指定文件
            bitmap.compress(format, 512,
                    out);
            out.flush();
            out.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * 获取图片路径。
     *
     * @return
     */
    public static String getSDPath() {
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) {
            return Environment.getExternalStorageDirectory().toString() + "/images";
        } else
            return "/data/data/cn.gxy/images";
    }

    public static Bitmap convertViewToBitmap(View view) {
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();
        return ((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap();
    }

    public static int dip2px(int dpValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static void setEditText(final EditText editText, final ImageView ivClear) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editText.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    ivClear.setVisibility(View.INVISIBLE);
                }
            }
        });


        //监听焦点
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && editText.getText().toString().trim().length() != 0) {
                    //获取焦点
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    //失去焦点
                    ivClear.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}
