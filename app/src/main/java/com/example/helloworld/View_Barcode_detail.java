package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.helloworld.Model.BarcodeDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View_Barcode_detail extends AppCompatActivity {

    private List<BarcodeDetail> list = new ArrayList<>();
    private ListView listView;
    private Adapter_barcode_detail adapter_barcode_detail;
    private String id_master = "";
    private String id_loi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        id_master = bundle.getString("barcode");
        id_loi = bundle.getString("loi");

        String url = "http://10.0.2.2:80/restserver/index.php/barcode_group_detail?id_master=" + id_master;
        RequestQueue queue = Volley.newRequestQueue(this);
        listView = (ListView) findViewById(R.id.list_detail);
        adapter_barcode_detail = new Adapter_barcode_detail(this, list);
        listView.setAdapter(adapter_barcode_detail);

        StringRequest jsonreq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray j = new JSONArray(response);

                            for (int i = 0; i < j.length(); i++) {
                                try {
                                    JSONObject obj = j.getJSONObject(i);
                                    BarcodeDetail dataSet = new BarcodeDetail(obj.getString("barcode_master_id"),
                                        obj.getString("KPS_LOI_ID_label"), obj.getInt("QUANTITY")
                                    );
                                list.add(dataSet);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            adapter_barcode_detail.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(jsonreq);

    }

    public void tambah(View view) {

        final EditText barcode_detail = (EditText) findViewById(R.id.txt_barcode_detail);

        String url = "http://10.0.2.2:80/restserver/index.php/barcode_group_detail";
//        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest jsonreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);
                        adapter_barcode_detail.notifyDataSetChanged();

                        Intent intent = new Intent(View_Barcode_detail.this, View_Barcode_detail.class);
                        intent.putExtra("barcode", id_master);
                        intent.putExtra("loi", id_loi);
                        startActivity(intent);

                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id_master", id_master);
                params.put("SBGM_BARCODE_ORDER_DETAIL", barcode_detail.getText().toString());
                params.put("username", Session.getUsername());
                params.put("id_loi", id_loi);

                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(jsonreq);
//        queue.add(jsonreq);

    }

    public void remove_intent(View view) {

        Intent intent = new Intent(View_Barcode_detail.this, Remove_form.class);
        intent.putExtra("barcode", id_master);
        startActivity(intent);

    }

    public void submit(View view) {

        String url = "http://10.0.2.2:80/restserver/index.php/barcode_group";
        StringRequest jsonreq = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);

                        Intent intent = new Intent(View_Barcode_detail.this, View_Barcode_master.class);
                        intent.putExtra("barcode", id_master);
                        intent.putExtra("loi", id_loi);
                        startActivity(intent);

                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id_master", id_master);

                return params;
            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }

        };

        MySingleton.getInstance(this).addToRequestQueue(jsonreq);


    }
}
