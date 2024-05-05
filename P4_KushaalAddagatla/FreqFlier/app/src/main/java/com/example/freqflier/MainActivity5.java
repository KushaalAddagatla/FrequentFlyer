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

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        getSupportActionBar().setTitle("FrequentFlier");

        TextView textView14 = findViewById(R.id.textView14);
        TextView textView15 = findViewById(R.id.textView15);
        TextView textView16 = findViewById(R.id.textView16);
        TextView textView17 = findViewById(R.id.textView17);
        TextView textView18 = findViewById(R.id.textView18);
        TextView textView19 = findViewById(R.id.textView19);
        TextView textView20 = findViewById(R.id.textView20);

        Spinner spinner2 = findViewById(R.id.spinner2);

        String pid = getIntent().getStringExtra("pid");

        textView14.setText("Select Award");

        RequestQueue queue6 = Volley.newRequestQueue(this);
        String urlAwardIds ="http://10.0.2.2:8080/frequentflier/AwardIds.jsp?pid="+pid;
        ArrayList<String> list = new ArrayList<String>();

        StringRequest request6 = new StringRequest(Request.Method.GET, urlAwardIds, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String result=s.trim();
                String[] ids =result.split("#");
                for(int i=0;i<ids.length;i++){
                    list.add(ids[i]);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity5.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                spinner2.setAdapter(adapter);
            }
        },null);

        queue6.add(request6);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String awardid = parent.getSelectedItem().toString();
                String urlRedemptionDetails ="http://10.0.2.2:8080/frequentflier/RedemptionDetails.jsp?awardid="+awardid+"&pid="+pid;
                StringRequest request7 = new StringRequest(Request.Method.GET, urlRedemptionDetails, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String results = s.trim();
                        textView15.setText("Prize Desc.");
                        textView17.setText("Points Needed");
                        textView19.setText("Redemption Date          Exchange Center\n---------------------------------------------------------------------------");

                        String[] rows =results.split("#");
                        String[] cols =rows[0].split(",");
                        textView16.setText(cols[0]);
                        textView18.setText(cols[1]+" Points");

                        textView20.setText("");
                        for (int i =0; i< rows.length; i++){
                            String[] columns = rows[i].split(",");
                            for(int j=2;j< cols.length-1; j++) {
                                textView20.append(columns[j]+"                      "+columns[j+1]);
                            }
                            textView20.append("\n");
                        }
                    }
                },null);
                queue6.add(request7);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}