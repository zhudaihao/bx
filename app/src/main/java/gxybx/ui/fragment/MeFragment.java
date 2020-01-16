package gxybx.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import base.BaseFragment;
import baseBean.ResponsePagesEntity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.gxybx.R;
import gxybx.bean.CompanyInfoBean;
import gxybx.ui.activity.ContactUsActivity;
import gxybx.ui.activity.NewsActivity;
import utils.Util;


/**
 * 更多
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.tv_icon)
    ImageView tvIcon;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_connection_me)
    TextView tvConnectionMe;
    @BindView(R.id.tv_versions)
    TextView tvVersions;
    @BindView(R.id.tv_versions_code)
    TextView tvVersionsCode;
    Unbinder unbinder;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        tvCompany.setText("公司简介");
        tvConnectionMe.setText("联系我们");
        tvVersions.setText("当前版本");

        String versionCode = Util.getVersionName(getContext());
        tvVersionsCode.setText(versionCode);

        tvIcon.setImageResource(R.mipmap.bg);
    }

    /**
     * 点击监听
     *
     * @param view
     */
    @OnClick({R.id.tv_company, R.id.tv_connection_me, R.id.ll_versions})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //公司简介
            case R.id.tv_company:
                getNetClient().getCompanyInfo();
                break;
                //联系我们
            case R.id.tv_connection_me:
                startToActivity(getActivity(), ContactUsActivity.class, false);
                break;
                //版本
            case R.id.ll_versions:

                break;
        }
    }

    @Override
    public void onSuccessful(String requestWhat, Object data, ResponsePagesEntity page) {
        super.onSuccessful(requestWhat, data, page);
        CompanyInfoBean companyInfoBean = JSONObject.parseObject(data.toString(), CompanyInfoBean.class);
        String content = companyInfoBean.getUrl();
        if (!Util.isEmpty(content)) {
            Intent intent=new Intent(getActivity(), NewsActivity.class);
            intent.putExtra("url",content);
            intent.putExtra("title","公司简介");
            startToActivity(intent);
        }

    }
}
