package com.example.alis.proiect;


public class Word {
    private int _idWord;
    private String _word;

    public Word(String word) {
        this._word = word;
    }

    public int get_idWord() {
        return _idWord;
    }

    public String get_word() {
        return _word;
    }

    public void set_idWord(int _idWord) {
        this._idWord = _idWord;
    }

    public void set_word(String word) {
        this._word = word;
    }
}
