
package com.example.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

//android设备\应用信息工具类
public class PhoneParameterUtils {
    /**
     * 得到屏幕的宽度
     */
    public static int getScreenWidth() {
        WindowManager manager = (WindowManager) ActivityStack.GlobalContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        // 低于版本13的
        if (Build.VERSION.SDK_INT <
                Build.VERSION_CODES.HONEYCOMB_MR2) {
            return display.getWidth();
        } else {
            Point point = new Point();
            display.getSize(point);
            return point.x;
        }
    }

    /**
     * 得到屏幕的高度
     */
    public static int getScreenHeight() {
        WindowManager manager = (WindowManager) ActivityStack.GlobalContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        // 低于版本13的
        if (Build.VERSION.SDK_INT <
                Build.VERSION_CODES.HONEYCOMB_MR2) {
            return display.getHeight();
        } else {
            Point point = new Point();
            display.getSize(point);
            return point.y;
        }
    }


    // WLAN MAC 地址
    public static String getPhoneMacId() {
        @SuppressLint("WifiManagerLeak") WifiManager wifi = (WifiManager) ActivityStack.GlobalContext().getSystemService(Context.WIFI_SERVICE);
        @SuppressLint("MissingPermission") WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    //IMEI: 仅仅只对Android手机有效
    public static String getPhoneIMEI() {
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager)ActivityStack.GlobalContext().getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String szImei = TelephonyMgr.getDeviceId();
            return szImei;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取手机名称+型号
    public static String getPhoneName() {
        return android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
    }


    //获取手机唯一标识
    public static String getPhoneId() {
        return getMD5Str(getPhoneIMEI() + getPhoneMacId());
    }


    //获取本App版本号
    public static String getVersionName() {
        try {
            PackageInfo pi = ActivityStack.GlobalContext().getApplicationContext().getPackageManager().getPackageInfo(ActivityStack.GlobalContext().getApplicationContext().getPackageName(), 0);
            return pi.versionName + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    //是否黑屏
    public static boolean isScreenUnlocked() {
        KeyguardManager mKeyguardManager = (KeyguardManager) ActivityStack.GlobalContext().getSystemService(Context.KEYGUARD_SERVICE);
        return !mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    //程序是否可见
    public static boolean isVisibleApp(String packageName) {
        ActivityManager activityManager = (ActivityManager) ActivityStack.GlobalContext().getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                LogUtils.d("定时校验token", "彩球可见");
                return true;
            } else {
                LogUtils.d("定时校验token", "彩球不可见");
            }
        }
        return false;

    }


    //MD5加密
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (byte b : byteArray) {
            if (Integer.toHexString(0xFF & b).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & b));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & b));
        }
        return md5StrBuff.toString();
    }

    // 判断是否是链接WiFi
    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }


    //判断网络是否连接
    public static boolean isConnectInternet() {
        boolean netStatus = false;
        ConnectivityManager conManager = (ConnectivityManager) ActivityStack.GlobalContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            netStatus = networkInfo.isAvailable();
        }
        return netStatus;
    }

    //4.4设置titleLayout padding
    public static void setTitleLayout(LinearLayout titleLayout) {
        if (null != titleLayout) {
            setStatusStyle(titleLayout);
        }
    }


    //获取versionCode
    public static int getVersionCode() {
        try {
            PackageInfo pi = ActivityStack.GlobalContext().getPackageManager().getPackageInfo(ActivityStack.GlobalContext().getPackageName(), 0);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //判断小于4.4的手机 顶部不需要叫padding
    public static void setStatusStyle(View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        view.setPadding(0, getPhoneStatusBarHeight(ActivityStack.GlobalContext().getResources()), 0, 0);
    }

    /**
     * 得到手机状态栏高度
     *
     * @param res 资源
     * @return 返回高度
     */
    public static int getPhoneStatusBarHeight(Resources res) {
        int result = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
