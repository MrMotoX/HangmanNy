package se.iths.dennisfransen.hangman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Theme.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.defaultThemeBtn).setOnClickListener(this);
        findViewById(R.id.halloweenThemeBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.defaultThemeBtn:
                Theme.changeToTheme(this, Theme.THEME_DEFAULT);
                break;
            case R.id.halloweenThemeBtn:
                Theme.changeToTheme(this, Theme.THEME_HALLOWEEN);
                break;
        }
    }

}



