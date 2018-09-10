package in.co.chicmic.chartsinandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity implements View.OnClickListener{

    private LineChart mLineChart;
    private PieChart mPieChart;
    private BarChart mBarChart;
    private CombinedChart mCombinedChart;
    private Button mButtonLineChart;
    private Button mButtonPieChart;
    private Button mButtonBarChart;
    private Button mButtonCombinedChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        initViews();
        setListeners();
    }

    private void initViews() {
        mLineChart = findViewById(R.id.line_chart);
        mBarChart = findViewById(R.id.bar_chart);
        mPieChart = findViewById(R.id.pie_chart);
        mCombinedChart = findViewById(R.id.bar_line_combine_chart);
        mButtonBarChart = findViewById(R.id.btn_bar_chart);
        mButtonPieChart = findViewById(R.id.btn_pie_chart);
        mButtonLineChart = findViewById(R.id.btn_line_chart);
        mButtonCombinedChart = findViewById(R.id.btn_bar_line_combine_chart);
    }

    private void setListeners() {
        mButtonLineChart.setOnClickListener(this);
        mButtonPieChart.setOnClickListener(this);
        mButtonBarChart.setOnClickListener(this);
        mButtonCombinedChart.setOnClickListener(this);
        mCombinedChart.setOnTouchListener(mListener);
    }


    // To disable touch on Graph.
    View.OnTouchListener mListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_bar_chart:
                showBarChart();
                break;
            case R.id.btn_line_chart:
                showLineChart();
                break;
            case R.id.btn_pie_chart:
                showPieChart();
                break;
            case R.id.btn_bar_line_combine_chart:
                showCombinedChart();
                break;
        }
    }

    private void showBarChart(){
        hideViews();
        mBarChart.setVisibility(View.VISIBLE);
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.getDescription().setText("");
        mBarChart.setDrawGridBackground(true);
        mBarChart.setDrawBarShadow(true);
        mBarChart.setHighlightFullBarEnabled(true);
        Legend l = mCombinedChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mMonths[(int) value % mMonths.length];
            }
        });

        BarData data = generateBarData();

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
        mBarChart.setData(data);
        mBarChart.invalidate();
    }


    private void showLineChart(){
        hideViews();
        mLineChart.setVisibility(View.VISIBLE);
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mLineChart.getDescription().setText("");
        mLineChart.setDrawGridBackground(true);

        Legend l = mLineChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mMonths[(int) value % mMonths.length];
            }
        });

        LineData data = generateLineData();
        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
        mLineChart.setData(data);
        mLineChart.invalidate();
    }


    private void showPieChart(){
        List<PieEntry> mPieEntry = new ArrayList<>();
        hideViews();
        mPieChart.setVisibility(View.VISIBLE);
        mPieChart.setUsePercentValues(true);
        mPieEntry.add(new PieEntry(70f, "Naushad", 25));
        mPieEntry.add(new PieEntry(80f, "Jubair", 25));
        mPieEntry.add(new PieEntry(65f, "Manbir", 25));
        mPieEntry.add(new PieEntry(51f, "Sonu", 25));
        PieDataSet pieDataSet = new PieDataSet(mPieEntry, "Pie Chart");
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        mPieChart.setData(pieData);
    }

    private void showCombinedChart(){
        hideViews();
        mCombinedChart.setVisibility(View.VISIBLE);
        mCombinedChart.getDescription().setText("");
        mCombinedChart.setDrawGridBackground(true);
        mCombinedChart.setDrawBarShadow(true);
        mCombinedChart.setHighlightFullBarEnabled(true);
        mCombinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,  CombinedChart.DrawOrder.LINE
        });

        Legend l = mCombinedChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        YAxis rightAxis = mCombinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = mCombinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = mCombinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mMonths[(int) value % mMonths.length];
            }
        });

        CombinedData data = new CombinedData();

        data.setData( generateLineData());
        data.setData(generateBarData());

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
        mCombinedChart.setData(data);
        mCombinedChart.invalidate();
    }

    private void hideViews() {
        mLineChart.setVisibility(View.GONE);
        mPieChart.setVisibility(View.GONE);
        mBarChart.setVisibility(View.GONE);
        mCombinedChart.setVisibility(View.GONE);
    }

    private ArrayList<Entry> getLineEntriesData(ArrayList<Entry> entries){
        entries.add(new Entry(1, 20));
        entries.add(new Entry(2, 10));
        entries.add(new Entry(3, 8));
        entries.add(new Entry(4, 40));
        entries.add(new Entry(5, 37));

        return entries;
    }

    private ArrayList<BarEntry> getBarEnteries(ArrayList<BarEntry> entries){
        entries.add(new BarEntry(1, 25));
        entries.add(new BarEntry(2, 30));
        entries.add(new BarEntry(3, 38));
        entries.add(new BarEntry(4, 10));
        entries.add(new BarEntry(5, 15));
        return  entries;
    }


    private LineData generateLineData() {
        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<>();
        entries = getLineEntriesData(entries);
        LineDataSet set = new LineDataSet(entries, "Line");
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries = getBarEnteries(entries);

        BarDataSet set1 = new BarDataSet(entries, "Bar");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setColors(ColorTemplate.COLORFUL_COLORS);
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.45f; // x2 dataset


        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);


        return d;
    }

    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "June"
    };
}
