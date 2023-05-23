package com.example.prm392_project.Application;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    String data;
    // getter and setter methods

    public MyViewModel() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
