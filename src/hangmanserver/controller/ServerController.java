/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmanserver.controller;
import java.io.IOException;
import static java.lang.Character.isLetter;
import hangmanserver.model.Hangman;
import hangmanserver.model.RandomWord;

/**
 *
 * @author darkferi
 */
public class ServerController {
    
    private boolean isStarted = false;
    private boolean isMiddle = false;
    private boolean flag = false;
    private Hangman hangmanGame;
    private StringBuilder wordScreen;
    private static final char SHOW_SCREEN = '_';
    private char guessChar;
    private String dataToPlayer;
    private static final String STRING_SEPERATOR = "#";
    
    
    
    
    
    
    public String checkUserInput(String userInput) throws IOException{
                if(userInput.startsWith("start") && userInput.length()==5){
                   
                    if (!isStarted){
                        if(!isMiddle){
                            RandomWord findWord = new RandomWord();
                            String chosenWord = findWord.getTheWord();
                            wordScreen = new StringBuilder(chosenWord);

                            for (int i = 0; i < wordScreen.length(); i++){
                                wordScreen.setCharAt(i, SHOW_SCREEN);                                   
                            }
                            
                            hangmanGame = new Hangman(chosenWord, chosenWord.length());
                            dataToPlayer = hangmanGame.showState(wordScreen);          
                            isStarted = true;
                        }
                        else{
                            dataToPlayer = hangmanGame.showState(wordScreen);
                            isMiddle = false;
                            isStarted = true;
                        }
                    }
                    
                    else{
                        if (!isMiddle){
                            //////////////////////////////////////////////////////////////////////////
                            dataToPlayer = "You started the game already!!!";
                            dataToPlayer += hangmanGame.showState(wordScreen); 
                            //////////////////////////////////////////////////////////////////////////
                        }
                        
                    }
                }
 
                else if(userInput.startsWith("word ") && (!isMiddle)){
                    if(isStarted == true){
                        userInput = userInput.substring(5).trim();
                        
                        dataToPlayer = hangmanGame.hangmanCheckWord(userInput, wordScreen);
                        String[] temp = dataToPlayer.split(STRING_SEPERATOR);
                        dataToPlayer = temp[0];
                        wordScreen = new StringBuilder(temp[1]);
                        
                        if(!hangmanGame.gameWinLoseState){
                            dataToPlayer = hangmanGame.showState(wordScreen);
                        }
                        else{
                            isMiddle = true;
                            isStarted = false;
                            hangmanGame.gameWinLoseState = false;
                        }
                    }
                    else{
                        //dataToPlayer = "You have not started the game yet. First write START command.";
                    }
                }
                else{
                    if(userInput.length()==0){//ok
                        //dataToPlayer = "\nYou didn't type anything or you are typing too fast!!!!\n";
                    }
                    else{
                        guessChar = userInput.charAt(0);
                        if( (userInput.length()==1) && (isLetter(guessChar)) && (isStarted == true) && (!isMiddle) ){
                            
                            dataToPlayer = hangmanGame.hangmanCheckChar(guessChar, wordScreen);
                            if(dataToPlayer.indexOf(STRING_SEPERATOR)!= -1){
                                String[] temp = dataToPlayer.split(STRING_SEPERATOR);
                                dataToPlayer = temp[0];
                                wordScreen = new StringBuilder(temp[1]);
                            }
                            else{
                                wordScreen = new StringBuilder(dataToPlayer);
                                flag = true;
                            }
                            
                            if(!hangmanGame.gameWinLoseState && !flag){
                                dataToPlayer = hangmanGame.showState(wordScreen);
                            }
                            else if(!hangmanGame.gameWinLoseState && flag){
                                dataToPlayer = hangmanGame.showState(wordScreen);
                                flag = false;
                            }
                            else{
                                isMiddle = true;
                                isStarted = false;
                                hangmanGame.gameWinLoseState = false;
                            }
                        }
                       
                        else if ( (userInput.length()==1) && (!isLetter(guessChar))&& (isStarted == true) && (!isMiddle)){//ok
                            dataToPlayer = hangmanGame.showState(wordScreen);
                        }
                        else{//ok
                            if ((isStarted) && (!isMiddle)){
                                dataToPlayer = hangmanGame.showState(wordScreen);
                            }
                        }
                    }
                }  
                
        return dataToPlayer;           

    }
    
}
