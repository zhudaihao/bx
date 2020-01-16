package gxybx.config;


import com.umeng.commonsdk.UMConfigure;

import config.BaseApp;


/**
 * 初始化第三方SDK和数据共享（不要忘记在mainfest注册）
 */
public class MyApp extends BaseApp {
    //使用单利模式
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        //友盟
        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE, "669c30a9584623e70e8cd01b0381dcb4");
        //测试
        UMConfigure.setLogEnabled(false);
    }



    /**
     * 刚启动App
     */
    private boolean isStart = true;

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }



}
