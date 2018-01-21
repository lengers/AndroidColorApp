package com.example.octavian.color1;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ftinc.scoop.Scoop;
import com.ftinc.scoop.BindTopping;
import com.ftinc.scoop.Topping;
import butterknife.BindView;
import butterknife.ButterKnife;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    JSONArray jsonArray;
    String url;
    Window window;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        url = "http://www.colourlovers.com/api/palettes/random?format=json";
        window = getWindow();
        activity = this;

        ((Button) findViewById(R.id.button14)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {
                            @SuppressLint("ResourceType")
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                try {
                                    jsonArray = new JSONArray(response);
                                    JSONObject obj = jsonArray.getJSONObject(0);
                                    JSONArray colors = obj.getJSONArray("colors"); //INKONSITENZ, wenn eure Webseite COLOURlovers heißt, dann heißt der Key hier gefälligst auch "colour"

                                    // set colors
                                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                    Log.e("COLORCODE", colors.get(0).toString());
                                    window.setStatusBarColor(Color.parseColor("#" + colors.get(0).toString()));

                                    ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
                                    //Log.e("THEME",);


                                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + colors.get(1).toString())));
                                    getSupportActionBar().setTitle((CharSequence) obj.get("title"));

                                    findViewById(R.id.button13).setBackgroundColor(Color.parseColor("#" + colors.get(2).toString()));

                                    findViewById(R.id.switch1).setBackgroundColor(Color.parseColor("#" + colors.get(2).toString()));
                                    findViewById(R.id.switch1).setDrawingCacheBackgroundColor(Color.parseColor("#" + colors.get(3).toString()));

                                    findViewById(R.id.checkBox).setBackgroundColor(Color.parseColor("#" + colors.get(4).toString()));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.e("Error.Response", error.toString());
                            }
                        }
                );
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
}
