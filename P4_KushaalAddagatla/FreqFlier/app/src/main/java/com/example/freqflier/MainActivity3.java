package com.example.freqflier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        getSupportActionBar().setTitle("FrequentFlier");

        TextView textView7 = findViewById(R.id.textView7);
        TextView textView8 = findViewById(R.id.textView8);

        String pid = getIntent().getStringExtra("pid");

        textView7.setText("Flights");

        RequestQueue queue = Volley.newRequestQueue(MainActivity3.this);

        String urlFlights="http://10.0.2.2:8080/frequentflier/Flights.jsp?pid="+pid;
        StringRequest request3 = new StringRequest(Request.Method.GET, urlFlights, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String result=s.trim();
                String[] rows=result.split("#");
                textView8.append("Flight Id       Flight Miles       Destination"+"\n");
                textView8.append("-----------------------------------------------------------------"+"\n");
                for(int i=0;i<rows.length;i++){
                    String[] cols=rows[i].split(",");
                    textView8.append(cols[0]+"           "+cols[1]+"                  " +cols[2]+"\n");
                }
                textView8.append("-----------------------------------------------------------------"+"\n");
            }
        },null);
        queue.add(request3);
    }
}