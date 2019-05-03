package com.example.helloworld;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.helloworld.Model.Barcode;

import java.util.List;


/**
 * Created by Hatesh kumar on 11/07/2016.
 */

public class Adapter_barcode_master extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Barcode> DataList;
//    ImageLoader imageLoader = Controller.getPermission().getImageLoader();

    public Adapter_barcode_master(Activity activity, List<Barcode> dataitem) {
        this.activity = activity;
        this.DataList = dataitem;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public Object getItem(int location) {
        return DataList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_barcode_master, null);

//        if (imageLoader == null)
//            imageLoader = Controller.getPermission().getImageLoader();
//        NetworkImageView thumbNail = (NetworkImageView) convertView
//                .findViewById(R.id.thumbnail);
        TextView barcode = (TextView) convertView.findViewById(R.id.txt_barcodeid);
        TextView detailofbarcode = (TextView) convertView.findViewById(R.id.txt_quantity);
        TextView lock = (TextView) convertView.findViewById(R.id.txt_lock);
        Barcode m = DataList.get(position);
        String temp1 = "ID | Barcode: " + m.getCodeitem() + " | "+ m.getBarcode();
        String temp2 = "Made by | Quanttiy: " + m.getMadeby() + " / " + String.valueOf(m.getQuantity());
        String temp3;
        if (m.getLock() == 0) {
            temp3 = "Status: Unlock";
        } else {
            temp3 = "Status: Lock";
        }
        barcode.setText(temp1);
        detailofbarcode.setText(temp2);
        lock.setText(temp3);

        return convertView;
    }

}
