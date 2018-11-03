package com.example.rickard.testfragment;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private MutableLiveData<String> buttonText;

    public MutableLiveData<String>getButtonText() {
        if (buttonText == null) {
            buttonText = new MutableLiveData<String>();
        }
        return buttonText;
    }

    public void setButtonText(String value) {
        buttonText.setValue(value);
    }
}
