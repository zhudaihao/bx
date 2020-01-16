package gxybx.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yolanda.nohttp.rest.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import net.RequestWhat;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;
import baseBean.ResponsePagesEntity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.gxybx.R;
import gxybx.adapter.HomeAdapter;
import gxybx.bean.HomeBean;
import gxybx.bean.ProductBean;
import gxybx.ui.activity.NewsActivity;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import utils.Util;


/**
 * 首页
 */

public class HomeFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.bga_layout)
    BGARefreshLayout bgaLayout;

    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.tv_state)
    TextView tvLoad;


    Unbinder unbinder;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    private HomeAdapter homeAdapter;
    private List<ProductBean> mList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        loadData();
    }

//    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在 activity 的 onStart 等方法中调用，
//    // 自动进入正在刷新状态获取最新数据（下拉刷新）
//    public void beginRefreshing() {
//        bgaLayout.beginRefreshing();
//    }
//
//    // 通过代码方式控制进入加载更多状态(上拉加载)
//    public void beginLoadingMore() {
//        bgaLayout.beginLoadingMore();
//    }

    private View headView;

    private void initView() {
        recyclerView.setHasFixedSize(true);//item高度固定，可以优化界面
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        //数据
        ProductBean productBean1 = new ProductBean(R.mipmap.product_one, "财产保险", "房屋、装修、家具、首饰等全方位安全保障");
        ProductBean productBean2 = new ProductBean(R.mipmap.renshou_img, "人寿保险", "人寿诚信至上，关爱天下民众，一份关爱伴随您走过人生的风风雨雨");
        ProductBean productBean3 = new ProductBean(R.mipmap.jiankan_img, "健康保险", "种类丰富的轻疾、重疾险、意外医疗、疫苗等全面保障");

        mList.add(productBean1);
        mList.add(productBean2);
        mList.add(productBean3);

        homeAdapter = new HomeAdapter(getContext(), mList);

        //false动画重复
        /*  homeAdapter.isFirstOnly(false);*/
        //设置那种动画
        homeAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        recyclerView.setAdapter(homeAdapter);

        //banner图和选手布局
        headView = View.inflate(activity, R.layout.fragment_home_head, null);
        homeAdapter.addHeaderView(headView);

        initHeadView(headView);

        //点击按钮刷新
        tvLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }


    //添加头部控件
    private Banner mBanner;

    //获取控件
    private void initHeadView(View headView) {
        mBanner = (Banner) headView.findViewById(R.id.bannerViewPager_home);
    }


    private void loadData() {
        getNetClient().home();
    }


    private HomeBean homeBean;

    @Override
    public void onSuccessful(String requestWhat, Object data, ResponsePagesEntity page) {
        super.onSuccessful(requestWhat, data, page);
        // LogUtils.e("---数据-------" + data.toString());

//        Log.e("zdh", "------------------" + data.toString());

        switch (requestWhat) {
            //banner图和选手
            case RequestWhat.HOME:
                //关闭刷新
                bgaLayout.endRefreshing();
                bgaLayout.endLoadingMore();

                homeBean = JSONObject.parseObject(data.toString(), HomeBean.class);
                if (isAdd) {
                    //上拉加载
                    showToast("没有更多数据");
                } else {
                    //下拉刷新
                    setBannerData(homeBean);
                }

                break;
        }
    }


    //轮播加载图片
    private List<HomeBean.ImageListBean> mHomeImageList;//轮播图的集合
    private ArrayList<String> mTitle;
    private ArrayList<String> mUrl;

    //初始化 下拉刷新控件
    protected BGANormalRefreshViewHolder viewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        //使用BGA下拉刷新fragment需在onCreateView初始化
        bgaLayout.setDelegate(this);
        viewHolder = new BGANormalRefreshViewHolder(activity, true);//设置为true可以上拉加载
        bgaLayout.setRefreshViewHolder(viewHolder);
        return rootView;
    }


    //轮播加载图片框架
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context)
                    .load(path)
                    .error(R.mipmap.no_image)
                    .placeholder(R.drawable.image)
                    .bitmapTransform(new RoundedCornersTransformation(context, 20, 0, RoundedCornersTransformation.CornerType.ALL))
                    .into(imageView);

        }

    }

    //轮播
    private void setBannerData(HomeBean entity) {
        mHomeImageList = entity.getImageList();
        mUrl = new ArrayList<>();
        for (int i = 0; i < mHomeImageList.size(); i++) {
            mUrl.add(mHomeImageList.get(i).getFileUrl());
        }


        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setImageLoader(new GlideImageLoader());
        //  mBanner.update(mUrl,mTitle);
        mBanner.setDelayTime(2000);
        mBanner.setImages(mUrl);
        //indicator_drawable_selected


        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);

        //轮播图监听
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String linkUrl = mHomeImageList.get(position).getLinkUrl();
                if (!Util.isEmpty(linkUrl)) {
                    Intent intent = new Intent(getContext(), NewsActivity.class);
                    intent.putExtra("title", "");
                    intent.putExtra("tag", 1);
                    intent.putExtra("url", linkUrl);
                    startToActivity(intent);
                }

            }
        });
        mBanner.start();


    }

    @Override
    public void onStop() {
        super.onStop();
        //轮播图停止轮播
        mBanner.stopAutoPlay();

    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        super.onFailed(what, response);
        //关闭刷新
        bgaLayout.endRefreshing();
        bgaLayout.endLoadingMore();
//        llLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onFailure(String requestWhat, Object data) {
        super.onFailure(requestWhat, data);
        //关闭刷新
        bgaLayout.endRefreshing();
        bgaLayout.endLoadingMore();
//        llLayout.setVisibility(View.VISIBLE);

    }

    public static Handler handler = new Handler();
    //上拉加载标记
    protected boolean isAdd = false;

    //下拉刷新
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //使用handler设置最少加载时间
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //重新请求网络数据
                isAdd = false;
                loadData();

            }

        }, 500);

    }

    //上拉加载
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //使用handler设置最少加载时间
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //重新请求网络数据
                isAdd = true;
                loadData();

            }

        }, 500);

        return true;
    }


}
