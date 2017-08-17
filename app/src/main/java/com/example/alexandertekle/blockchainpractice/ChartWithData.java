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
    private float currentPrice;
    private float diff;
    private float volume;
    private float percentdiff;



    public ChartWithData(float min, float max, LineData dataset, BarData volDataSet, float firstprice, float currentPrice, float diff, float percentdiff,
                         float volume)
    {
        this.min = min;
        this.max = max;
        this.dataset = dataset;
        this.volDataSet = volDataSet;
        this.firstprice = firstprice;
        this.currentPrice = currentPrice;
        this.diff = diff;
        this.percentdiff = percentdiff;

        this.volume = volume;
    }

    public float getMin()
    {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax()
    {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public LineData getDataset() {
        return dataset;
    }

    public void setDataset(LineData dataset) {
        this.dataset = dataset;
    }

    public void setVolDataSet(BarData volDataSet) {
        this.volDataSet = volDataSet;
    }

    public void setFirstprice(float firstprice) {
        this.firstprice = firstprice;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public float getDiff() {
        return diff;
    }

    public void setDiff(float diff) {
        this.diff = diff;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getPercentdiff() {
        return percentdiff;
    }

    public void setPercentdiff(float percentdiff) {
        this.percentdiff = percentdiff;
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
