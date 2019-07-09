package com.flb.sample.fzw.jND2B;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.adapter.DialogAdapter;
import com.flb.sample.fzw.adapter.JNDAdapter;
import com.flb.sample.fzw.model.DialogBean;
import com.flb.sample.fzw.widgets.CountNumberView;
import com.flb.sample.fzw.widgets.LogUtil;
import com.flb.sample.fzw.widgets.TimeUtil;
import com.zsml.dialoglibrary.widget.CustomDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class JND2BActivity extends BaseActivity implements View.OnClickListener {


    private CountNumberView tv_one, tv_two, tv_three, tv_number;
    private RecyclerView rl_number;
    private Button btn_start;
    private List<Integer> numberList;
    private List<String> mList = new ArrayList<>();
    private List<String> infoList = new ArrayList<>();
    private int MAX = 10000;
    private int DIGIT = 20;
    private JNDAdapter jndAdapter;
    private Button btn_history;

    @Override
    public int getContentView() {
        return R.layout.activity_jnd2_b;
    }

    @Override
    public void initView() {
        tv_one = findViewById(R.id.tv_number_one);
        tv_two = findViewById(R.id.tv_number_two);
        tv_three = findViewById(R.id.tv_number_three);
        tv_number = findViewById(R.id.tv_number);
        rl_number = findViewById(R.id.rl_number);
        btn_start = findViewById(R.id.btn_start);
        btn_history = findViewById(R.id.btn_history);
    }

    @Override
    public void initData() {
        tv_one.setText("?");
        tv_two.setText("?");
        tv_three.setText("?");
        tv_number.setText("?");
        numberList = new ArrayList<>(20);
        rl_number.setLayoutManager(new LinearLayoutManager(this));
        jndAdapter = new JNDAdapter(this, mList);
        rl_number.setAdapter(jndAdapter);
        btn_start.setOnClickListener(this);
        btn_history.setOnClickListener(this);
    }

    private void setAdapterData(String s) {
        int size = mList.size();
        if (jndAdapter != null) {
            mList.add(s);
            if (size == 0) {
                jndAdapter.notifyItemInserted(0);
            } else {
                jndAdapter.notifyItemInserted(size - 1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            if (mList.size()> 0 && numberList.size() > 0){
                reSet();
            }
            getRandom();
        }
        if (v.getId() == R.id.btn_history){
            if (infoList.size() > 0){
              CustomDialog  dialog = new CustomDialog.Builder(this)
                        .view(R.layout.layout_dialog)
                        .build();
                RecyclerView rl = dialog.findView(R.id.rl_dialog);
                rl.setLayoutManager(new LinearLayoutManager(this));
                StatusLayoutManager statusLayoutManager = new StatusLayoutManager.Builder(rl).build();
                statusLayoutManager.showLoadingLayout();
                dialog.show();
                Map<String,Integer> map = new HashMap<>();
                List<DialogBean> list = new ArrayList<>();
                for (String string : infoList) {
                    if(map.containsKey(string)) {
                        map.put(string, map.get(string).intValue()+1);
                    }else {
                        map.put(string, new Integer(1));
                    }
                }
                Iterator<String> iter = map.keySet().iterator();
                while(iter.hasNext()) {
                    String key = iter.next();
                    DialogBean bean = new DialogBean();
                    bean.setType(key);
                    bean.setNumber(map.get(key));
                    list.add(bean);
                }
                DialogAdapter adapter = new DialogAdapter(this, list);
                rl.setAdapter(adapter);
                statusLayoutManager.showSuccessLayout();
            }else {
                Toast.makeText(this,"没有记录",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void reSet() {
        btn_start.setText("重新开始");
        tv_one.setText("?");
        tv_two.setText("?");
        tv_three.setText("?");
        tv_number.setText("?");
        jndAdapter.notifyItemRangeRemoved(0, mList.size());
        mList.clear();
        numberList.clear();
    }

    private void getRandom() {
        Random rand = new Random();
        for (int i = 0; i < DIGIT; i++) {
            int num = rand.nextInt(MAX);
            numberList.add(num);
        }
        Collections.sort(numberList);
        setText();
        setNumber();
    }

    private void setNumber() {
        float number0 = tv_one.getNumber();
        float number1 = tv_two.getNumber();
        float number2 = tv_three.getNumber();
        float num = (number0 + number1 + number2);
        if (number0 == number1 && number0 == number2 && number1 == number2) {
            setAdapterData("豹子");
            infoList.add("豹子");
        }
        tv_number.showNumberWithAnimation(num, CountNumberView.INTREGEX);
        setColor((int) num);
    }

    private void setColor(int a) {
        setAdapterData(TimeUtil.bgColorName[a]);
        infoList.add(TimeUtil.bgColorName[a]);
        infoList.add(String.valueOf(a));
        if (a < 14) {
            if ((a % 2) == 0) {
                setAdapterData("小双");
                infoList.add("小双");
            } else {
                setAdapterData("小单");
                infoList.add("小单");
            }
        } else if (a >= 14) {
            if ((a % 2) == 0) {
                setAdapterData("大双");
                infoList.add("大双");
            } else {
                setAdapterData("大单");
                infoList.add("大单");
            }
        }
        if ((a % 2) == 0) {
            setAdapterData("双");
        } else {
            setAdapterData("单");
        }
        tv_number.setBackgroundResource(TimeUtil.bgColor[a]);

    }

    private void setText() {
        // 2 5 8 11 14 17
        int b0 = numberList.get(2) + numberList.get(5) +
                numberList.get(8) + numberList.get(11) +
                numberList.get(14) + numberList.get(17);
        int len0 = String.valueOf(b0).length();
        String num_one = String.valueOf(b0).substring(len0 - 1, len0);
        tv_one.showNumberWithAnimation(Float.valueOf(num_one), CountNumberView.INTREGEX);

        //3 6 9 12 15 18
        int b1 = numberList.get(3) + numberList.get(6) +
                numberList.get(9) + numberList.get(12) +
                numberList.get(15) + numberList.get(18);
        int len1 = String.valueOf(b1).length();
        String num_two = String.valueOf(b1).substring(len1 - 1, len1);
        tv_two.showNumberWithAnimation(Float.valueOf(num_two), CountNumberView.INTREGEX);

        //4 7 10 13 16 19
        int b2 = numberList.get(4) + numberList.get(7) +
                numberList.get(10) + numberList.get(13) +
                numberList.get(16) + numberList.get(19);
        int len2 = String.valueOf(b2).length();
        String num_three = String.valueOf(b2).substring(len2 - 1, len2);
        tv_three.showNumberWithAnimation(Float.valueOf(num_three), CountNumberView.INTREGEX);

    }
}
