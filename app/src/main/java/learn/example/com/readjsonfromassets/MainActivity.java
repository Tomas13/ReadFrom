package learn.example.com.readjsonfromassets;

import android.content.Context;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            JsonReader jsonReader = new JsonReader(
                    new InputStreamReader(getAssets().open("routes_august.json")));
            Gson gson = new Gson();

            Routes r = gson.fromJson(jsonReader, Routes.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
