package com.example.alis.proiect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class BlockSMS extends BroadcastReceiver {

    DBHandler db;
    String data = "";
    String contacts = "";
    String[] contactsArray;
    String[] noArray;
    String[] wordsArray;
    public static final String SMS_EXTRA_NAME = "pdus";
    private String number = "";
    private String message = "";

    public void onReceive(Context context, Intent intent) {
        int s = 0;
        int d = 0;
        int c = 0;
        db = new DBHandler(context, null, null, 1);
        contacts = db.databaseContactsToString();
        contactsArray = contacts.split("\n");
        String numbers = db.databaseNumbersToString();
        noArray = numbers.split("\n");
        String words = db.databaseWordsToString();
        wordsArray = words.split("\n");


        Bundle extras = intent.getExtras();


        if (extras != null) {

            Object[] smsExtra = (Object[]) extras.get(SMS_EXTRA_NAME);


            for (int i = 0; i < smsExtra.length; ++i) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);

                message = sms.getMessageBody().toString();
                number = sms.getOriginatingAddress();
            }


            String[] wordsInMessage = message.split(" ");


            for (int j = 0; j < noArray.length; j++) {
                String newNo = "+4" + noArray[j];
                if ((newNo.equals(number)) || (number == null)) {
                    s = 1;
                }
            }

            for (int k = 0; k < wordsArray.length; k++) {
                for (int l = 0; l < wordsInMessage.length; l++) {
                    if ((wordsArray[k].toLowerCase().equals(wordsInMessage[l].toLowerCase()))) {
                        d = 1;
                    }
                }
            }
            for (int ci = 0; ci < contactsArray.length; ci++) {
                String newc = "+4" + contactsArray[ci];
                if (number.equals(newc)) {
                    c = 1;
                }
            }
        }


        if (s == 1) {
            this.abortBroadcast();
            Toast.makeText(context, "mesaj blocat", Toast.LENGTH_LONG).show();
            data = "Message from blocked number: " + number + "\n" + message;
            Data newData = new Data(data);
            db.addData(newData);

        }

        if (d == 1) {
            this.abortBroadcast();
            Toast.makeText(context, "mesaj blocat", Toast.LENGTH_LONG).show();
            data = "Message blocked from number: " + number + "\n" + message;
            Data newData = new Data(data);
            db.addData(newData);

        }
        if (c == 0) {
            this.abortBroadcast();
            Toast.makeText(context, "mesaj blocat", Toast.LENGTH_LONG).show();
            data = "Message from blocked number: " + number + "\n" + message;
            Data newData = new Data(data);
            db.addData(newData);
        }
    }
}