package learn.example.com.readjsonfromassets;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class GottaDOActivity extends AppCompatActivity {

    String data;
    TextView textView;
    JSONArray resultJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gotta_do);

        textView = (TextView) findViewById(R.id.textview_gotta);
        data = readFromFile("example.json", getApplicationContext());
        resultJson = new JSONArray();

        As as = new As();
        as.execute();

    }

    class As extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            process();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ko", resultJson.toString());
            textView.setText(resultJson.toString());
        }
    }


    public void process() {
        try {
            JSONArray jsonArray = new JSONArray(data);

            JSONArray emptyJsonArray = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray trackJsonArray = new JSONArray();
                JSONArray busStopsJsonArray = new JSONArray();
                JSONObject newJson = new JSONObject();
                JSONObject firstJsonObject = jsonArray.getJSONObject(i);

                Double lat = (Double) firstJsonObject.get("latitude");
                Double lon = (Double) firstJsonObject.get("longitude");
                JSONObject firstTrackObject = new JSONObject();
                firstTrackObject.put("lat", lat);
                firstTrackObject.put("lon", lon);
                trackJsonArray.put(firstTrackObject);

                JSONObject stopsJsonObject = new JSONObject();

                //searching for busstops point
                if (firstJsonObject.get("isStopPoint").equals(1)) {
                    stopsJsonObject.put("id", firstJsonObject.get("stopID"));
                    stopsJsonObject.put("title", firstJsonObject.get("stopTitle"));
                    stopsJsonObject.put("x", firstJsonObject.get("stopLongitude"));
                    stopsJsonObject.put("y", firstJsonObject.get("stopLatitude"));
                    stopsJsonObject.put("desc", "");
                    stopsJsonObject.put("routes", emptyJsonArray);
                    busStopsJsonArray.put(stopsJsonObject);
                    newJson.put("busstops", busStopsJsonArray);
                }

                for (int j = i + 1; j < jsonArray.length(); j++) {

                    JSONObject secondJsonObject = jsonArray.getJSONObject(j);
                    if (firstJsonObject.get("routeNumber").equals(secondJsonObject.get("routeNumber"))) {
                        //if equal then create track array with lat lon

                        JSONObject secondTrackObject = new JSONObject();
                        Double lat2 = (Double) secondJsonObject.get("latitude");
                        Double lon2 = (Double) secondJsonObject.get("longitude");
                        secondTrackObject.put("lat", lat2);
                        secondTrackObject.put("lon", lon2);

                        trackJsonArray.put(secondTrackObject);

                        newJson.put("track", trackJsonArray);
                        newJson.put("n", firstJsonObject.get("routeNumber"));
                        newJson.put("id", firstJsonObject.get("id"));
                        newJson.put("d", "not used");




                        if (secondJsonObject.get("isStopPoint").equals(1)) {
                            stopsJsonObject.put("id", secondJsonObject.get("stopID"));
                            stopsJsonObject.put("title", secondJsonObject.get("stopTitle"));
                            stopsJsonObject.put("x", secondJsonObject.get("stopLongitude"));
                            stopsJsonObject.put("y", secondJsonObject.get("stopLatitude"));
                            stopsJsonObject.put("desc", "");
                            stopsJsonObject.put("routes", emptyJsonArray);
                            busStopsJsonArray.put(stopsJsonObject);
                            newJson.put("busstops", busStopsJsonArray);
                        }

                        i = j;

                    } else {
                        break;
                    }
                }
                resultJson.put(newJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static String readFromFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_PRIVATE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }
}
