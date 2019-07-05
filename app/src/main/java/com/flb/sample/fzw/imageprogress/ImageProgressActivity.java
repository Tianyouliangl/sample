package com.flb.sample.fzw.imageprogress;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.adapter.ImageProgressAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/5/27
 */
public class ImageProgressActivity extends BaseActivity{

    private List<String> mList;
    private ViewPager vp_image;
    private ImageProgressAdapter adapter;

    @Override
    public int getContentView() {
        return R.layout.activity_imageprogress;
    }

    @Override
    public void initView() {
        vp_image = findViewById(R.id.vp_image);
        mList = new ArrayList<>();
    }

    @Override
    public void initData() {
        mList.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1558939765&di=cad6940a43348b9c33fc2e125a24a966&src=http://b-ssl.duitang.com/uploads/item/201811/10/20181110234310_kdgdr.jpeg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558949886395&di=6a35e13feb9acbfac06273e4f2a54c35&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F87969682f40fbe99747a2e07ed616c24fe7c9679b8a7-PfvLq6_fw658");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558949886393&di=d4851343600a78c8a99a138cff52fcee&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2F201412%2F30%2F161211bmlcwpkkw3ffpifz.jpg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558949928657&di=74ff76b3bda92d88dbc614fbf61fbda1&imgtype=jpg&src=http%3A%2F%2Fs11.sinaimg.cn%2Forignal%2Faca685efh7b6c246a317a%26690");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558949886391&di=94801dcfc405718c6f14272702f0641b&imgtype=0&src=http%3A%2F%2Fs8.sinaimg.cn%2Fmiddle%2F49c12368g6f5e3132dc87%26690");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558949886391&di=53f06244a95f3afc3b8f9ae2fa7d4d31&imgtype=0&src=http%3A%2F%2Fattachments.gfan.net.cn%2Fforum%2F201501%2F22%2F213254kb5oytb8zzki58h5.jpg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558949886390&di=dd1f4ef3de057d01bf439152b13ba950&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20110203%2FImg302850891.jpg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558949886385&di=09d9d1b57728423ae1897d8b3387f910&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F27%2F20151127173805_NfHWv.thumb.700_0.jpeg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558949886424&di=129882563e9ba882ad5c550d11e379a5&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F406c1528490a2de3489ebf80302c4294c3f1f10b24b65-kL3FeZ_fw658");
        adapter = new ImageProgressAdapter(getSupportFragmentManager(), mList);
        vp_image.setAdapter(adapter);
    }
}
