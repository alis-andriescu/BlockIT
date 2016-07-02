package com.example.alis.proiect;


public class PhoneNumber {

    private int _idNumber;
    private String _phoneNumber;


    public PhoneNumber(String phoneNumber) {
        this._phoneNumber = phoneNumber;
    }

    public int get_idNumber() {
        return _idNumber;
    }

    public String get_phoneNumber() {
        return _phoneNumber;
    }

    public void set_idNumber(int _idNumber) {
        this._idNumber = _idNumber;
    }

    public void set_phoneNumber(String phoneNumber) {
        this._phoneNumber = phoneNumber;
    }
}
