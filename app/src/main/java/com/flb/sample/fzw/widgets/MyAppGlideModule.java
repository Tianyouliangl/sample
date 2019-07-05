package com.flb.sample.fzw.widgets;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * author : 冯张伟
 * date : 2019/5/28
 */

@GlideModule
public class MyAppGlideModule  extends AppGlideModule {
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
