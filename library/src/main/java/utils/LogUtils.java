package utils;

import android.util.Log;

import com.orhanobut.logger.Logger;

public class LogUtils {
//    public static boolean isShow = true;//开发模式

    public static boolean isShow = false;//线上模式

    public static void e(String msg) {
        if (isShow) {
            Log.e("TAG", "--------------------" + msg);
        }
    }


    public static void i(String msg) {
        if (isShow) {
            Log.i("------------", msg);
        }
    }


    public static void w(String msg, Throwable e) {
        if (isShow) {
            Log.w("------------", msg, e);
        }
    }

    public static void g(String msg) {
        if (isShow) {
            Log.w("------------", msg);
        }
    }

    public static void json(String msg){

        if (isShow){
            Logger.json(msg);
        }
    }
}
