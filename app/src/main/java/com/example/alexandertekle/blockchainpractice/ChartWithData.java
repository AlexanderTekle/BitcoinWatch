package com.example.alexandertekle.blockchainpractice;

/**
 * Created by alexandertekle on 8/11/17.
 */
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;

public class ChartWithData {
    private float min, max;
    private LineData dataset;
    private BarData volDataSet;
    private float firstprice;

    public ChartWithData(float min, float max, LineData dataset, BarData volDataSet, float firstprice)
    {
        this.min = min;
        this.max = max;
        this.dataset = dataset;
        this.volDataSet = volDataSet;
        this.firstprice = firstprice;
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

    public BarData getVolDataSet() {
        return volDataSet;
    }

    public float getFirstprice() { return firstprice; }

}
