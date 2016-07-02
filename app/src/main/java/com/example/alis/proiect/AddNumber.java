package com.example.alis.proiect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddNumber extends AppCompatActivity {

    EditText noInput;
    TextView numberView;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);
        noInput = (EditText) findViewById(R.id.noInput);
        numberView = (TextView) findViewById(R.id.numberView);
        dbHandler = new DBHandler(this,null,null,1);
        printNumbersDatabase();
    }

    public void printNumbersDatabase(){
        String dbString = dbHandler.databaseNumbersToString();
        numberView.setText(dbString);
        noInput.setText("");
    }

    public void addNumberClicked(View view){
        String phoneNumber = noInput.getText().toString();
        PhoneNumber newNumber = new PhoneNumber(phoneNumber);
        dbHandler.addNumber(newNumber);
        printNumbersDatabase();
    }

    public void deleteNumberClicked(View view){
        String inputText = noInput.getText().toString();
        dbHandler.deleteNumber(inputText);
        printNumbersDatabase();
    }

    public void goBack(View view){
        Intent i = new Intent(this, BlockIt.class);
        startActivity(i);
    }
}
