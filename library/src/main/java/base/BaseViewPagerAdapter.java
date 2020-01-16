package base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public class BaseViewPagerAdapter extends FragmentPagerAdapter {

    //Fragment
    protected List<Fragment> list_Fragment;

    //指示器 标题
    private List<String> list_title;

    public BaseViewPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> list_title) {
        super(fm);
        this.list_Fragment = list;
        this.list_title = list_title;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return list_title.get(position);
    }

    @Override
    public int getCount() {
        return list_title.size();
    }


    @Override
    public Fragment getItem(int position) {
        return list_Fragment.get(position);
    }


}
