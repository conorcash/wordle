package com.techelevator.wordle;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


public class WordService {
    public static final String GENERATE_WORD_API_URL = "https://random-word-api.herokuapp.com/word?length=5";
    public static final String CHECK_WORD_API_URL_ROOT = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    private final RestTemplate restTemplate = new RestTemplate();

    public String generateWord () {
        boolean validWord = false;
        String word = "";
        while (!validWord) {
            word = restTemplate.getForObject(GENERATE_WORD_API_URL,String.class);
            word = word.substring(2,word.length() - 2);
            validWord = isSingular(word) && isWord(word);
        }
        return word;
    }

    public boolean isWord (String word) {
        String apiUrl = CHECK_WORD_API_URL_ROOT + word;
        String apiResponse = "";
        try {
            apiResponse = restTemplate.getForObject(apiUrl, String.class);
        } catch (HttpClientErrorException e) {
            return false;
        }
        return !apiResponse.contains("Sorry pal, we couldn't find definitions for the word you were looking for.");
    }

    private boolean isSingular (String word) {
        if (!word.endsWith("S")) {
            return true;
        } else if (word.endsWith("IES")) {
            return !isWord(word.substring(0,word.length() - 3) + "Y");
        }  else {
            return !isWord(word.substring(0,word.length() - 1));
        }
    }


}
