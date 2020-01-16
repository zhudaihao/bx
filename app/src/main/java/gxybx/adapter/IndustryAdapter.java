package gxybx.adapter;


import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.gxybx.R;
import gxybx.bean.IndustryBean;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by Administrator on 2017/9/13.
 */

public class IndustryAdapter extends BaseQuickAdapter<IndustryBean.ArticlesBean, BaseViewHolder> {
    private List<IndustryBean.ArticlesBean> mList;
    private Context context;

    public void setList(List<IndustryBean.ArticlesBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public List<IndustryBean.ArticlesBean> getmList() {
        return mList;
    }

    public IndustryAdapter(List<IndustryBean.ArticlesBean> mList, Context context) {
        super(R.layout.item_industry, mList);
        this.mList = mList;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, IndustryBean.ArticlesBean item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_time, item.getCreateTimeStr());

        if (helper.getPosition()==0){
            RelativeLayout rl_item_industry=helper.itemView.findViewById(R.id.rl_item_industry);
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10,0,10);
            rl_item_industry.setLayoutParams(params);
        }

        ImageView imageView = (ImageView) helper.itemView.findViewById(R.id.iv_icon);
        Glide.with(context)
                .load(item.getFilePath())
                .error(R.drawable.image)
                .bitmapTransform(new RoundedCornersTransformation(context,10,0,RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);



    }



}
