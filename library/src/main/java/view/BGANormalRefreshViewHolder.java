/**
 * Copyright 2015 bingoogolapple
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import gxy.library.R;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 13:05------------------测试---------
 * 描述:类似新浪微博下拉刷新风格
 */
public class BGANormalRefreshViewHolder extends BGARefreshViewHolder {
    private TextView mHeaderStatusTv;
    private ImageView mHeaderArrowIv;
    private ImageView mHeaderChrysanthemumIv;
    private AnimationDrawable mHeaderChrysanthemumAd;


    private RotateAnimation mUpAnim;
    private RotateAnimation mDownAnim;

    private String mPullDownRefreshText = "下拉刷新";
    private String mReleaseRefreshText = "释放更新";
    private String mRefreshingText = "加载中...";

    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public BGANormalRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
        //initAnimation();
    }

    private void initAnimation() {
        mUpAnim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mUpAnim.setDuration(150);
        mUpAnim.setFillAfter(true);

        mDownAnim = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mDownAnim.setFillAfter(true);
    }

    /**
     * 设置未满足刷新条件，提示继续往下拉的文本
     *
     * @param pullDownRefreshText
     */
    public void setPullDownRefreshText(String pullDownRefreshText) {
        mPullDownRefreshText ="下拉刷新";
    }

    /**
     * 设置满足刷新条件时的文本
     *
     * @param releaseRefreshText
     */
    public void setReleaseRefreshText(String releaseRefreshText) {
        mReleaseRefreshText ="松开刷新";
    }

    /**
     * 设置正在刷新时的文本
     *
     * @param refreshingText
     */
    public void setRefreshingText(String refreshingText) {
        mRefreshingText ="正在刷新";
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_header_normal, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }
            mHeaderStatusTv = (TextView) mRefreshHeaderView.findViewById(R.id.tv_normal_refresh_header_status);
            mHeaderArrowIv = (ImageView) mRefreshHeaderView.findViewById(R.id.iv_normal_refresh_header_arrow);
            mHeaderChrysanthemumIv = (ImageView) mRefreshHeaderView.findViewById(R.id.iv_normal_refresh_header_chrysanthemum);
            mHeaderChrysanthemumAd = (AnimationDrawable) mHeaderChrysanthemumIv.getDrawable();
            mHeaderArrowIv.setImageResource(R.mipmap.a1);
            mHeaderStatusTv.setText(mPullDownRefreshText);
        }
        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
    }

    @Override
    public void changeToIdle() {
    }

    @Override
    public void changeToPullDown() {
        mHeaderStatusTv.setText(mPullDownRefreshText);
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumAd.stop();
        mHeaderArrowIv.setVisibility(View.VISIBLE);
//        mDownAnim.setDuration(150);
        mHeaderArrowIv.setImageResource(R.mipmap.a1);
    }

    @Override
    public void changeToReleaseRefresh() {
        mHeaderStatusTv.setText(mReleaseRefreshText);
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumAd.stop();
        mHeaderArrowIv.setVisibility(View.VISIBLE);
//        mHeaderArrowIv.startAnimation(mUpAnim);
        mHeaderArrowIv.setImageResource(R.mipmap.a10);
    }

    @Override
    public void changeToRefreshing() {
        mHeaderArrowIv.setImageResource(R.mipmap.a1);
        mHeaderStatusTv.setText(mRefreshingText);
        // 必须把动画清空才能隐藏成功
        mHeaderArrowIv.clearAnimation();
        mHeaderArrowIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumIv.setVisibility(View.VISIBLE);
        mHeaderChrysanthemumAd.start();
    }

    @Override
    public void onEndRefreshing() {
        mHeaderStatusTv.setText("刷新完成");
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumAd.stop();
        mHeaderArrowIv.setVisibility(View.VISIBLE);
//        mDownAnim.setDuration(0);
        mHeaderArrowIv.setImageResource(R.mipmap.a15);
//        mHeaderArrowIv.startAnimation(mDownAnim);
    }

}