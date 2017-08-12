package com.example.alexandertekle.blockchainpractice;

/**
 * Created by alexandertekle on 8/11/17.
 */
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class ChartWithData {
    private float min, max;
    private LineData dataset;

    public ChartWithData(float min, float max, LineData dataset)
    {
        this.min = min;
        this.max = max;
        this.dataset = dataset;
    }

    public float getMin()
    {
        return min;
    }

    public float getMax()
    {
        return max;
    }

    public LineData getDataSet()
    {
        return dataset;
    }

}
