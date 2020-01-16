package gxybx.ui.activity;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import base.BaseActivity;
import baseBean.ResponsePagesEntity;
import butterknife.BindView;
import butterknife.OnClick;
import cn.gxybx.R;
import gxybx.bean.MeBean;
import gxybx.utils.DialogPhone;

/**
 * 联系我们
 */
public class ContactUsActivity extends BaseActivity {
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_ad)
    TextView tvAd;
    @BindView(R.id.tv_em)
    TextView tvEm;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @BindView(R.id.tv_connection_me)
    TextView tvConnectionMe;

    @Override
    public int getResLayout() {
        return R.layout.activity_contact_me;
    }

    @Override
    protected void initView() {
        super.initView();
        rl_base_title.setVisibility(View.VISIBLE);
        rb_base_left.setVisibility(View.VISIBLE);

        tvConnectionMe.setText("在线客服");
        getNetClient().getMe();
    }


    private MeBean.InfoBean info;

    @Override
    public void onSuccessful(String requestWhat, Object data, ResponsePagesEntity page) {
        super.onSuccessful(requestWhat, data, page);
        MeBean meBean = JSONObject.parseObject(data.toString(), MeBean.class);
        info = meBean.getInfo();

        ivIcon.setImageResource(R.mipmap.img);
        tvCompanyName.setText(info.getCompanyName());
        tvAd.setText(info.getDetailAddress());
        tvEm.setText(info.getServiceEmail());
        tvPhone.setText(info.getServiceTelphone());


    }


    private DialogPhone dialogPhone;

    @OnClick({R.id.tv_connection_me, R.id.rl_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_connection_me:
                if (info != null) {
                    String serviceUrl = info.getServiceUrl();
                    Intent intent = new Intent(this, NewsActivity.class);
                    intent.putExtra("title", "在线客户");
                    intent.putExtra("tag", 1);
                    intent.putExtra("url", serviceUrl);
                    startToActivity(intent, false);
                }
                break;
            //点击在线客服
            case R.id.rl_phone:
                if (info != null) {
                    dialogPhone = new DialogPhone(this, info.getServiceTelphone());
                    dialogPhone.show();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialogPhone != null) {
            dialogPhone.dismiss();
        }
    }
}
