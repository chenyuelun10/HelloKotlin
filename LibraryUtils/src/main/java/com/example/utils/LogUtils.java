package com.example.utils;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class LogUtils {
    private static String TAG = "caiqrLog##";

    private static boolean logCanPrint;


    public static void initLogCanPrint(boolean logCanPrint){
        LogUtils.logCanPrint = logCanPrint;
    }
    /**
     * 打印字符串日志 String
     *
     * @param tag 日志TAG
     * @param msg 日志内容
     */
    public static void d(String tag, String msg) {
        if (logCanPrint) {
            for (String str : processTooLong(msg)) {
                Log.d(tag, str);
            }

        }
    }



    /**
     * 打印字符串日志 String
     *
     * @param msg 日志内容
     */
    public static void d(String msg) {
        if (logCanPrint) {
            for (String str : processTooLong(msg)) {
                Log.d(TAG, str);
            }

        }
    }

    public static String[] processTooLong(String msg) {
        int index = 0;
        int maxLength = 2048;
        int countOfSub = msg.length() / maxLength;
        String[] result = new String[countOfSub + 1];

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                result[i] = msg.substring(index, index + maxLength);
                index += maxLength;
            }
        }

        result[countOfSub] = msg.substring(index, msg.length());

        return result;
    }

    /**
     * 打印整形日志 int
     *
     * @param tag 日志TAG
     * @param msg 日志内容
     */
    public static void d(String tag, int msg) {
        if (logCanPrint) {
            Log.d(tag, msg + "");
        }

    }

    /**
     * 打印浮点型日志 float
     *
     * @param tag 日志TAG
     * @param msg 日志内容
     */
    public static void d(String tag, float msg) {
        if (logCanPrint) {
            Log.d(tag, msg + "");
        }

    }

    /**
     * 打印字符串数组日志 String[]
     *
     * @param tag   日志TAG
     * @param array 日志内容
     */
    public static void d(String tag, String[] array) {
        if (logCanPrint) {
            Log.d(tag, Arrays.toString(array));
        }

    }

    /**
     * 打印整形数组日志 int[]
     *
     * @param tag   日志TAG
     * @param array 日志内容
     */
    public static void d(String tag, int[] array) {
        if (logCanPrint) {
            Log.d(tag, Arrays.toString(array));
        }

    }

    /**
     * 打印列表日志 List
     *
     * @param tag  日志TAG
     * @param list 日志内容
     */
    public static void d(String tag, List list) {
        if (logCanPrint) {
            Log.d(tag, list.toString());
        }

    }
}