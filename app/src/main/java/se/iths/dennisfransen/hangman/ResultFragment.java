package se.iths.dennisfransen.hangman;

import android.arch.lifecycle.Observer;
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
import android.widget.TextView;

public class ResultFragment extends Fragment {

    private FragmentViewModel mViewModel;
    private TextView resultTextView, triesLeftResultTextView, theWordResultTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(FragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        ImageButton playToolbarBtn;
        ImageButton infoToolbarBtn;

        triesLeftResultTextView = view.findViewById(R.id.triesLeftResultTextView);
        resultTextView = view.findViewById(R.id.resultTextView);
        final Observer<Integer> amountOfTriesObserver = new Observer<Integer>() {
            public void onChanged(@Nullable final Integer newAmountOfTries) {
                // Update the UI, in this case, a TextView.
                triesLeftResultTextView.setText(String.format(getResources().getString(R.string.tries_result), newAmountOfTries));
                if (newAmountOfTries > 0) {
                    resultTextView.setText(R.string.you_won);
                } else {
                    resultTextView.setText(R.string.you_lost);
                }
            }
        };
        mViewModel.getTries().observe(this, amountOfTriesObserver);

        theWordResultTextView = view.findViewById(R.id.theWordResultTextView);
        final Observer<String> chosenWordObserver = new Observer<String>() {
            public void onChanged(@Nullable final String newChosenWord) {
                // Update the UI, in this case, a TextView.
                theWordResultTextView.setText(String.format(getResources().getString(R.string.result_word), newChosenWord));
            }
        };
        mViewModel.getChosenWord().observe(this, chosenWordObserver);

        // Send player to main menu
        Button backToMainMenuBtn = view.findViewById(R.id.backToMainMenuBtn);
        backToMainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenuActivity();
            }
        });

        ImageButton goBackBtn = view.findViewById(R.id.goBackBtn);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenuActivity();
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
     * Activate MainFragment
     */
    public void openMainMenuActivity() {
        ((MainActivity) getActivity()).setViewPager(0);
    }
}
