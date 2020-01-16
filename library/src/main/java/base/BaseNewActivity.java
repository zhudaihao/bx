package base;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.zhy.autolayout.AutoLayoutActivity;

import net.HttpListener;
import net.NetClient;

import java.util.Timer;
import java.util.TimerTask;

import baseBean.ResponsePagesEntity;
import butterknife.ButterKnife;

import config.BaseApp;
import encoder.AESCrypt;

import gxy.library.R;
import top.wefor.circularanim.CircularAnim;
import utils.Util;

/**
 * Created by computer on 2018/3/30.
 */

//自定义个基类;用于给mainActivity继承
//BaseActivity抽象就不用注册
public abstract class BaseNewActivity extends AutoLayoutActivity implements HttpListener<String> {
    {
        //构造代码块(调用了文件夹config的MyApp类的添加activity方法)
        BaseApp.getInstance().addActivity(this);
    }

    protected AESCrypt aesCrypt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResLayout());

        try {
            aesCrypt = new AESCrypt();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

        //小刀注解
        ButterKnife.bind(this);
        initView();

    }


    //创建个抽象的方法获取布局
    public abstract int getResLayout();

    //获取组件方法
    protected abstract void initView();


    /**
     * 常用Toast
     */
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });


    }

    /**
     * 自定义Toast
     */
    public void showCustomToast(String num) {
        Toast mToast = new Toast(this);
        View inflate = View.inflate(this, R.layout.toast_layout, null);
        TextView tv_num = (TextView) inflate.findViewById(R.id.tv_num);
        tv_num.setText(num);
        mToast.setView(inflate);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    /**
     * 常用对象
     */
    //创建个构造方法得到上下文
    public Activity getActivity() {
        return this;
    }


    /**
     * 关闭队列
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (requestQueue != null) {
            requestQueue.cancelAll(); // 退出页面时时取消所有请求。
            requestQueue.stop(); // 退出时销毁队列，回收资源。
        }
        BaseApp.getInstance().removeActivity(this);


    }

    /**
     * 网络框架
     */
    protected NetClient netClient;
    protected RequestQueue requestQueue;

    public NetClient getNetClient() {
        requestQueue = NoHttp.newRequestQueue();
        if (netClient == null) {
            netClient = new NetClient(requestQueue, this, this);
        }
        return netClient;
    }

    //网络请求成功
    @Override
    public void onSuccessful(String requestWhat, Object data, ResponsePagesEntity page) {

    }

    //网络请求出错
    @Override
    public void onFailure(String requestWhat, Object data) {
        //  showToast(requestWhat);
    }

    //网络请求失败
    @Override
    public void onFailed(int what, Response<String> response) {
        //showToast("请求网路失败");
    }


//    /**
//     * 解决键盘适配问题，可以写在BaseActivity里面
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//
//            //如果不是落在EditText区域，则需要关闭输入法
//            if (isShouldHideKeyboard(v, ev)) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//
//    /**
//     * 控制键盘
//     */
//    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
//    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
//        if (v != null && (v instanceof EditText)) {
//            int[] l = {0, 0};
//            v.getLocationInWindow(l);
//            //获取现在拥有焦点的控件view的位置，即EditText
//            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
//
//            //判断我们手指点击的区域是否落在EditText上面，如果不是，则返回true，否则返回false
//            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
//        }
//        return false;
//    }


    // 隐藏软键盘
    protected void hintKbOne(EditText etFind) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etFind.getWindowToken(), 0);
    }


    /**
     * 获取网络状态
     */
    protected boolean getNetStart(Activity activity) {
        ConnectivityManager manager = (ConnectivityManager) activity
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }

    /**
     * 页面跳转动画效果
     */
    //动画类型
    private int animType = 1;

    protected int getAnimType() {
        return animType;
    }

    protected void setAnimType(int animType) {
        this.animType = animType;
    }

    /**
     * 动画
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (animType == 2) {
            if (item.getItemId() == android.R.id.home) {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 手机返回键
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (animType == 2) {
            // 添加返回过渡动画.
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            finish();
            overridePendingTransition(0, R.anim.anim_right);
        }
    }

    protected void setActivityAnimation(Activity activity, View view) {
        if (animType == 2) {
            CircularAnim.fullActivity(activity, view)
                    .colorOrImageRes(R.color.activity_animation)  //注释掉，因为该颜色已经在App.class 里配置为默认色
                    .go(new CircularAnim.OnAnimationEndListener() {
                        @Override
                        public void onAnimationEnd() {
                            finish();
                        }
                    });
        } else {
            finishToActivity();
        }
    }


    /**
     * 跳转页面动画
     */

    public void finishToActivity() {
        finish();
        overridePendingTransition(0, R.anim.anim_right);
    }

    //动画
    protected void setToAnim(final Activity startActivity, final Class<?> endActivity, final Button button, final ProgressBar progressBar) {
        CircularAnim.hide(button)
                .endRadius(progressBar.getHeight() / 2)
                .deployAnimator(new CircularAnim.OnAnimatorDeployListener() {
                    @Override
                    public void deployAnimator(Animator animator) {
                        animator.setDuration(1200L);
                        animator.setInterpolator(new AccelerateInterpolator());
                    }
                })
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CircularAnim.fullActivity(getActivity(), progressBar)
                                        .go(new CircularAnim.OnAnimationEndListener() {
                                            @Override
                                            public void onAnimationEnd() {
                                                //动画完成
                                                startActivity(new Intent(getActivity(), endActivity));
                                                button.setVisibility(View.VISIBLE);
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        });
                            }
                        }, 500);
                    }
                });
    }


    public void startToActivity() {
        overridePendingTransition(R.anim.anim_left, 0);
        finish();
    }

    public void startToActivity(Activity activity, Class<?> toClass, boolean isFinish) {
        Intent intent = new Intent(activity, toClass);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_left, 0);
        //是否关闭页面
        if (isFinish) {
            finish();
        }

    }

    /**
     * 优化glide内存
     */

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //内存低时清理Glide缓存
        Glide.get(this).clearMemory();
    }


    /**
     * //短信倒计时
     */
    protected Timer timer;
    protected TimerTask task;
    protected int relent = Util.TIMERTASK;

    protected void getRunTimer(final Activity context, final TextView textView) {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        relent--;
                        textView.setText(relent + "s" + "后重新发送");

                        //倒计时结束
                        if (relent <= 0) {
                            timer.cancel();
                            // timeTest.setVisibility(View.GONE);
                            textView.setEnabled(true);
                            textView.setText("重新发送");
                            task.cancel();
                            timer = null;
                            task = null;
                            relent = Util.TIMERTASK;

                        }
                    }
                });

            }
        };
        timer.schedule(task, 1000, 1000);
    }
}
