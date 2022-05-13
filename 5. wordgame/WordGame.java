import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class WordGame {

    // inner static class
    public static class WordGameState {

        private String word;
        private String displayWord;
        private int mistakes;
        private int mistakeLimit;
        private int missingChars;

        private WordGameState(String word, int mistakeLimit) {
            this.word = word;
            this.mistakes = 0;
            this.mistakeLimit = mistakeLimit;
            this.displayWord = ("_").repeat(this.word.length());
            this.missingChars = word.length();
        }

        public String getWord() {

            return this.displayWord;
        }

        public int getMistakes() {

            return this.mistakes;
        }

        public int getMistakeLimit() {

            return this.mistakeLimit;
        }

        public int getMissingChars() {

            return this.missingChars;
        }
        private void addMistake() {
            this.mistakes = this.mistakes + 1;
        }
    }

    private ArrayList<String> allWords;
    private WordGameState currentGame;

    // constructor
    public WordGame(String wordFilename) throws IOException {
        this.allWords = readFile(wordFilename);
    }

    public void initGame(int wordIndex, int mistakeLimit) {

        int N = this.allWords.size();
        String word = this.allWords.get(wordIndex % N);
        this.currentGame = new WordGameState(word, mistakeLimit);

    }

    public boolean isGameActive() {

        if (this.currentGame == null) {
            return false;
        }
        return true;
    }

    public WordGameState getGameState() throws GameStateException {

        if (isGameActive()) {
            return checkGameOver();
        }
        else {
            throw new GameStateException("There is currently no active word game!");
        }
    }

    public  WordGameState guess(char c) throws GameStateException {

        if (isGameActive())
        {
            // Store indexes of matching characters in indexes array
            ArrayList<Integer> indexes = new ArrayList<>();

            // Find first matching index
            int index = this.currentGame.word.indexOf(Character.toLowerCase(c));
            int index2 = this.currentGame.displayWord.indexOf(Character.toLowerCase(c));
            // If the letter isn't found
            if ((index == -1 ) || (index2 != -1))
            {
                this.currentGame.addMistake();
            }
            // If at least one letter is found
            else
            {
                // Store index of matching char
                indexes.add(index);

                // Look for more matches
                while ((index >= 0) && (index < this.currentGame.word.length()-1))
                {
                    index = this.currentGame.word.indexOf(Character.toLowerCase(c), index + 1);
                    if (index != -1)  {
                        indexes.add(index);
                    }
                }

                // Make char arrays of display word string
                char[] displayWordArr = this.currentGame.displayWord.toCharArray();

                // Replace the chars in the display word that match the char
                for (int i : indexes) {
                    displayWordArr[i] = Character.toLowerCase(c);
                }

                // Update the displayWord value with the new string
                this.currentGame.displayWord = String.valueOf(displayWordArr);

                this.currentGame.missingChars = this.currentGame.missingChars - indexes.size();
            }

            return checkGameOver();
        }
        else {
            throw new GameStateException("There is currently no active word game!");
        }
    }

    public WordGameState guess(String word) throws GameStateException {

        if (isGameActive())
        {
            if (word.equalsIgnoreCase(this.currentGame.word)) {
                // "Reveal" the word by setting the display word to equal the word
                this.currentGame.displayWord = word;
                this.currentGame.missingChars = 0;
            }
            else
            {
                this.currentGame.addMistake();
            }

            return checkGameOver();
        }
        else {
            throw new GameStateException("There is currently no active word game!");
        }
    }

    private ArrayList<String> readFile(String fileName) throws IOException {

        ArrayList<String> allWords = new ArrayList<>();

        try (var wordFile = new BufferedReader(new FileReader(fileName))) {

            String line;
            while((line = wordFile.readLine()) != null)
            {
                allWords.add(line);
            }
        }
        return allWords;
    }

    private WordGameState checkGameOver() {
        // Store the current state of the game in a temp variable
        WordGameState state = this.currentGame;

        // Set this.currentGame to null if a game over condition has been met
        // if win
        if (state.getMissingChars() == 0) {
            this.currentGame = null;
        }
        // if lost
        else if (state.getMistakes() > state.getMistakeLimit()) {
            state.displayWord = state.word;
            this.currentGame = null;
        }
        // No matter what, return the stored game state.
        return state;
    }
}
