package com.example.alis.proiect;


public class Data {
    private int _idData;
    private String _data;

    public void Data(){
    }

    public Data(String _data) {
        this._data = _data;
    }

    public int get_idData() {
        return _idData;
    }

    public String get_data() {
        return _data;
    }

    public void set_idData(int _idData) {
        this._idData = _idData;
    }

    public void set_data(String data) {
        this._data = data;
    }
}
