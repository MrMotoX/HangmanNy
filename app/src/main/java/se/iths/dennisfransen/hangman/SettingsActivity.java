package se.iths.dennisfransen.hangman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.defaultThemeBtn).setOnClickListener(this);
        findViewById(R.id.halloweenThemeBtn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.defaultThemeBtn:
                Utils.changeToTheme(this, Utils.THEME_DEFAULT);
                break;
            case R.id.halloweenThemeBtn:
                Utils.changeToTheme(this, Utils.THEME_HALLOWEEN);
                break;
        }
    }
}



