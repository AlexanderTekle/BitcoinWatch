package bitcoin.watch.alexandertekle.blockchainpractice.Charts;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alexandertekle on 8/22/17.
 */

public class TimeAxisValueFormatter implements IAxisValueFormatter {
    DateFormat sdf = new SimpleDateFormat("HH:mm");

    public TimeAxisValueFormatter()
    {

    }

    public String getFormattedValue(float value, AxisBase axis)
    {
        float timestamp = value * 1000L;
        Date netDate = (new Date((long)timestamp));
        return sdf.format(netDate);

    }


}
