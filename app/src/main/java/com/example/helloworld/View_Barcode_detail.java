package com.example.helloworld;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
    private int lock = 0;
    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        id_master = bundle.getString("barcode");
        id_loi = bundle.getString("loi");
        lock = bundle.getInt("lock");

        String url = "http://10.0.2.2:80/restserver/index.php/barcode_group_detail?id_master=" + id_master;
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
                                    BarcodeDetail dataSet = new BarcodeDetail(obj.getString("BARCODE"),
                                        obj.getString("KPS_LOI_ID_label"), obj.getInt("QUANTITY"),
                                        obj.getString("barcode_master_id")
                                    );
                                    quantity = quantity + obj.getInt("QUANTITY");
                                    list.add(dataSet);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            TextView quan = (TextView) findViewById(R.id.txt_quantity);
                            quan.setText("Total quantity: " + quantity);
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

        if (lock != 1){
            final EditText barcode_detail = (EditText) findViewById(R.id.txt_barcode_detail);

            String url = "http://10.0.2.2:80/restserver/index.php/barcode_group_detail";
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
                            Log.d("Error", "Wakwaw");
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    Log.d("Tag", id_master + barcode_detail.getText().toString() + Session.getUsername() + id_loi);
                    params.put("id_master", id_master);
                    params.put("SBGM_BARCODE_ORDER_DETAIL", barcode_detail.getText().toString());
                    params.put("username", Session.getUsername());
                    params.put("id_loi", id_loi);

                    return params;
                }
            };

            MySingleton.getInstance(this).addToRequestQueue(jsonreq);

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(View_Barcode_detail.this);
            builder.setTitle("Lock");
            builder.setMessage("Status barcode terkunci");
            builder.setCancelable(true);
            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(getApplicationContext(), "Neutral button clicked", Toast.LENGTH_SHORT).show();
                }
            });

            builder.show();

        }

//        queue.add(jsonreq);

    }

    public void remove_intent(View view) {

        if (lock != 1) {

            Intent intent = new Intent(View_Barcode_detail.this, View_Remove_form.class);
            intent.putExtra("barcode", id_master);
            startActivity(intent);
            finish();

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(View_Barcode_detail.this);
            builder.setTitle("Lock");
            builder.setMessage("Status barcode terkunci");
            builder.setCancelable(true);
            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(getApplicationContext(), "Neutral button clicked", Toast.LENGTH_SHORT).show();
                }
            });

            builder.show();

        }

    }

    public void submit(View view) {

        if (lock != 1) {

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
                            finish();

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
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

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(View_Barcode_detail.this);
            builder.setTitle("Lock");
            builder.setMessage("Status barcode terkunci");
            builder.setCancelable(true);
            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(getApplicationContext(), "Neutral button clicked", Toast.LENGTH_SHORT).show();
                }
            });

            builder.show();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(View_Barcode_detail.this, View_Barcode_master.class);
        startActivity(intent);
        finish();

    }
}
