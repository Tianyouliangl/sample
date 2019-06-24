package com.flb.sample.gallery;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.adapter.GalleryAdapter;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;

public class GalleryActivity extends BaseActivity {

    private RecyclerView rv_gallery;
    private List<String> mList = new ArrayList<>();
    private PagerSnapHelper snapHelper;

    @Override
    public int getContentView() {
        return R.layout.activity_gallery;
    }

    @Override
    public void initView() {
        rv_gallery = findViewById(R.id.rv_gallery);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_gallery.setLayoutManager(linearLayoutManager);
        //   mRecyclerView绑定scale效果
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv_gallery);

    }

    @Override
    public void initData() {
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561367671041&di=562f551a5a3e27d2aabbccb0e7f789d2&imgtype=0&src=http%3A%2F%2Fimg.alicdn.com%2Fimgextra%2Fi2%2F2345782751%2FTB2zWVJweySBuNjy1zdXXXPxFXa_%2521%25212345782751.jpg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561367671042&di=d6e85d119554add6f81149a601a0df5e&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20120808%2FImg350129585.jpg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561367671038&di=3517959dc3ad426e33d02058e0832457&imgtype=0&src=http%3A%2F%2Fp.qpic.cn%2Fdnfbbspic%2F0%2Fdnfbbs_dnfbbs_dnf_gamebbs_qq_com_forum_201905_13_092619pukba8oyqqu6kdvk.jpg%2F0");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561367671031&di=bdae3b8df837c6693894cc11ed524ee4&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160805%2F5dda5b7f080e4e5f9c4bc3f9b7eef4f5_th.jpg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561367671031&di=e71d8e811b9af99498fdea742e511706&imgtype=0&src=http%3A%2F%2Fp.qpic.cn%2Fdnfbbspic%2F0%2Fdnfbbs_dnfbbs_dnf_gamebbs_qq_com_forum_201905_13_092712zpx29pafjco2m2ae.jpg%2F0");
        rv_gallery.setAdapter(new GalleryAdapter(this, mList));
    }

}
