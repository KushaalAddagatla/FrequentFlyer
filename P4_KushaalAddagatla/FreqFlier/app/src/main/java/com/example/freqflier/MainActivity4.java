package com.example.freqflier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        getSupportActionBar().setTitle("FrequentFlier");

        TextView textView9 = findViewById(R.id.textView9);
        TextView textView10 = findViewById(R.id.textView10);
        TextView textView11 = findViewById(R.id.textView11);
        TextView textView12 = findViewById(R.id.textView12);
        TextView textView13 = findViewById(R.id.textView13);
        Spinner spinner = findViewById(R.id.spinner);

        String pid = getIntent().getStringExtra("pid");

        textView9.setText("Select Flight");

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlFlights="http://10.0.2.2:8080/frequentflier/Flights.jsp?pid="+pid;
        ArrayList<String> list = new ArrayList<String>();

        StringRequest request4 = new StringRequest(Request.Method.GET, urlFlights, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String result=s.trim();
                String[] ids =result.split("#");
                for(int i=0;i<ids.length;i++){
                    list.add(ids[i].substring(0,5));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity4.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                spinner.setAdapter(adapter);
            }
        },null);

        queue.add(request4);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String flightid = parent.getSelectedItem().toString();
                String urlFlightDetails ="http://10.0.2.2:8080/frequentflier/FlightDetails.jsp?flightid="+flightid;
                StringRequest request5 = new StringRequest(Request.Method.GET, urlFlightDetails, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String results = s.trim();
                        textView10.setText("Departure : \nArrival : \nMiles : \n");
                        textView11.setText("Trip Id                       Trip Miles \n----------------------------------------------------------\n");

                        String[] rows =results.split("#");
                        String[] cols =rows[0].split(",");

                        textView12.setText(cols[0] +"\n" + cols [1] + "\n" +cols[2]);
                        textView13.setText("");
                        for (int i =0; i< rows.length; i++){
                            String[] columns = rows[i].split(",");
                            for(int j=3;j< cols.length-1; j++) {
                                textView13.append(columns[j]+"                         "+columns[j+1]);
                            }
                            textView13.append("\n");
                        }
                    }
                },null);
                queue.add(request5);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}