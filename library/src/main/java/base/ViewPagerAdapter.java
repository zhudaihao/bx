package base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import gxy.library.R;


/**
 * Created by Administrator on 2017/7/1.
 */

public class ViewPagerAdapter extends PagerAdapter {

    protected Context context;



    //创建个集合装载组件
    protected List<ImageView> list = new ArrayList<>();
    protected int[] image = {R.mipmap.one, R.mipmap.two, R.mipmap.threa};


    //构造方法
    public ViewPagerAdapter(Context context) {
        this.context = context;

        for (int i = 0; i < image.length; i++) {
            //创建个组件
            ImageView iv = new ImageView(context);
            //把图片设置到组件
            iv.setImageResource(image[i]);

            //设置组件的全屏拉伸属性
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //注意用这个属性图片就按以前的比例拉伸就可能不能全屏 iv.setAdjustViewBounds(true);
            //把组件装进集合
            list.add(iv);

        }

    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(list.get(position));
        return list.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));

    }


}
