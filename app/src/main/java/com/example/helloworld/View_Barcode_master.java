package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.helloworld.Model.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class View_Barcode_master extends AppCompatActivity {

    private List<Barcode> list = new ArrayList<Barcode>();
    private ListView listView;
    private Adapter_barcode_master adapterBarcodemaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        System.out.println("Cekcek");

        listView = (ListView) findViewById(R.id.list);
        String url = "http://10.0.2.2:80/restserver/index.php/barcode_group";
        adapterBarcodemaster = new Adapter_barcode_master(this, list);
        listView.setAdapter(adapterBarcodemaster);

        JsonArrayRequest jsonreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Coba");
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Barcode dataSet = new Barcode(obj.getString("SBGM_BARCODE"),
                                        obj.getString("SBGM_ID"),
                                        obj.getString("SBGM_QTY_TOTAL"),
                                        obj.getString("SBGM_CREATE_BY"),
                                        obj.getString("SBGM_LOI_ID"),
                                        obj.getInt("SBGM_STATUS_LOCK"));
                                Log.d("Barcode", dataSet.getBarcode());
                                list.add(dataSet);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        adapterBarcodemaster.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.toString());
                        }
                    });
        MySingleton.getInstance(this).addToRequestQueue(jsonreq);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sbgm_barcode = list.get(position).getBarcode();
                String sbgm_loi = list.get(position).getId_loi();
                int sbgm_lock = list.get(position).getLock();
                Intent intent = new Intent(View_Barcode_master.this, View_Barcode_detail.class);
                intent.putExtra("barcode", sbgm_barcode);
                intent.putExtra("loi", sbgm_loi);
                intent.putExtra("lock", sbgm_lock);
                startActivity(intent);
                finish();

            }
        });
    }

    public void Add_new(View view) {
        Intent intent = new Intent(View_Barcode_master.this, View_Input_barcode_master.class);
        startActivity(intent);
        finish();
    }

    public void refresh(View view) {
        Intent intent = new Intent(View_Barcode_master.this, View_Barcode_master.class);
        startActivity(intent);
        finish();
    }
}

