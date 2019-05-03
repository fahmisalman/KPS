package com.example.helloworld;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class View_Input_barcode_master extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void Add(View view) {
        final EditText barcode = (EditText) findViewById(R.id.barcode_txt);
        final EditText line = (EditText) findViewById(R.id.line_txt);
        final EditText level = (EditText) findViewById(R.id.level_txt);
        final EditText space = (EditText) findViewById(R.id.space_txt);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:80/restserver/index.php/barcode_group";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.d("JSON", obj.toString());

                            AlertDialog.Builder builder = new AlertDialog.Builder(View_Input_barcode_master.this);
                            builder.setTitle("Alert dialog demo !");
                            builder.setMessage("This is an alert dialog message");
                            builder.setCancelable(true);
                            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), "Neutral button clicked", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(View_Input_barcode_master.this, View_Barcode_master.class);
                                    startActivity(intent);
                                }
                            });

                            builder.show();


                        } catch (Throwable tx) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                        }

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
                params.put("SBGM_BARCODE_ORDER_DETAIL", barcode.getText().toString());
                params.put("SBGM_LINE", line.getText().toString());
                params.put("SBGM_LEVEL", level.getText().toString());
                params.put("SBGM_SPACE", space.getText().toString());
                params.put("username", Session.getUsername());

                return params;
            }
        };

        queue.add(postRequest);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(View_Input_barcode_master.this, View_Barcode_master.class);
        startActivity(intent);
        finish();
    }
}
