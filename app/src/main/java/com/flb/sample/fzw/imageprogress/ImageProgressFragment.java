package com.flb.sample.fzw.imageprogress;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.flb.sample.fzw.BaseLazyFragment;
import com.flb.sample.fzw.R;

/**
 * author : 冯张伟
 * date : 2019/5/27
 */
public class ImageProgressFragment extends BaseLazyFragment{

    private ImageView glideImageView;

    @Override
    protected int setContentView() {
        return R.layout.fragment_image_progress;
    }

    @Override
    protected void init(View view) {
        glideImageView = view.findViewById(R.id.iv_progress);
    }

    @Override
    protected void lazyLoad() {
        String url = getArguments().getString("url");
        Glide.with(this).load(url).into(glideImageView);
    }


}
