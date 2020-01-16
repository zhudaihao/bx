package gxybx.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yolanda.nohttp.rest.Response;

import net.RequestWhat;

import java.util.List;

import base.BaseActivity;
import base.ViewPagerAdapter;
import baseBean.ResponsePagesEntity;
import cn.gxybx.R;
import gxybx.bean.UpDateBean;
import gxybx.config.MyApp;
import gxybx.utils.UpdateManager;
import gxybx.utils.VersionUtil;
import utils.LogUtils;
import utils.SPUtils;
import utils.Util;


public class StarterActivity extends BaseActivity {
    private ViewPager viewPager;
    private LinearLayout llProgress;
    private RelativeLayout rl_layout;
    private TextView bt_start;
    private TextView tv_skip;
    private int imageMax = 3;//圆点数量

    @Override
    public int getResLayout() {
        return R.layout.activity_start;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notSetStatusBarColor();
    }

    protected void initView() {
        //隐藏标题
        rl_base_title.setVisibility(View.GONE);

        //获取布局
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        llProgress = (LinearLayout) findViewById(R.id.ll_progress);
        rl_layout = (RelativeLayout) findViewById(R.id.rl_layout);
        bt_start = (TextView) findViewById(R.id.bt_start);
        tv_skip = findViewById(R.id.tv_skip);
        //点击体验按钮监听
        setListener();
        //设置启动页还是引导页
        boolean one = (boolean) SPUtils.get(StarterActivity.this, "one", true);
        if (one) {
//            MyApp.getInstance().setService("https://tgxybx-app.gxyclub.com");
            viewPager.setVisibility(View.VISIBLE);
            setWelcomeDialog();
        } else {
            viewPager.setVisibility(View.GONE);

        }
        //点击跳过
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFinish();
            }
        });


        //设置动画
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        anim.setFillAfter(true);
        //动画监听
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 两个参数分别表示进入的动画,退出的动画
                if (!one) {
                    //请求版本更新接口
                   loadUpDate();
                    //测试
//                    showDialogUrl();
                }
            }
        });
        rl_layout.setAnimation(anim);
    }


    /**
     * 设置网络环境对话框
     */
    private Dialog dialogUrl;

    private void showDialogUrl() {
        dialogUrl = new Dialog(this, R.style.CursorDialogNotFloatTheme);
        dialogUrl.setContentView(R.layout.dialog_url);
        dialogUrl.setCanceledOnTouchOutside(false);//设置点击dialog外部不关闭
        Button bt1 = (Button) dialogUrl.findViewById(R.id.bt1);
        Button bt2 = (Button) dialogUrl.findViewById(R.id.bt2);
        Button bt3 = (Button) dialogUrl.findViewById(R.id.bt3);
        Button bt4 = (Button) dialogUrl.findViewById(R.id.bt4);
        Button bt5 = (Button) dialogUrl.findViewById(R.id.bt5);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApp.getInstance().setService("https://tgxybx-app.gxyclub.com");
                dialogUrl.dismiss();
                loadUpDate();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApp.getInstance().setService("https://ygxybx-app.gxyclub.com");
                dialogUrl.dismiss();
                loadUpDate();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApp.getInstance().setService("https://gxybx-app.gxyclub.com");
                dialogUrl.dismiss();
                loadUpDate();

            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApp.getInstance().setService("http://192.168.2.117:8087");
                dialogUrl.dismiss();
                loadUpDate();
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApp.getInstance().setService("http://192.168.2.54:8080/gxy-ff-app");
                dialogUrl.dismiss();
                loadUpDate();

            }
        });

        dialogUrl.show();

    }


    /**
     * 点击监听
     */
    private void setListener() {
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.put(StarterActivity.this, "one", false);
                setFinish();
            }
        });
    }

    /**
     * 跳转页面
     */
    private void setFinish() {
        startToActivity(this, MainActivity.class, true);
    }


    /**
     * 请求升级接口
     */
    private void loadUpDate() {
        getNetClient().checkUpdate();
    }

    /**
     * 请求接口回调
     */
    private UpDateBean upDateBean;
    private String url;

    //请求成功
    @Override
    public void onSuccessful(String requestWhat, Object data, ResponsePagesEntity page) {
        super.onSuccessful(requestWhat, data, page);
        switch (requestWhat) {
            case RequestWhat.UPDATE:
                upDateBean = JSONObject.parseObject(data.toString(), UpDateBean.class);
                if (upDateBean != null) {
                    String vcode = upDateBean.getMobileVersion().getVname();
                    url = upDateBean.getMobileVersion().getUrl();
                    setUpData(vcode, url);
                } else {
                    setFinish();
                }

                break;
        }
    }


    //请求失败
    @Override
    public void onFailed(int what, Response response) {
        super.onFailed(what, response);
        setFinish();
    }

    //请求错误
    @Override
    public void onFailure(String requestWhat, Object data) {
        super.onFailure(requestWhat, data);
        setFinish();
    }

    /**
     * 版本更新
     */
    //强制更新
    private boolean coerce = false;
    private UpdateManager um;

    private void setUpData(String vcode, String url) {
        String oldVersion = Util.getVersionName(this);
        if (VersionUtil.compareVersion(vcode, oldVersion) > 0) {
            //有新版本
            //获取存储权限
            if (AndPermission.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //isUpdate	number	是否必须更新（0:否；1:是）
                int isUpdate = upDateBean.getMobileVersion().getIsUpdate();

                LogUtils.e("--------------更新");
                if (isUpdate == 1) {
                    coerce = true;
                } else {
                    coerce = false;
                }

                um = new UpdateManager(this, true, coerce);

                um.showNoticeDialog(url);


            } else {
                //申请权限
                AndPermission.with(this)
                        .requestCode(100)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .rationale((requestCode, rationale) ->
                                // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(this, rationale).show()
                        )
                        .send();

            }

        } else {
            //没有新版本
            startToActivity(this, MainActivity.class, true);
        }


    }

    /**
     * 申请权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，第一个参数是当前Acitivity/Fragment，回调方法写在当前Activity/Framgent。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    // 成功回调的方法，用注解即可，里面的数字是请求时的requestCode。
    @PermissionYes(100)
    private void getLocationYes(List<String> grantedPermissions) {
        // TODO 申请权限成功。
        //getNetClient().download(updateInfoEntity.getUrl(), downloadListener);

        //isUpdate	number	是否必须更新（0:否；1:是）
        int isUpdate = upDateBean.getMobileVersion().getIsUpdate();
        if (isUpdate == 1) {
            coerce = true;
        } else {
            coerce = false;
        }
        um = new UpdateManager(this, true, coerce);

        um.showNoticeDialog(url);

    }


    // 失败回调的方法，用注解即可，里面的数字是请求时的requestCode。
    @PermissionNo(100)
    private void getLocationNo(List<String> deniedPermissions) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种:用默认的提示语。
            AndPermission.defaultSettingDialog(this, 100).show();
        }
    }


    /**
     * 设置引导页
     */
    //定义个变量实现没有被选中状态(注意赋值必须为正数)
    private int index = 0;

    private void setWelcomeDialog() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(StarterActivity.this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        //画圆形进度
        drawableOver(llProgress, 0);
        //监听 设置圆形进度
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //监听viewpager切换时；进行逻辑处理
                //设置没有被选中的点
                llProgress.getChildAt(index % imageMax).setSelected(false);
                //设置选中的点
                llProgress.getChildAt(position % imageMax).setSelected(true);


                //显示体验按钮
                if (position == (imageMax - 1)) {
                    bt_start.setVisibility(View.VISIBLE);
                } else {
                    bt_start.setVisibility(View.GONE);
                }

                //把当前选中的点赋值给没有选中的变量；当下个被选中当前就要隐藏
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //在布局画圆
    private void drawableOver(LinearLayout linearLayout, int tag) {
        for (int i = 0; i < imageMax; i++) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.welcome_over_shape);
            //设置哪个默认选择
            if (tag == i) {
                view.setSelected(true);
            } else {
                view.setSelected(false);
            }
            //设置圆大小(注意装圆的是什么布局就用什么布局设置宽高)
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            //设置圆之间的距离
            params.setMargins(10, 10, 10, 10);
            //把大小和间距设置到组件
            view.setLayoutParams(params);
            //把组件放到布局里面
            linearLayout.addView(view);
        }
    }


}
