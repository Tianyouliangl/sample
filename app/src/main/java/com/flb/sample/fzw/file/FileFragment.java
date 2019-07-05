package com.flb.sample.fzw.file;

import android.graphics.Color;
import android.view.View;

import com.flb.sample.fzw.BaseLazyFragment;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.common.Constant;
import com.flb.sample.fzw.model.ChartBean;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/4/19
 */
public class FileFragment extends BaseLazyFragment{


    private LineChart chart;
    private Gson gson;
    private int charDataSize;
    private int chartXCount ;

    @Override
    protected int setContentView() {
        return R.layout.fragment_file;
    }

    @Override
    protected void init(View view) {
        gson = new Gson();
        chart = view.findViewById(R.id.chart);
    }

    @Override
    protected void lazyLoad() {

        String type = getArguments().getString("type");

        if (type.equals(Constant.type.DATE_QI)){
            charDataSize = 7;
            chartXCount = 7;
        }

        if (type.equals(Constant.type.DATE_SHIWU)){
            charDataSize = 15;
            chartXCount = 5;
        }

        if (type.equals(Constant.type.DATE_SANSHI)){
            charDataSize = 30;
            chartXCount = 5;
        }

        chart.animateX(1000, Easing.EasingOption.EaseInOutQuart);
        ChartBean json = gson.fromJson(Constant.type.json_chart, ChartBean.class);
        List<ChartBean.ThirtyDayBean> chartList = json.getThirtyDay();
        chart.setDoubleTapToZoomEnabled(false);
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < charDataSize; i++) {
            entries.add(new Entry(i,chartList.get(i).getReadCnt()));
        }

        //设置样式
        YAxis rightAxis = chart.getAxisRight();

        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();

        //设置图表左边的y轴禁用
        leftAxis.setDrawGridLines(false);
        leftAxis.setEnabled(true);
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            list.add(i+"月");
        }

        //设置x轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));
        xAxis.setAxisMinimum(0f);
        xAxis.setLabelCount(chartXCount);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘

        //透明化图例
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);

        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(Color.parseColor("#7d7d7d"));//线条颜色
        dataSet.setDrawFilled(true);
        dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
        dataSet.setLineWidth(1f);


        LineData lineData = new LineData(dataSet);
        lineData.setDrawValues(false);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }


}
