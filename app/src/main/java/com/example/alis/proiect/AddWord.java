package com.example.alis.proiect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddWord extends AppCompatActivity {

    EditText wordInput;
    TextView wordView;
    DBHandler dbHandlerW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        wordInput = (EditText) findViewById(R.id.wordInput);
        wordView = (TextView) findViewById(R.id.wordView);
        dbHandlerW = new DBHandler(this,null,null,1);
        printWordsDatabase();
    }

    public void printWordsDatabase(){
        String dbStringW = dbHandlerW.databaseWordsToString();
        wordView.setText(dbStringW);
        wordInput.setText("");
    }

    public void addWordClicked(View view){
        String word = wordInput.getText().toString();
        Word newWord = new Word(word);
        dbHandlerW.addWord(newWord);
        printWordsDatabase();
    }

    public void deleteWordClicked(View view){
        String inputText = wordInput.getText().toString();
        dbHandlerW.deleteWord(inputText);
        printWordsDatabase();
    }
    public void goBack(View view){
        Intent i = new Intent(this, BlockIt.class);
        startActivity(i);
    }
}
