package gxybx.ui.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.umeng.analytics.MobclickAgent;

import base.BaseActivity;
import butterknife.BindView;
import cn.gxybx.R;
import gxybx.config.MyApp;
import gxybx.ui.fragment.HomeFragment;
import gxybx.ui.fragment.IndustryFragment;
import gxybx.ui.fragment.MeFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.rg_tab)
    public RadioGroup rgTab;


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public int getResLayout() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        //监听
        //设置初始选中的
        rgTab.check(R.id.rb_home);
        showFragment(0);
        rgTab.setOnCheckedChangeListener(this);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //获取tag
        if (group.findViewById(checkedId).getTag() == null) {
            return;
        }
        int tag = Integer.parseInt(group.findViewById(checkedId).getTag().toString());
        //显示fragment方法
        showFragment(tag);

    }


    //定义个常量为当前页
    private int index = -1;
    private Fragment[] fragments = new Fragment[3];

    public void showFragment(int tag) {
        if (index == tag) {
            return;
        }
        //获取事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (index != -1) {
            //隐藏fragment
            transaction.hide(fragments[index]);

        }
        if (fragments[tag] == null) {
            //创建fragment对象，注意是:fragments[tag]=创建方法
            // fragments[tag] = createFragment(tag);

            //使用反射创建
            createFragment2(tag);
            //添加fragment
            transaction.add(R.id.frameLayout, fragments[tag]);
        } else {
            transaction.show(fragments[tag]);
        }

        //提交事务
        transaction.commit();
        index = tag;


        switch (tag) {
            case 0:
                tv_base_title.setText("首页");

                break;
            case 1:
                tv_base_title.setText("动态资讯");

                break;
            case 2:
                tv_base_title.setText("更多");

                break;
        }

    }

    /**
     * ----------------------//反射, Fragment-----------------
     */

    public static final String[] fragmentName = {
            HomeFragment.class.getName(),
            IndustryFragment.class.getName(),
            MeFragment.class.getName()};

    //创建fragment
    public void createFragment2(int tag) {
        try {
            fragments[tag] = (Fragment) Class.forName(fragmentName[tag]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    //点击两次退出App
    // （注意要把父类方法去掉）
    private long current;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - current < 2000) {
            //遍历循环退出
            MyApp.getInstance().exit();
        } else {
            showToast("再按一次退出程序");
            current = System.currentTimeMillis();
        }

    }





}
