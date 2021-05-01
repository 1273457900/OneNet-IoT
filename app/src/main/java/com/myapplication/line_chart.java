package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Delayed;

public class line_chart extends AppCompatActivity implements OnChartGestureListener {

    private static final String TAG = "myTag";
    private int[] mDataYs = new int[]{80, 70};
    private LineChart mLineChart;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
         mLineChart = (LineChart) findViewById(R.id.line_chart);






        new Thread(getRunable).start();

    }

    //获取数据线程
    final Runnable getRunable = new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            line_chart.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    RefreshChart();
                }
            });
            Looper.loop();
        }
    };

    public void RefreshChart(){
        Intent intent = getIntent();

        mDataYs=Arrays.copyOf(mDataYs, mDataYs.length+1);

        mDataYs[mDataYs.length-1]=Integer.parseInt(intent.getStringExtra("data")) ;
        setLineChart(mLineChart);
        loadLineChartData(mLineChart);
        Log.w("mDataYs",""+mDataYs[mDataYs.length-1]);
        new Thread(getRunable).start();
    }




    /**
     * 设置折现图的样式
     *
     * @param chart
     */
    private void setLineChart(LineChart chart) {

//        chart.setBackgroundColor(0xff00ff00);

//        chart.setDescription("zhuanghongji");
//        chart.setDescriptionColor(Color.RED);
//
//        chart.setDescriptionPosition(150f,150f);
//        chart.setDescriptionTextSize(16f);

//        chart.setNoDataTextDescription("Data 为空！");

        // 设置手势
        chart.setOnChartGestureListener(this);
//        chart.setScaleEnabled(true);
//        chart.setTouchEnabled(true);
//        chart.setScaleXEnabled(true);
//        chart.setScaleYEnabled(true);
//        chart.setPinchZoom(true);
//        chart.setDragEnabled(true);

        chart.setDrawGridBackground(false); // 设置网格背景
//        chart.setGridBackgroundColor(Color.BLUE);

//        chart.setDrawBorders(true);
//        chart.setBorderColor(Color.YELLOW);
//        chart.setBorderWidth(4f);

        chart.setTouchEnabled(true);
        chart.setDoubleTapToZoomEnabled(true);

        chart.setScaleEnabled(false); // 设置缩放
        chart.setDoubleTapToZoomEnabled(false); // 设置双击不进行缩放

        chart.setAutoScaleMinMaxEnabled(false);

        // 设置X轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
//        xAxis.setTypeface(mTf); // 设置字体
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(false);
//        xAxis.setDrawLabels(false);

//        xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());


        // Styling / modifying the axis
//        xAxis.setTextColor(Color.BLUE);
//        xAxis.setTextSize(24f);
//        xAxis.setGridLineWidth(10f);
//        xAxis.setGridColor(Color.RED);
//        xAxis.setAxisLineColor(Color.GREEN);
//        xAxis.setAxisLineWidth(5f);
//        xAxis.enableGridDashedLine(4,4,1);

        // 设置x轴的LimitLine，index是从0开始的
//        LimitLine xLimitLine = new LimitLine(4f,"xL 测试");
//        xLimitLine.setLineColor(Color.GREEN);
//        xLimitLine.setTextColor(Color.GREEN);
//        xAxis.addLimitLine(xLimitLine);
//        xAxis.setDrawLimitLinesBehindData(true);

        xAxis.setSpaceBetweenLabels(1);
//        xAxis.setLabelsToSkip(4);
//        xAxis.resetLabelsToSkip();
//        xAxis.setAvoidFirstLastClipping(true);
//        xAxis.setPosition(XAxis.XAxisPosition.TOP);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        // 图例
        Legend legend = chart.getLegend();
//        legend.setEnabled(false);
//        legend.setTextColor(Color.GREEN);
        legend.setTextSize(10f);
//        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
//        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        legend.setFormSize(18f);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setForm(Legend.LegendForm.LINE);

        // 设置x轴的LimitLine
//        LimitLine yLimitLine = new LimitLine(50f,"yLimit 测试");
//        yLimitLine.setLineColor(Color.RED);
//        yLimitLine.setTextColor(Color.RED);

        // 获得左侧侧坐标轴
        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setValueFormatter(new MyYAxisValueFormatter());
        leftAxis.setTextSize(18f);
//        leftAxis.setTextColor(Color.GREEN);

        // 出现错误，leftAxis的setValueFormatter参数类型只能是 YAxisFormatter
//        leftAxis.setValueFormatter(new MyValueFormatter());


//        leftAxis.addLimitLine(yLimitLine);
//        leftAxis.setDrawLimitLinesBehindData(false);
//        leftAxis.setStartAtZero(false);
//        leftAxis.setAxisMinValue(30);
//        leftAxis.setAxisMaxValue(60);

//        leftAxis.setInverted(true);

//        leftAxis.setTypeface(mTf);
//        leftAxis.setAxisLineWidth(1.5f);

//        leftAxis.setLabelCount(5, false);
//        leftAxis.setShowOnlyMinMax(true);

//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        //设置右侧坐标轴
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawAxisLine(false); // 右侧坐标轴线
        rightAxis.setDrawLabels(false); // 右侧坐标轴数组Label
//        rightAxis.setTypeface(mTf);
//        rightAxis.setLabelCount(5);
//        rightAxis.setDrawGridLines(false);



    }

    /**
     * 为折线图设置数据
     *
     * @param chart
     */
    private void loadLineChartData(final LineChart chart) {
        //所有线的List
        ArrayList<LineDataSet> allLinesList = new ArrayList<LineDataSet>();

        ArrayList<Entry> entryList = new ArrayList<Entry>();
        for (int i = 0; i < mDataYs.length; i++) {
            //Entry(yValue,xIndex);一个Entry表示一个点，第一个参数为y值，第二个为X轴List的角标
            entryList.add(new Entry(mDataYs[i], i));
        }


        //LineDataSet可以看做是一条线
        LineDataSet dataSet = new LineDataSet(entryList, "dataLine");
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleSize(4.5f);
        dataSet.setHighLightColor(Color.RED); // 设置点击某个点时，横竖两条线的颜色
        dataSet.setDrawValues(false); // 是否在点上绘制Value
        dataSet.setValueTextColor(Color.GREEN);
        dataSet.setValueTextSize(24f);
        allLinesList.add(dataSet);

//        Typeface tf1 = Typeface.createFromAsset(getAssets(), "OpenSans-BoldItalic.ttf");
//        dataSet.setValueTypeface(tf1);

//        Typeface tf2 = Typeface.createFromAsset(getAssets(), "OpenSans-LightItalic.ttf");
//        dataSet.setValueTypeface(tf2);

        //LineData表示一个LineChart的所有数据(即一个LineChart中所有折线的数据)
        LineData mChartData = new LineData(getXAxisShowLable(), allLinesList);

        // set data
        chart.setData((LineData) mChartData);

        // 设置动画
//        chart.animateX(8000);
//        chart.animateY(8000);
//        chart.animateXY(8000, 8000);
        // 1
//        chart.animateY(8000, Easing.EasingOption.Linear);
//        chart.animateY(8000, Easing.EasingOption.EaseInQuad);
//
//        chart.animateY(8000, Easing.EasingOption.EaseOutQuad);
//        chart.animateY(8000, Easing.EasingOption.EaseInOutQuad);
//
//        chart.animateY(8000, Easing.EasingOption.EaseInCubic);
//        chart.animateY(8000, Easing.EasingOption.EaseOutCubic);
        // 7
//        chart.animateY(8000, Easing.EasingOption.EaseInOutCubic);
//        chart.animateY(8000, Easing.EasingOption.EaseInQuart);
//
//        chart.animateY(8000, Easing.EasingOption.EaseOutQuart);
//        chart.animateY(8000, Easing.EasingOption.EaseInOutQuart);
//
//        chart.animateY(8000, Easing.EasingOption.EaseInSine);
//        chart.animateY(8000, Easing.EasingOption.EaseOutSine);
        // 13
//        chart.animateY(8000, Easing.EasingOption.EaseInOutSine);
//        chart.animateY(8000, Easing.EasingOption.EaseInExpo);

//        chart.animateY(8000, Easing.EasingOption.EaseOutExpo);
//        chart.animateY(8000, Easing.EasingOption.EaseInOutExpo);

//        chart.animateY(8000, Easing.EasingOption.EaseInCirc);
//        chart.animateY(8000, Easing.EasingOption.EaseOutCirc);
        // 19
//        chart.animateY(8000, Easing.EasingOption.EaseInOutCirc);
//        chart.animateY(8000, Easing.EasingOption.EaseInElastic);

//        chart.animateY(8000, Easing.EasingOption.EaseOutElastic);
//        chart.animateY(8000, Easing.EasingOption.EaseInOutElastic);
        // 23
//        chart.animateY(8000, Easing.EasingOption.EaseInBack);
//        chart.animateY(8000, Easing.EasingOption.EaseOutBack);

//        chart.animateY(8000, Easing.EasingOption.EaseInOutBack);
//        chart.animateY(8000, Easing.EasingOption.EaseInBounce);
        // 27
//        chart.animateY(8000, Easing.EasingOption.EaseOutBounce);
//        chart.animateY(8000, Easing.EasingOption.EaseInOutBounce);

//        chart.animateY(2000, Easing.EasingOption.EaseInOutBounce);

        // Modifying the Viewport -> Restraining what's visible
//        chart.setVisibleXRangeMaximum(3);
//        chart.setVisibleXRangeMinimum(17);
        chart.setVisibleYRangeMaximum(50f,YAxis.AxisDependency.LEFT);
//        chart.setVisibleYRangeMaximum(70f,YAxis.AxisDependency.RIGHT);
//        chart.setViewPortOffsets(50,50,50,50);
//        chart.resetViewPortOffsets();
//        chart.setExtraOffsets(50,0,0,0);

        // Modifying the Viewport -> Moving the view
//        chart.moveViewToX(2);
        chart.moveViewToY(60,YAxis.AxisDependency.LEFT);
//        chart.fitScreen();

        // Modifying the Viewport -> Zooming
//        chart.zoomIn();
//        chart.zoomOut();

        if(mDataYs.length>10)
        {
            int[] x={mDataYs[mDataYs.length-1]};
            mDataYs=x;

        }
    }


    private ArrayList<String> getXAxisShowLable() {//设置X轴
        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");
        return m;
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d(TAG, "onChartGestureStart");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d(TAG, "onChartGestureEnd");
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.d(TAG, "onChartLongPressed");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.d(TAG, "onChartDoubleTapped");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.d(TAG, "onChartSingleTapped");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.d(TAG, "onChartFling");
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.d(TAG, "onChartScale");
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.d(TAG, "onChartTranslate");
    }
}
