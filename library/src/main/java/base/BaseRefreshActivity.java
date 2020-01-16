package base;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yolanda.nohttp.rest.Response;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import baseBean.ResponsePagesEntity;
import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import gxy.library.R;
import view.BGANormalRefreshViewHolder;


/**
 * 封装常用刷新activity
 */

public class BaseRefreshActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    //刷新控件
    protected RecyclerView recyclerView;
    protected BGARefreshLayout bgaLayout;

    //title部分布局
    protected ImageView ivBack;
    protected TextView tvTitle;
    protected TextView tvPlayer;
    protected RelativeLayout rlTitle;

    //错误提示部分布局
    protected ImageView ivIcon;
    protected TextView tvHint;
    protected TextView tvState;
    protected LinearLayout llLayout;


    @Override
    public int getResLayout() {
        return R.layout.activity_base_refresh;
    }


    /**
     * ---------------------------点击返回键监听-------------------------------
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        bgaLayout = (BGARefreshLayout) findViewById(R.id.bga_layout);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvPlayer = (TextView) findViewById(R.id.tv_player);
        rlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvHint = (TextView) findViewById(R.id.tv_hint);

        tvHint = (TextView) findViewById(R.id.tv_hint);
        tvState = (TextView) findViewById(R.id.tv_state);
        llLayout = (LinearLayout) findViewById(R.id.ll_layout);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivityAnimation(getActivity(), view);
            }
        });

        setView();
    }


    protected boolean isBeginRefreshing = false;

    @Override
    public void onResume() {
        super.onResume();
        //自动加载
        if (isBeginRefreshing) {
            beginRefreshing();
        } else {
            setBaseLoadData();
        }

    }

    /**
     * ------------------------初始化 下拉刷新控件-----------------------
     */

    protected BGANormalRefreshViewHolder viewHolder;

    private void setView() {
       /* tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginRefreshing();
            }
        });*/

        recyclerView.setHasFixedSize(true);//item高度固定，可以优化界面
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //使用BGA下拉刷新fragment需在onCreateView初始化
        bgaLayout.setDelegate(this);
        viewHolder = new BGANormalRefreshViewHolder(this, true);//设置为true可以上拉加载
        bgaLayout.setRefreshViewHolder(viewHolder);

        setBaseRefreshAdapter();


        //自动加载
        //beginRefreshing();

    }


    /**
     * ----------------下面三个方法需子类须实现--------------------------
     */
    //设置适配器
    protected void setBaseRefreshAdapter() {
     /*   guaranteeAuditAdapter = new GuaranteeAuditAdapter(mList, this);
        //false动画重复
        guaranteeAuditAdapter.isFirstOnly(false);
        //设置那种动画
        guaranteeAuditAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        recyclerView.setAdapter(guaranteeAuditAdapter);

        guaranteeAuditAdapter.setOnItemClickListener(this);*/
    }

    //网络请求
    protected int pageNum = 1;

    protected void setBaseLoadData() {
    }

    //处理获取后台数据逻辑处理
    protected void setBaseSuccessfulDate(String requestWhat, Object data) {
        /*List<ArticleList> articleLists = JSON.parseArray(((JSONObject) data).get("list").toString(), ArticleList.class);
        mList.addAll(articleLists);

        if (mList.size() == 0) {
            llLayout.setVisibility(View.VISIBLE);
        } else {
            llLayout.setVisibility(View.GONE);

            if (isAdd) {
                //上拉加载
                moreAdapter.setList(mList);
                if (articleLists.size() == 0) {
                    showToast("没有更多数据");
                }
            } else {
                //下拉刷新
                mList.clear();
                mList.addAll(articleLists);
                //下拉刷新调用适配器的setNewData方法
                moreAdapter.setNewData(mList);
            }


        }*/
    }

    /**
     * ----------------------------请求网络接口回调-----------------------------------
     */

    @Override
    public void onSuccessful(String requestWhat, Object data, ResponsePagesEntity page) {
        super.onSuccessful(requestWhat, data, page);
        llLayout.setVisibility(View.GONE);
        //关闭刷新
        bgaLayout.endRefreshing();
        bgaLayout.endLoadingMore();

        setBaseSuccessfulDate(requestWhat, data);

    }


    @Override
    public void onFailed(int what, Response response) {
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


    /**
     * -----------------------------刷新-----------------------
     */
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
                pageNum = pageNum + 1;
                isAdd = true;
                setBaseLoadData();

            }

        }, 500);
        return true;
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


}
