package gxybx.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.gxybx.R;
import gxybx.bean.ProductBean;


/**
 * Created by Administrator on 2017/9/13.
 */

public class HomeAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {

    private Context context;
    private List<ProductBean> mList;

    public void setList(List<ProductBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    //定义类型
    public HomeAdapter(Context context, List<ProductBean> data) {
        super(R.layout.item_home,data);
        this.context = context;
        this.mList = data;

    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {
        //给控件赋值
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_time, item.getDetails());

        ImageView imageView = (ImageView) helper.itemView.findViewById(R.id.iv_icon);

        Glide.with(context)
                .load(item.getImage())
                .into(imageView);


    }
}
