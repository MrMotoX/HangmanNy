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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PlayFragment extends Fragment {

    private EditText inputEditText;
    private TextView outputTextView, guessCharTextView, triesLeftTextView;
    ImageButton infoToolbarBtn;
    private ImageView img;

    private FragmentViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(FragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        // TODO: Add back button on Toolbar

        // Initialize number of remaining tries and pictures
        img = view.findViewById(R.id.hangmanImageView);
        img.setImageResource(R.drawable.hang10);

        // Initialize output fields
        outputTextView = view.findViewById(R.id.outputTextView);
        triesLeftTextView = view.findViewById(R.id.triesLeftTextView);
        guessCharTextView = view.findViewById(R.id.guessCharTextView);
        final Observer<Integer> resultObserver = new Observer<Integer>() {
            public void onChanged(@Nullable final Integer newCycle) {
                guessCharTextView.setText(mViewModel.getGuessedChars());
                outputTextView.setText(mViewModel.getResultString());
                triesLeftTextView.setText(String.format(getResources().getString(R.string.tries_left_text_view), mViewModel.getTriesInt()));
                int pic = getImg(mViewModel.getTriesInt());
                img.setImageResource(pic);
            }
        };
        mViewModel.getCycle().observe(this, resultObserver);

        Button guessButton = view.findViewById(R.id.guessButton);
        guessButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                guessCharTextView = getView().findViewById(R.id.guessCharTextView);
                inputEditText = getView().findViewById(R.id.inputEditText);
                ImageView img = getView().findViewById(R.id.hangmanImageView);

                if (inputEditText.getText().length() == 1) {
                    char inChar = inputEditText.getText().charAt(0);
                    inChar = Character.toUpperCase(inChar);

                    if (mViewModel.guessedAllCharsContainsChar(inChar)) {
                        Toast.makeText(getActivity(),
                                R.string.already_typed_letter, Toast.LENGTH_LONG).show();
                        inputEditText.setText("");
                    } else {
                        mViewModel.checkInputToChosenWord(inChar);

                        mViewModel.addGuessedAllChar(inChar);

                        inputEditText.setText("");

                        //Output result
                        if (mViewModel.isDone()) {
                            // Game over. Guess complete or out of tries
                            activateResultActivity();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(),
                            R.string.toManyCharErrorMsg, Toast.LENGTH_LONG).show();
                }
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
     * Gets an image corresponding to number of remaining guesses in the game.
     *
     * @param index number of false guesses
     * @return an index to the image in drawable resources
     */
    private int getImg(int index) {
        switch (index) {
            case 0:
                return R.drawable.hang0;
            case 1:
                return R.drawable.hang1;
            case 2:
                return R.drawable.hang2;
            case 3:
                return R.drawable.hang3;
            case 4:
                return R.drawable.hang4;
            case 5:
                return R.drawable.hang5;
            case 6:
                return R.drawable.hang6;
            case 7:
                return R.drawable.hang7;
            case 8:
                return R.drawable.hang8;
            case 9:
                return R.drawable.hang9;
            case 10:
                return R.drawable.hang10;
            default:
                return -1;
        }
    }

    /**
     * Activates ResultFragment with parameters
     */
    public void activateResultActivity() {
        // TODO Skicka data till ViewModel
        mViewModel.setChosenWord();

        ((MainActivity) getActivity()).setViewPager(2);
    }

}