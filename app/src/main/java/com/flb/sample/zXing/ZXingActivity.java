package com.flb.sample.zXing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ZXingActivity extends BaseActivity implements View.OnClickListener {


    @Override
    public int getContentView() {
        return R.layout.activity_zxing;
    }

    @Override
    public void initView() {
        Button  btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            new IntentIntegrator(this)
//                    .setCaptureActivity(ZXingActivity.class)
                    .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                    .setPrompt("请对准二维码")// 设置提示语
                    .setCameraId(0)// 选择摄像头,可使用前置或者后置
                    .setBeepEnabled(false)// 是否开启声音,扫完码之后会"哔"的一声
                    // 初始化扫码
                    .initiateScan();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (null != result && null != result.getContents()) {
            Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
