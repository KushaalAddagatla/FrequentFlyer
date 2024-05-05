package com.example.freqflier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        getSupportActionBar().setTitle("FrequentFlier");

        TextView textView21 =  findViewById(R.id.textView21);
        TextView textView22 = findViewById(R.id.textView22);
        EditText editText4 = findViewById(R.id.editText4);
        Button button7 = findViewById(R.id.button7);
        Spinner spinner3 = findViewById(R.id.spinner3);

        String pid = getIntent().getStringExtra("pid");

        textView21.setText("Select Passenger Id");

        RequestQueue queue8 = Volley.newRequestQueue(MainActivity6.this);

        String urlPassengerIds ="http://10.0.2.2:8080/frequentflier/GetPassengerids.jsp?pid="+pid;
        ArrayList<String> list = new ArrayList<String>();
        StringRequest request8 = new StringRequest(Request.Method.GET, urlPassengerIds, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String result=s.trim();
                String[] ids =result.split("#");
                for(int i=0;i<ids.length;i++){
                    list.add(ids[i]);
                }
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(MainActivity6.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                spinner3.setAdapter(adapter3);
            }
        },null);
        queue8.add(request8);

        textView22.setText("Points to Transfer: ");

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dpid = parent.getSelectedItem().toString();

                button7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String npid= editText4.getText().toString();
                        String urlTransferPoints ="http://10.0.2.2:8080/frequentflier/TransferPoints.jsp?spid="+pid+"&dpid="+dpid+"&npoints="+npid;
                        StringRequest request9=new StringRequest(Request.Method.GET, urlTransferPoints, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                String result = s.trim();
                                 if(result.contains("Points transfer successful")){
                                    Toast.makeText(MainActivity6.this,npid+" Points Transferred Successfully", Toast.LENGTH_LONG).show();
                                }
                                 else{
                                     Toast.makeText(MainActivity6.this,"Insufficient Points in Account Balance", Toast.LENGTH_LONG).show();
                                 }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                            }
                        });
                        queue8.add(request9);
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}