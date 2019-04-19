package com.example.helloworld;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.helloworld.Model.BarcodeDetail;

import java.util.List;


/**
 * Created by Hatesh kumar on 11/07/2016.
 */

public class Adapter_barcode_detail extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<BarcodeDetail> DataList;
//    ImageLoader imageLoader = Controller.getPermission().getImageLoader();

    public Adapter_barcode_detail(Activity activity, List<BarcodeDetail> dataitem) {
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
            convertView = inflater.inflate(R.layout.list_barcode_detail, null);

//        if (imageLoader == null)
//            imageLoader = Controller.getPermission().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView name = (TextView) convertView.findViewById(R.id.txt_barcode);
        TextView worth = (TextView) convertView.findViewById(R.id.txt_detailofbarcode);
        TextView source = (TextView) convertView.findViewById(R.id.source);
        TextView year = (TextView) convertView.findViewById(R.id.inYear);
        BarcodeDetail m = DataList.get(position);
        name.setText(m.getBarcode());
//        source.setText("Wealth Source: " + String.valueOf(m.getLevel()));
//        worth.setText(String.valueOf(m.getLine()));
//        year.setText(String.valueOf(m.getSpace()));

        return convertView;
    }

}
