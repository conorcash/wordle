package com.techelevator.wordle;

import java.util.HashMap;
import java.util.Map;

public class Word {

    WordService wordService = new WordService();

    private final String WORD = wordService.generateWord().toUpperCase();
    private final int CHARACTER_COUNT = WORD.length();
    private final int MAX_ATTEMPTS = CHARACTER_COUNT + 1;
    private int attempt = 0;
    private final Map<String,Integer> solutionLetterMap = getCharacterCountMap(WORD);

    public enum CharacterResult {
        CORRECT,
        OUT_OF_POSITION,
        NOT_IN_WORD
    }

    public Word() {
    }

    public int getCHARACTER_COUNT() {
        return CHARACTER_COUNT;
    }

    public CharacterResult[] evaluateGuess(String guess) {
        CharacterResult[] results = new CharacterResult[guess.length()];
        Map<String, Integer> guessLetterMap = getCharacterCountMap(guess);
        for (int i = 0; i < guess.length(); i++) {
            String guessLetter = guess.substring(i, i + 1);
            String solutionLetter = WORD.substring(i, i + 1);
            int guessLetterCount = guessLetterMap.get(guessLetter);
            Integer solutionLetterCount = solutionLetterMap.get(guessLetter);

            if (guessLetter.equals(solutionLetter)) {
                solutionLetterMap.put(guessLetter, solutionLetterCount - 1);
                results[i] = CharacterResult.CORRECT;
            } else if (solutionLetterCount == null || guessLetterCount > solutionLetterCount) {
                results[i] = CharacterResult.NOT_IN_WORD;
            } else {
                results[i] = CharacterResult.OUT_OF_POSITION;
            }
            guessLetterMap.put(guessLetter,guessLetterCount - 1);
        }
        return results;
    }

    public Map<String, Integer> getCharacterCountMap(String str) {
        String letter = "";
        Map<String, Integer> characterCountMap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            letter = str.substring(i,i + 1);
            if (!characterCountMap.containsKey(letter)) {
                characterCountMap.put(letter, 1);
            } else {
                int letterCount = characterCountMap.get(letter);
                characterCountMap.put(letter, letterCount + 1);
            }
        }
        return characterCountMap;
    }

    public String getWORD () {
        return WORD;
    }

    public int getAttempt() {
        return attempt;
    }

    public void incrementAttempts () {
        attempt++;
    }

    public int getMAX_ATTEMPTS() {
        return MAX_ATTEMPTS;
    }

    public boolean validGuess(String guess) {
        return guess.length() == getCHARACTER_COUNT() && wordService.isWord(guess);
    }
}
