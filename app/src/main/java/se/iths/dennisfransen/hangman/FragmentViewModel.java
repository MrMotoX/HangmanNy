package se.iths.dennisfransen.hangman;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Random;

public class FragmentViewModel extends ViewModel {
    private MutableLiveData<Integer> amountOfTriesText;
    private MutableLiveData<String> chosenWordText;

    private Random rand = new Random();
    private String[] wordStringArray;
    private char[] chosenWordArray;
    private String guessedAllChars;
    private String guessedChars;
    private MutableLiveData<String> resultCharArray;

    private MutableLiveData<Integer> cycle;

    public FragmentViewModel() {
        cycle = new MutableLiveData<>();
        cycle.setValue(0);
        resultCharArray = new MutableLiveData<>();
        chosenWordText = new MutableLiveData<>();
        this.initTries();
        initWordArray(new String[]{});
    }

    public void initWordArray(String[] words) {
        wordStringArray = words;
        chosenWordArray = pickWord(wordStringArray);
        guessedAllChars = "";
        guessedChars = "";
        incCycle();
    }

    private char[] pickWord(String[] wordArray) {
        if (wordArray.length > 0) {
            return wordArray[rand.nextInt(wordArray.length)].toUpperCase().toCharArray();
        }
        incCycle();

        return new char[] {};
    }

    public void initResultCharArray() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < chosenWordArray.length; i++) s.append('-');
        resultCharArray.setValue(s.toString());
        incCycle();
    }

    public MutableLiveData<String> getResultCharArray() {
        return resultCharArray;
    }

    public String getResultString() {
        return resultCharArray.getValue();
    }

    public void setChosenWord() {
        chosenWordText.setValue(new String(chosenWordArray));
        incCycle();
    }

    public MutableLiveData<String> getChosenWord() {
        return chosenWordText;
    }

    public void checkInputToChosenWord(char inChar) {
        Boolean fault = true;
        char[] resultNewCharArray = resultCharArray.getValue().toCharArray();
        for (int i = 0; i < chosenWordArray.length; i++) {
            if (inChar == chosenWordArray[i]) {
                resultNewCharArray[i] = inChar;
                fault = false;
            }
        }

        if (fault) {
            addGuessedChar(inChar);
            decTries();
        }

        resultCharArray.setValue(new String(resultNewCharArray));
        incCycle();
    }

    private void addGuessedChar(char value) {
        guessedChars += value;
        incCycle();
    }

    public String getGuessedChars() {
        return guessedChars;
    }

    public void addGuessedAllChar(char value) {
        guessedAllChars += value;
        incCycle();
    }

    public Boolean guessedAllCharsContainsChar(char value) {
        incCycle();
        return guessedAllChars.indexOf(value) > -1;
    }

//    public MutableLiveData<Boolean> getResult() {
//        MutableLiveData<Boolean> result = new MutableLiveData<>();
//        result.setValue(amountOfTriesText.getValue() > 0);
//        return result;
//    }

    public void decTries() {
        if (amountOfTriesText.getValue() > 0) {
            amountOfTriesText.setValue(amountOfTriesText.getValue() - 1);
        }
        incCycle();
    }

    public void initTries() {
        if (amountOfTriesText == null) {
            amountOfTriesText = new MutableLiveData<>();
        }
        amountOfTriesText.setValue(10);
        incCycle();
    }

    public MutableLiveData<Integer> getTries() {
        return amountOfTriesText;
    }

    public Boolean isDone() {
        return !String.valueOf(resultCharArray.getValue().toCharArray()).contains("-") || getTriesInt() < 1;
    }

    public Integer getTriesInt() {
        return amountOfTriesText.getValue();
    }

    private void incCycle() {
        cycle.setValue(cycle.getValue() + 1);
    }

    public MutableLiveData<Integer> getCycle() {
        return cycle;
    }
}