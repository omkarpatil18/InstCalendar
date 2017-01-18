package com.bignerdranch.android.institutecalender;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class CalendarActivity extends Activity {
    ArrayList<String> calendarUrlArray = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        final TextView mOddYear1TextView = (TextView) findViewById(R.id.odd_year1_textView);
        final TextView mEvenYear1TextView = (TextView)findViewById(R.id.even_year1_textView);
        final TextView mOddYear2TextView = (TextView) findViewById(R.id.odd_year2_textView);
        final TextView mEvenYear2TextView = (TextView)findViewById(R.id.even_year2_textView);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("students.iitm.ac.in")
                .appendPath("studentsapp")
                .appendPath("calendar")
                .appendPath("cal.php");
        String url = builder.build().toString();

        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsArray = new JSONArray(response);

                    JSONObject jsObject0 = jsArray.getJSONObject(0);
                    String year1=jsObject0.getString("year");
                    mOddYear1TextView.setText("Jul-Nov "+year1);
                    mEvenYear1TextView.setText("Jan-May "+year1);
                    calendarUrlArray.add(jsObject0.getString("even"));
                    calendarUrlArray.add(jsObject0.getString("odd"));

                    JSONObject jsObject1 = jsArray.getJSONObject(1);
                    String year2=jsObject1.getString("year");
                    mOddYear2TextView.setText("Jul-Nov "+year2);
                    mEvenYear2TextView.setText("Jan-May "+year2);
                    calendarUrlArray.add(jsObject1.getString("even"));
                    calendarUrlArray.add(jsObject1.getString("odd"));


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CalendarActivity.this, "Couldn't connect to the server.", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyResponseError", error);
                Toast.makeText(CalendarActivity.this, "Couldn't connect to the server.", Toast.LENGTH_SHORT).show();

            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq);

        ImageButton mOddYear1ImageButton= (ImageButton)findViewById(R.id.odd_year1);
        ImageButton mEvenYear1ImageButton = (ImageButton)findViewById(R.id.even_year1);
        ImageButton mOddYear2ImageButton = (ImageButton)findViewById(R.id.odd_year2);
        ImageButton mEvenYear2ImageButton = (ImageButton)findViewById(R.id.even_year2);

        mOddYear1ImageButton.setTag(1);
        mEvenYear1ImageButton.setTag(0);
        mOddYear2ImageButton.setTag(3);
        mEvenYear2ImageButton.setTag(2);


        mOddYear1ImageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dummy_calendar));
        mEvenYear1ImageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dummy_calendar));
        mOddYear2ImageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dummy_calendar));
        mEvenYear2ImageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dummy_calendar));


        mOddYear1ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar((Integer)v.getTag());
            }
        });

        mEvenYear1ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar((Integer)v.getTag());
            }
        });

        mOddYear2ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar((Integer)v.getTag());
            }
        });

        mEvenYear2ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar((Integer)v.getTag());
            }
        });

    }
    public void showCalendar(int c){
        if(calendarUrlArray !=null){
            Intent intent = new Intent(this, CalendarDisplayActivity.class);
            intent.putExtra("calendar_url", calendarUrlArray.get(c));
            startActivity(intent);
        }

    }
}

