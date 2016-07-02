package com.example.alis.proiect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ViewHistory extends AppCompatActivity {
    TextView historyView;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        historyView = (TextView) findViewById(R.id.historyView);
        dbHandler = new DBHandler(this, null, null, 1);
        printDataDatabase();
    }

    public void goBack(View view) {
        Intent i = new Intent(this, BlockIt.class);
        startActivity(i);
    }

    public void clearHistory(View view) {
        dbHandler.deleteHistory();
        printDataDatabase();
    }

    public void printDataDatabase() {
        String dbStringW = dbHandler.databaseDataToString();
        historyView.setText(dbStringW);

    }

}
