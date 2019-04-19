package com.example.helloworld.Model;

public class Barcode {

    private String barcode;
    private String codeitem;
    private int quantity;
    private String madeby;
    private int lock;
    private String id_loi;

    public Barcode(String barcode, String codeitem, String quantity, String madeby, String id_loi, int lock) {
        setBarcode(barcode);
        setCodeitem(codeitem);
        setId_loi(id_loi);
        setLock(lock);
        setMadeby(madeby);
        if (quantity.equals("null")) {
            setQuantity(0);
        } else {
            setQuantity(Integer.parseInt(quantity));
        }
    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCodeitem() {
        return codeitem;
    }

    public void setCodeitem(String codeitem) {
        this.codeitem = codeitem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMadeby() {
        return madeby;
    }

    public void setMadeby(String madeby) {
        this.madeby = madeby;
    }

    public int getLock() {
        return lock;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }

    public String getId_loi() {
        return id_loi;
    }

    public void setId_loi(String id_loi) {
        this.id_loi = id_loi;
    }
}
