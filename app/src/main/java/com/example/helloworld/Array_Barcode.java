package com.example.helloworld;

import com.example.helloworld.Model.Barcode;

import java.util.ArrayList;

public class Array_Barcode {

    static private ArrayList<Barcode> arr = new ArrayList<>();

    public static void setArr(ArrayList<Barcode> arr) {
        Array_Barcode.arr = arr;
    }

    public static ArrayList<Barcode> getArr() {
        return arr;
    }

}
