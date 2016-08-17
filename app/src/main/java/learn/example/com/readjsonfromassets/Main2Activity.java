package learn.example.com.readjsonfromassets;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    Routes[] processedArray;

    JsonReader jsonReader;

    TextView text;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        text = (TextView) findViewById(R.id.textview);
        progress = (ProgressBar) findViewById(R.id.progress);

        AsyncClass asyncTask = new AsyncClass();
        asyncTask.execute();
    }

    class AsyncClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            processedArray = readJson();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray resultJsonArray = createNewJson(processedArray);
                progress.setVisibility(View.GONE);
                text.setText(resultJsonArray.toString());
                Log.d("ko", "FUCK YEAH " + resultJsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Log.d("ko", processedArray[0].getStopTitle_ru());
        }
    }

    private Routes[] readJson() {
        Routes[] data = null;
        try {

            Log.d("ko", "got to readJson");
            Gson gson = new Gson();

            jsonReader = new JsonReader(
                    new InputStreamReader(getAssets().open("routes_august.json")));
            data = gson.fromJson(jsonReader, Routes[].class);

            data[0].getStopTitle_ru();
        } catch (IOException e) {
            e.printStackTrace();

        }

        return data;
    }


    private JSONArray createNewJson(Routes[] array) throws JSONException {

        JSONArray routesJsonArray = new JSONArray();
        JSONObject firstJsonObject = new JSONObject();

        for (int i = 0; i < (array.length / 30) - 1; i++) {

            firstJsonObject.put("routeNumber", array[i].getRouteNumber());

            if (firstJsonObject.get("routeNumber").equals(array[i + 1].getRouteNumber())) {

                for (int j = i; j < (array.length / 30) - 1; j++) {
//                    String routeNumberNext = array[j + 1].getRouteNumber();
                    JSONArray trackJsonArray = new JSONArray();
                    JSONObject latlon = new JSONObject();
                    latlon.put("lat", array[j + 1].getLatitude());
                    latlon.put("lon", array[j + 1].getLongitude());
                    trackJsonArray.put(latlon);
                    firstJsonObject.put("track", trackJsonArray);


                }
            } else {
                routesJsonArray.put(firstJsonObject);
                continue;
            }


        }

        return routesJsonArray;
    }

}
