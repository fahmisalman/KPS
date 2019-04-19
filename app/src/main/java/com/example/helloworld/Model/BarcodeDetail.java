package com.example.helloworld.Model;

public class BarcodeDetail {

    private String barcode;
    private String id_loi;
    private int quantity;

    public BarcodeDetail(String barcode, String id_loi, int quan) {
        this.setBarcode(barcode);
        this.setId_loi(id_loi);
        this.setQuantity(quan);
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }


    public String getId_loi() {
        return id_loi;
    }

    public void setId_loi(String id_loi) {
        this.id_loi = id_loi;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
