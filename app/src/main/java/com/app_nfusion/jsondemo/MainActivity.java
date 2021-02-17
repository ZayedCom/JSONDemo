package com.app_nfusion.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;
import org.jsoup.Jsoup;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    JSONObject jsonObject = new JSONObject();
    JSONObject dogObject = new JSONObject();

    @SuppressLint("StaticFieldLeak")
    void getData() {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onPreExecute() {
                //swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    String jsonString = Jsoup.connect("https://dog.ceo/api/breeds/list/all").ignoreContentType(true).timeout(10000).get().text();
                    Log.e("x", jsonString);

                    jsonObject = new JSONObject(jsonString);
                    dogObject = jsonObject.getJSONObject("message");

                    return "ok";
                } catch (Exception e) {
                    Log.e("x", "WS Parse Ex : " + e);

                    return e.toString();
                }
            }

            @Override
            protected void onPostExecute(Object o) {
                //swipeRefreshLayout.setRefreshing(false);

                try {
                    textView.setText(dogObject.getJSONArray("australian").toString());
                } catch (Exception e) {
                    Log.e("x", "Item Parse Ex : " + e);
                }
            }
        }.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.jsonText);

        getData();
    }
}
