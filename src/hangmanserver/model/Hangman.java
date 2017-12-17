

package hangmanserver.model;

import java.io.IOException;

/**
 *
 * @author darkferi
 */
public class Hangman {
    
    private int score = 0;
    private int attempNo;
    private String chosenWord;
    private static final char SHOW_SCREEN = '_';
    private static final char SPACE = ' ';
    private String outputInScreen;
    public boolean gameWinLoseState = false;
    private String s;
    private static final String STRING_SEPERATOR = "#";
    
    
    public Hangman(){
        this.chosenWord = " ";
        this.attempNo = 0;
    }

    public Hangman(String chosenWord, int attempNo){
        this.chosenWord = chosenWord;
        this.attempNo = attempNo;
    }
    
    private void IncreaseScore(){
        score++;
    }
    
    private void DecreaseScore(){
        score--;
    }
    
    
    public String hangmanCheckChar(char c, StringBuilder screenWord) throws IOException{
       
        boolean changed = false;
        boolean win = false;
        for(int i=0; i < chosenWord.length(); i++){
           if(c==chosenWord.charAt(i)){
               if(screenWord.charAt(i)==SHOW_SCREEN){
                   screenWord.setCharAt(i, c);
                   changed = true;
               }
               else{
                   return screenWord.toString(); 
               }
           }
        }
        
        if(changed == true){
            if(hangmanWin(screenWord)){
                IncreaseScore();
                outputInScreen = chosenWord.substring(0, 1).toUpperCase() + chosenWord.substring(1);
                s = "Congrats! The correct word: " + outputInScreen + "@Your Score: " + score;
                screenWord = selectNextWord(screenWord);
                s += STRING_SEPERATOR + screenWord.toString();
            }
            else{
                s = STRING_SEPERATOR + screenWord.toString();
            }
        }
        else{
            attempNo--;
            if (attempNo == 0){
                DecreaseScore();
                gameWinLoseState = true;
                outputInScreen = chosenWord.substring(0, 1).toUpperCase() + chosenWord.substring(1);
                s = "You Lost! The correct word: " + outputInScreen + "@Your Score: " + score;
                screenWord = selectNextWord(screenWord);
                s += STRING_SEPERATOR + screenWord.toString();
            }
            else{
                s += STRING_SEPERATOR + screenWord.toString();
            }
        }
        return s;
    }
    
    
    
    public String hangmanCheckWord(String userInput, StringBuilder screenWord) throws IOException{
        
        /*for(;;){
           //for testing responsive user interface 
        }*/
        
        if (userInput.equals(chosenWord.trim())){
            IncreaseScore();
            gameWinLoseState = true;
            outputInScreen = chosenWord.substring(0, 1).toUpperCase() + chosenWord.substring(1);
            
            screenWord = selectNextWord(screenWord);
            s = "Congrats! The correct word: " + outputInScreen + "@Your Score: " + score;
            s += STRING_SEPERATOR + screenWord.toString();
        }
            
        else{
            attempNo--;
            if (attempNo == 0){
                DecreaseScore();
                gameWinLoseState = true;
                outputInScreen = chosenWord.substring(0, 1).toUpperCase() + chosenWord.substring(1);
                s = "You Lost! The correct word: " + outputInScreen + "@Your Score: " + score;
                screenWord = selectNextWord(screenWord);
                s += STRING_SEPERATOR + screenWord.toString();
            }
            else{
                s += STRING_SEPERATOR + screenWord.toString();
            }
        }
 
        return s;
    }
    
    
    private boolean hangmanWin(StringBuilder screenWord){
        int found = screenWord.toString().indexOf(SHOW_SCREEN);
        if(found == -1){
            gameWinLoseState = true;
            return true;
        }
        return false;
    }
    
    private StringBuilder selectNextWord(StringBuilder screenWord){
        RandomWord newWord = new RandomWord();
        chosenWord = newWord.getTheWord();
        attempNo = chosenWord.length();
        screenWord = new StringBuilder(chosenWord);
        for (int i = 0; i < screenWord.length(); i++){
            screenWord.setCharAt(i, SHOW_SCREEN);
        }
        return screenWord;
    }
    
    
    public String showState(StringBuilder wordScreen) throws IOException{                                          
        StringBuilder outputBuilder = new StringBuilder(wordScreen.toString() + wordScreen.toString());
        
        //outputInScreen;
        for (int i = 0; i < wordScreen.length(); i++){
                outputBuilder.setCharAt(2*i, wordScreen.charAt(i));
                outputBuilder.setCharAt(2*i+1, SPACE);
            }
        outputInScreen = outputBuilder.toString();
        outputInScreen = outputInScreen.substring(0,1).toUpperCase() + outputInScreen.substring(1);
        s = "Word: " + outputInScreen + "@Attemp: " + attempNo+ "@Score:" + score ;
        return s;
    }
    
}
