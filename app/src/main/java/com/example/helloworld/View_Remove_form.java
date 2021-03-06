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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class View_Remove_form extends AppCompatActivity {

    private String id_master = "";
    private String id_loi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Bundle bundle = getIntent().getExtras();
        id_master = bundle.getString("barcode");
        id_loi = bundle.getString("loi");

    }

    public void remove(View view) {

        String url = "http://10.0.2.2:80/restserver/index.php/barcode_group_detail";
        final EditText barcode_text = (EditText) findViewById(R.id.txt_barcodeid);
        final StringRequest jsonreq = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Intent intent = new Intent(View_Remove_form.this, View_Barcode_master.class);
                        intent.putExtra("barcode", id_master);
                        intent.putExtra("loi", id_loi);
                        startActivity(intent);
                        finish();

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
                params.put("SBGM_BARCODE_ORDER_DETAIL", barcode_text.getText().toString());
                params.put("username", Session.getUsername());

                return params;
            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }

        };

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(View_Remove_form.this);
        alertDialogBuilder.setTitle("Remove barcode");
        alertDialogBuilder
                .setMessage("Apakah anda yakin untuk menghapus barcode "+ barcode_text.getText().toString() + "?")
//                                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        MySingleton.getInstance(View_Remove_form.this).addToRequestQueue(jsonreq);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
