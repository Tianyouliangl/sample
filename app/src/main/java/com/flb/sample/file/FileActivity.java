package com.flb.sample.file;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.adapter.TabLayoutAdapter;
import com.flb.sample.common.Constant;

import java.util.ArrayList;
import java.util.List;

public class FileActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private TabLayout file_tab;
    private ViewPager file_vp;
    private List<String> mList = new ArrayList<>();
    private TabLayoutAdapter tabAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_file;
    }

    @Override
    public void initView() {
        file_tab = findViewById(R.id.file_tab);
        file_vp = findViewById(R.id.file_vp);
    }

    @Override
    public void initData() {
        mList.add(Constant.type.DATE_QI);
        mList.add(Constant.type.DATE_SHIWU);
        mList.add(Constant.type.DATE_SANSHI);
        tabAdapter = new TabLayoutAdapter(getSupportFragmentManager(),mList);
        file_vp.setAdapter(tabAdapter);
        file_vp.setCurrentItem(0);
        file_tab.setupWithViewPager(file_vp);
        for (int i=0 ;i < tabAdapter.getCount() ; i++){
            TabLayout.Tab tab = file_tab.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.layout_tab_item);//给每一个tab设置view
            TextView title =  tab.getCustomView().findViewById(R.id.item_title);
            if (i == 0){
                title.setSelected(true);
            }
            title.setText(mList.get(i));
        }
        file_tab.addOnTabSelectedListener(this);
        file_vp.setOnPageChangeListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        TextView title = tab.getCustomView().findViewById(R.id.item_title);
        title.setSelected(true);
        file_vp.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        TextView title = tab.getCustomView().findViewById(R.id.item_title);
        title.setSelected(false);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        state ==1的时候默示正在滑动，
//        state==2的时候默示滑动完毕了，
//        state==0的时候默示什么都没做.
//            switch (state){
//                case 1:
//                    for (int i=0 ;i < tabAdapter.getCount() ; i++) {
//                        TabLayout.Tab tab = file_tab.getTabAt(i);//获得每一个tab
//                        TextView title = tab.getCustomView().findViewById(R.id.item_title);
//                        title.setSelected(false);
//                        title.setTextColor(Color.rgb(255, 255, 255));
//                    }
//                    break;
//                case 2:
//                    break;
//                case 0:
//                    int item = file_vp.getCurrentItem();
//                    TabLayout.Tab tab =  file_tab.getTabAt(item);
//                    TextView title =  tab.getCustomView().findViewById(R.id.item_title);
//                    title.setSelected(true);
//                    title.setTextColor(Color.rgb(17,17,17));
//                    break;
//                default:
//                    break;
//            }


    }
}
