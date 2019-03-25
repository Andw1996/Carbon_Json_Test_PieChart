package com.example.carbon_json_test_piechart;

import android.os.AsyncTask;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


//fuelmix ID 20 = 5123.51
//fuelmix ID 49 = 4891.86
//Above are the total values of two different fuelmix inputs. Around 5000
//The values of the json data represent the MW generated due to the use of that particular fuel.


public class fetchData extends AsyncTask <Void, Void, Void> {

    String data = "";
    String dataParsed = "";
    String singleParsed ="";
    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("https://carbondown.tk/fuelmix");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray JA = new JSONArray(data);
            for(int i = 0; i < 1; i++){
                JSONObject JO = (JSONObject) JA.get(JA.length()-1); // The length -1 fetches the latest array object
                int renew = JO.getInt("renew");
                singleParsed =  "fuelmixID: " + JO.get("fuelmixID") + "\n" +
                                "fuelmixDateTime: " + JO.get("fuelmixDateTime") + "\n" +
                                "coal: " + JO.get("coal") + "\n" +
                                "gas: " + JO.get("gas") + "\n" +
                                "netImport: " + JO.get("netImport") + "\n" +
                                "otherFossil: " + JO.get("otherFossil") + "\n" +
                                "renew: " + JO.get("renew") + "\n";

                dataParsed = dataParsed + singleParsed + "\n";

            }

            PieDataSet dataSet = new PieDataSet((List<PieEntry>) JA, "FuelMix");
            PieData pData = new PieData(dataSet);

            //get the chart




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setupPieChart() {
        //Populating a list of pie entries
      //  PieChart chart = (PieChart)findViewById;
        //chart.setData();
    }




    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.data.setText(this.dataParsed);
    }
}
