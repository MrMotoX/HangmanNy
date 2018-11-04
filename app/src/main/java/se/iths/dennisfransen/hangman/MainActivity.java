package se.iths.dennisfransen.hangman;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PlayStatePagerAdapter mSectionStatePageAdapter = new PlayStatePagerAdapter(getSupportFragmentManager());
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        PlayStatePagerAdapter adapter = new PlayStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(), "MainFragment");
        adapter.addFragment(new PlayFragment(), "PlayFragment");
        adapter.addFragment(new ResultFragment(), "ResultFragment");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        mViewPager.setCurrentItem(fragmentNumber);
    }

}
