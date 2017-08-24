package bitcoin.watch.alexandertekle.blockchainpractice.Charts;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 * Created by alexandertekle on 8/12/17.
 */

public class VolumeChart {
    public static float[] getData(int days) throws Exception
    {
        int amount;
        //float [] values = new float[amount-1];
        String url= "";
        if (days == 1)
        {
            amount = 25;
            url = "https://min-api.cryptocompare.com/data/histohour?fsym=BTC&tsym=USD&limit=24&aggregate=1";

        }
        else {
            amount = days;
            url = "https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit="+amount+"&aggregate=1&e=CCCAGG";
           // url = "https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=" + (amount) + "&aggregate=7&e=CCCAGG";
        }

        float values[] = new float[amount];


        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = client.execute(request);


        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";

        String jsonLine = "[";
        String json = "";
        while ((json = rd.readLine()) != null){
            jsonLine += json;
        }
        jsonLine += "]";


        JSONTokener tokener = new JSONTokener(jsonLine);
        JSONArray jsonArray = new JSONArray(tokener);
        JSONObject j = jsonArray.getJSONObject(0);

        JSONArray data = j.getJSONArray("Data");
        //Log.d("here", data.toString());
        int i = 0;

        while (i < amount) {
            JSONObject daily = data.getJSONObject(i);
            //Log.d("now",i + ": "+Float.parseFloat(daily.getString("y")));
            //Log.d("daily.")
            values[i] = Float.parseFloat(daily.getString("volumeto"));
            //Log.d("val", daily.getString("volumeto"));
            i++;
        }

        return values;
    }
}

