package se.iths.dennisfransen.hangman;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class PlayFragment extends Fragment {

    private EditText inputEditText;
    private TextView outputTextView, guessCharTextView, triesLeftTextView;
    ImageButton infoToolbarBtn, goBackToolBarBtn;
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



        // Initialize number of remaining tries and pictures
        img = view.findViewById(R.id.hangmanImageView);

        // Initialize output fields
        outputTextView = view.findViewById(R.id.outputTextView);
        triesLeftTextView = view.findViewById(R.id.triesLeftTextView);
        guessCharTextView = view.findViewById(R.id.guessCharTextView);
        final Observer<Integer> resultObserver = new Observer<Integer>() {
            public void onChanged(@Nullable final Integer newCycle) {
                guessCharTextView.setText(mViewModel.getGuessedChars());
                outputTextView.setText(mViewModel.getResultString());
                triesLeftTextView.setText(String.format(getResources().getString(R.string.tries_left_text_view), mViewModel.getTriesInt()));
                Picasso.get().load(getImg(mViewModel.getTriesInt())).into(img);
            }
        };
        mViewModel.getCycle().observe(this, resultObserver);

        Button guessButton = view.findViewById(R.id.guessButton);
        guessButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
                            activateResultFragment();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(),
                            R.string.toManyCharErrorMsg, Toast.LENGTH_LONG).show();
                }
            }
        });


        // onClick -> fragment_main
        goBackToolBarBtn = view.findViewById(R.id.backToolBarBtn);
        goBackToolBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateMainFragment();
            }
        });

        // onClick -> activity_about
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
    private String getImg(int index) {
        return "https://raw.githubusercontent.com/dennisfransen/HangmanNy/master/app/src/main/res/drawable-v24/hang" + index + ".gif";
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    /**
     * Activates ResultFragment with parameters
     */
    public void activateResultFragment() {
        mViewModel.setChosenWord();

        ((MainActivity) getActivity()).setViewPager(2);
    }

    public void activateMainFragment() {
        mViewModel.setChosenWord();

        ((MainActivity) getActivity()).setViewPager(0);
    }

}
