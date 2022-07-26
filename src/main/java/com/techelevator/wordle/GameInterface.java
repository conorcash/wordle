package com.techelevator.wordle;

import java.util.Scanner;

public class GameInterface {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        Word word = new Word();
        final int MAX_ATTEMPTS = word.getMAX_ATTEMPTS();
        Scanner userInput = new Scanner(System.in);
        boolean tryAgain = true;

        while (word.getAttempt() < MAX_ATTEMPTS && tryAgain) {
            String guess = userInput.nextLine().toUpperCase();
            if (!word.validGuess(guess)) {
                System.out.println("Invalid guess.");
                continue;
            }
            tryAgain = false;
            word.incrementAttempts();
            String guessReturn = "";
            Word.CharacterResult[] results = word.evaluateGuess(guess);

            for (int i = 0; i < word.getCHARACTER_COUNT(); i++) {
                String checkLetter = guess.substring(i,i + 1);
                if (results[i] == Word.CharacterResult.CORRECT) {
                    guessReturn += ANSI_GREEN + checkLetter + ANSI_RESET;
                } else if (results[i] == Word.CharacterResult.OUT_OF_POSITION) {
                    tryAgain = true;
                    guessReturn += ANSI_YELLOW + checkLetter + ANSI_RESET;
                } else if (results[i] == Word.CharacterResult.NOT_IN_WORD) {
                    tryAgain = true;
                    guessReturn += checkLetter;
                }
            }
            System.out.println(guessReturn);
        }
        System.out.println(word.getWORD());
    }
}
