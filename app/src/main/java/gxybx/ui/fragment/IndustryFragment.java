package gxybx.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;
import baseBean.ResponsePagesEntity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.gxybx.R;
import gxybx.adapter.IndustryAdapter;
import gxybx.bean.IndustryBean;
import gxybx.ui.activity.NewsActivity;
import view.BGANormalRefreshViewHolder;


/**
 * 行业资讯
 */

public class IndustryFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.bga_layout)
    BGARefreshLayout bgaLayout;

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.tv_state)
    TextView tvLoad;

    @BindView(R.id.ll_layout)
    LinearLayout llLayout;

    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_player_news;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        beginRefreshing();
    }

    private List<IndustryBean.ArticlesBean> mList = new ArrayList<>();
    private IndustryAdapter highlightAdapter;

    private void initView() {
        ivIcon.setVisibility(View.GONE);
        tvHint.setText("暂无资讯");


        recyclerView.setHasFixedSize(true);//item高度固定，可以优化界面
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        highlightAdapter = new IndustryAdapter(mList, getContext());

        //false动画重复,默认true
        // highlightAdapter.isFirstOnly(false);
        //设置那种动画
        highlightAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        recyclerView.setAdapter(highlightAdapter);

        highlightAdapter.setOnItemClickListener(this);


        //点击按钮刷新
        tvLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginRefreshing();
            }
        });

    }

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

    protected int pageNum = 1;

    /**
     * 请求网络
     */
    private void loadData() {
        getNetClient().getIndustry(pageNum);
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在 activity 的 onStart 等方法中调用，
    // 自动进入正在刷新状态获取最新数据（下拉刷新）
    public void beginRefreshing() {
        bgaLayout.beginRefreshing();
    }

    // 通过代码方式控制进入加载更多状态(上拉加载)
    public void beginLoadingMore() {
        bgaLayout.beginLoadingMore();
    }

    @Override
    public void onSuccessful(String requestWhat, Object data, ResponsePagesEntity page) {
        super.onSuccessful(requestWhat, data, page);
        //关闭刷新
        bgaLayout.endRefreshing();
        bgaLayout.endLoadingMore();
        List<IndustryBean.ArticlesBean> highlightBeanList = JSON.parseObject(data.toString(), IndustryBean.class).getArticles();

        mList.addAll(highlightBeanList);

        if (mList.size() == 0) {
            llLayout.setVisibility(View.VISIBLE);
        } else {
            llLayout.setVisibility(View.GONE);

            if (isAdd) {
                //上拉加载
                highlightAdapter.setList(mList);
                if (highlightBeanList.size() == 0) {
                    showToast("没有更多数据");
                }
            } else {
                //下拉刷新
                mList.clear();
                mList.addAll(highlightBeanList);
                //下拉刷新调用适配器的setNewData方法
                highlightAdapter.setNewData(mList);
            }


        }


    }

    public void onFailed(int what, Response<String> response) {
        super.onFailed(what, response);
        //关闭刷新
        bgaLayout.endRefreshing();
        bgaLayout.endLoadingMore();
        llLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onFailure(String requestWhat, Object data) {
        super.onFailure(requestWhat, data);
        //关闭刷新
        bgaLayout.endRefreshing();
        bgaLayout.endLoadingMore();
        llLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
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
                pageNum = 1;
                isAdd = false;
                loadData();

            }

        }, 300);
    }


    //上拉加载
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //使用handler设置最少加载时间
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //重新请求网络数据
                pageNum = pageNum + 1;
                isAdd = true;
                loadData();

            }

        }, 300);
        return true;
    }


    //点击item
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(activity, NewsActivity.class);
        String url = mList.get(position). getUrl();
        if (!TextUtils.isEmpty(url)){
            intent.putExtra("url", url);
            intent.putExtra("tag",1);
            intent.putExtra("title", "动态资讯");
            activity.overridePendingTransition(R.anim.anim_left, 0);
            startToActivity(intent);
        }

    }


}
