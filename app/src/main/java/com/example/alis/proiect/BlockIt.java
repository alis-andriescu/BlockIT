package com.example.alis.proiect;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BlockIt extends AppCompatActivity {

    TextView tv;
    DBHandler dbhandler;
    BroadcastReceiver callBlocker;
    TelephonyManager telephonyManager;
    ITelephony telephonyService;
    String data = "";
    String contacts = "";
    String[] contactsArray;
    String[] noArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhandler = new DBHandler(this, null, null, 1);
        String numbers = dbhandler.databaseNumbersToString();
        noArray = numbers.split("\n");
        getContactsList();
        contactsArray = contacts.split(",");

        blockCalls();
    }

    public void onClickAddNumber(View view) {

        Intent i = new Intent(this, AddNumber.class);
        startActivity(i);
    }

    public void onClickAddWord(View view) {
        Intent i = new Intent(this, AddWord.class);
        startActivity(i);
    }

    public void onClickViewHistory(View view) {
        Intent i = new Intent(this, ViewHistory.class);
        startActivity(i);
    }

    public void onClickInstructions(View view){
        Intent i = new Intent(this,Instructions.class);
        startActivity(i);
    }

    public void onClickAbout(View view){
        Intent i = new Intent(this,About.class);
        startActivity(i);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onClickExit(View view){
        finishAffinity();
        System.exit(0);

    }
    public void blockCalls() {

        callBlocker = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);


                Class c = null;
                try {
                    c = Class.forName(telephonyManager.getClass().getName());
                } catch (ClassNotFoundException e) {

                    e.printStackTrace();
                }
                Method m = null;
                try {
                    m = c.getDeclaredMethod("getITelephony");
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                m.setAccessible(true);
                try {
                    telephonyService = (ITelephony) m.invoke(telephonyManager);
                } catch (IllegalArgumentException e) {

                    e.printStackTrace();
                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                } catch (InvocationTargetException e) {

                    e.printStackTrace();
                }
                telephonyManager.listen(callBlockListener, PhoneStateListener.LISTEN_CALL_STATE);
            }


            PhoneStateListener callBlockListener = new PhoneStateListener() {
                public void onCallStateChanged(int state, String incomingNumber) {


                    if (state == TelephonyManager.CALL_STATE_RINGING) {

                        String numbers = dbhandler.databaseNumbersToString();
                        noArray = numbers.split("\n");
                            int d = 0;
                            if (incomingNumber == null) {
                                try {
                                    telephonyService.endCall();
                                    data = "Call from number: Unknown";
                                    Data newData = new Data(data);
                                    dbhandler.addData(newData);

                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }else {

                                for (int i = 0; i < noArray.length; i++) {
                                    if (incomingNumber.equalsIgnoreCase(noArray[i])) {
                                        try {

                                            telephonyService.endCall();
                                            //tv.setText("teext");
                                            data = "Call from blocked number: " + incomingNumber;
                                            Data newData = new Data(data);
                                            dbhandler.addData(newData);

                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                }


                                for (int j = 0; j < contactsArray.length; j++) {
                                    if ((incomingNumber.equalsIgnoreCase(contactsArray[j]))) {

                                        d = 1;
                                        break;
                                    }
                                }
                                if (d == 0) {
                                    try {
                                        telephonyService.endCall();
                                        data = "Call from number not in Contacts: " + incomingNumber ;
                                        Data newData = new Data(data);
                                        dbhandler.addData(newData);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

            };
        };
        IntentFilter filter = new IntentFilter("android.intent.action.PHONE_STATE");
        registerReceiver(callBlocker, filter);
    }


    public void getContactsList() {

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    if (Integer.parseInt(cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor cursor1 = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (cursor1.moveToNext()) {

                            int phoneType = cursor1.getInt(cursor1.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.TYPE));
                            String phoneNumber = cursor1.getString(cursor1.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            switch (phoneType) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    contacts += phoneNumber + ",";
                                    dbhandler.addContact(phoneNumber);
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    contacts += phoneNumber + ",";
                                    dbhandler.addContact(phoneNumber);
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    contacts += phoneNumber + ",";
                                    dbhandler.addContact(phoneNumber);
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                                    contacts += phoneNumber + ",";
                                    dbhandler.addContact(phoneNumber);
                                    break;
                                default:
                                    break;

                            }
                        }
                        cursor1.close();
                    }
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (callBlocker != null) {
            unregisterReceiver(callBlocker);
            callBlocker = null;
        }
    }
}
