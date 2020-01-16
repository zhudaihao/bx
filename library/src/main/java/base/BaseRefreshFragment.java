package base;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yolanda.nohttp.rest.Response;

import baseBean.ResponsePagesEntity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import gxy.library.R;
import view.BGANormalRefreshViewHolder;


/**
 * Created by Administrator on 2017/4/17/017.
 */


//带下拉刷新，上拉加载
public class BaseRefreshFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate,
        BaseQuickAdapter.OnItemClickListener {

    protected RecyclerView recyclerView;

    protected BGARefreshLayout bgaLayout;//刷新控件

    //加载数据错误提示

    protected LinearLayout llLayout;

    protected ImageView ivIcon;//提示图标

    protected TextView tvHint;//提示文本
/*    @BindView(R.id.tv_state)
    protected TextView tvState;//点击刷新按钮*/

    //title布局

    protected RelativeLayout rlTitle;//默认隐藏

    protected TextView tvTitle;//默认显示

    protected TextView tvPlayer;//默认隐藏

    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_refresh;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }


    protected boolean isBeginRefreshing = false;

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        bgaLayout = (BGARefreshLayout) view.findViewById(R.id.bga_layout);
        tvPlayer = (TextView) view.findViewById(R.id.tv_player);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvPlayer = (TextView) view.findViewById(R.id.tv_player);
        rlTitle = (RelativeLayout) view.findViewById(R.id.rl_title);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        tvHint = (TextView) view.findViewById(R.id.tv_hint);

        tvHint = (TextView)view. findViewById(R.id.tv_hint);

        llLayout = (LinearLayout)view. findViewById(R.id.ll_layout);

        tvPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.overridePendingTransition(R.anim.anim_left, 0);
            }
        });


       /* tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginRefreshing();
            }
        });*/
        recyclerView.setHasFixedSize(true);//item高度固定，可以优化界面
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        //设置刷新适配器
        setBaseRefreshAdapter();
        //自动加载
        if (isBeginRefreshing) {
            beginRefreshing();
        } else {
            setBaseLoadData();
        }
    }

    /**
     * ----------------下面三个方法需子类须实现--------------------------
     */

    //设置标题和刷新设置，适配器
    protected void setBaseRefreshAdapter() {
      /*  baseRefreshAdapter = new BaseRefreshAdapter(mList, BaseRefreshFragment.this, activity);
        //false动画重复
        *//*  baseRefreshAdapter.isFirstOnly(false);*//*
        //设置那种动画
        baseRefreshAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        recyclerView.setAdapter(baseRefreshAdapter);

        baseRefreshAdapter.setOnItemClickListener(this);*/
    }

    //设置网络请求
    protected void setBaseLoadData() {
    }

    //设置网络请求成功后的逻辑处理
    protected void setBaseSuccessfulDate(String requestWhat, Object data) {
    }


    /**
     * 初始化 下拉刷新控件------------------------------------------------------
     */

    protected BGANormalRefreshViewHolder viewHolder;
    protected boolean isLoading = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        //使用BGA下拉刷新fragment需在onCreateView初始化
        bgaLayout.setDelegate(this);
        viewHolder = new BGANormalRefreshViewHolder(activity, isLoading);//设置为true可以上拉加载
        bgaLayout.setRefreshViewHolder(viewHolder);
        return rootView;
    }


    protected int pageNum = 1;

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
        llLayout.setVisibility(View.GONE);

        //关闭刷新
        bgaLayout.endRefreshing();
        bgaLayout.endLoadingMore();

        //设置成功后数据
        setBaseSuccessfulDate(requestWhat, data);


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
                setBaseLoadData();

            }

        }, 1000);
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
                setBaseLoadData();

            }

        }, 1000);
        return true;
    }


    //点击item(注意子类需要动画，实现逻辑需要在super之前)
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        activity.overridePendingTransition(R.anim.anim_left, 0);

       /* Intent intent = new Intent(activity, MatchDetailsActivity.class);
        intent.putExtra("competitionId", baseRefreshAdapter.getData().get(position).getCompetitionId());
        startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_left, 0);*/
    }


}
