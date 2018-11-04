package se.iths.dennisfransen.hangman;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class MainFragment extends Fragment {

    private FragmentViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(FragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button playButton;
        Button aboutButton;
        Button settingsButton;
        ImageButton infoToolbarBtn;
        ImageButton playToolbarBtn;

        playButton = view.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayActivity();
            }
        });

        aboutButton = view.findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutActivity();
            }
        });

        settingsButton = view.findViewById(R.id.settingsBtn);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });

        playToolbarBtn = view.findViewById(R.id.playToolbarBtn);
        playToolbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayActivity();
            }
        });

        infoToolbarBtn = view.findViewById(R.id.infoToolbarBtn);
        infoToolbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutActivity();
            }
        });

        return view;
    }

    /**
     * Activate AboutActivity
     */
    public void openAboutActivity() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    /**
     * Activate PlayFragment
     */
    public void openPlayActivity() {
        mViewModel.initTries();

        Resources res = getResources();
        mViewModel.initWordArray(res.getStringArray(R.array.guessWords));
        mViewModel.initResultCharArray();

        ((MainActivity) getActivity()).setViewPager(1);
    }

    /**
     * Activate SettingsActivity
     */
    public void openSettingsActivity() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }
}
