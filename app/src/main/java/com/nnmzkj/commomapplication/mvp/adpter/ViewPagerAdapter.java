package com.nnmzkj.commomapplication.mvp.adpter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.nnmzkj.common.base.BaseFragment;
import java.util.ArrayList;

/**
 * Date : 2020/3/9
 * Author : Davaid.lvfujiang
 * Desc : ViewPage适配器
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<BaseFragment> mFragments;
    private String[] mTitles;

    public ViewPagerAdapter(FragmentManager fm, ArrayList mFragments, String[] mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
