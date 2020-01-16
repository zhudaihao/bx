package config;

import android.app.Activity;
import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.yolanda.nohttp.NoHttp;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.HashSet;
import java.util.Set;

import gxy.library.R;
import top.wefor.circularanim.CircularAnim;

/**
 * Created by Administrator on 2018\5\11 0011.
 */

public class BaseApp extends Application {

    //使用单利模式
    private static BaseApp instance;

    public static BaseApp getInstance() {
        return instance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //按钮界面跳转过度动画
        CircularAnim.init(700, 500, R.color.activity_animation);

        //屏幕适配 3(最后就是BaseActivity继承AutoLayoutActivity或者XML使用auto的布局)
        AutoLayoutConifg.getInstance().useDeviceSize();

        //初始化网络框架NoHttp
        NoHttp.initialize(this, new NoHttp.Config()
                .setConnectTimeout(10 * 1000)
                .setReadTimeout(10 * 1000));

        //内存泄露检查
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        //打印
        Logger.addLogAdapter(new AndroidLogAdapter());

    }



    /**
     * 循环变历退出(实现：APP在任何个界面都可以直接退出程序而不是返回上界面)
     * 用HashSet集合去重复
     */

    private Set<Activity> activityList = new HashSet<>();

    //添加activity
    public void addActivity(Activity a) {
        activityList.add(a);
    }

    public void removeActivity(Activity a) {
        activityList.remove(a);
    }

    //退出程序
    public void exit() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
    }

    /**
     * 环境测试配置
     */
    private String service="https://tgxybx-app.gxyclub.com";

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
