package com.example.darkferi.hw05.client.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.darkferi.hw05.R;
import com.example.darkferi.hw05.client.controller.ClientController;
import com.example.darkferi.hw05.client.network.OutputHandler;

import java.io.IOException;



public class DisplayActivity extends AppCompatActivity {

    public static final int SERVER_PORT = 8080;
    public static final String START = "start";
    public static final String EMPTY = "";
    public static final String WORD = "word ";
    public static final String EXIT = "exit";
    public static final String STRING_SEPERATOR = "@";
    public static final String SPACE = " ";

    public Button send1, send2, tryAgain, exit, start;
    public EditText editText1, editText2;
    public TextView textView;
    public String messageOnDevice;
    private boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_game);
        startGame();
    }

    /////////////////////////////////////////////////////////////Start//////////////////////////////////////////////////////////

    private void startGame() {

        Thread userInterfaceThread = new Thread(new ClientInterpreter(SERVER_PORT));
        userInterfaceThread.start();

    }


    /////////////////////////////////////////////////////////////Display////////////////////////////////////////////////////////

    private class ConsoleOutput implements OutputHandler {                      /////////ezafe shod

        @Override
        public void messageOnScreen(String message) {
            printMyMsg(message);
        }

        synchronized void printMyMsg(String DisplayMsg) {
            if (!DisplayMsg.isEmpty()) {
                messageOnDevice = DisplayMsg;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] Msg = messageOnDevice.split(STRING_SEPERATOR);
                        if (Msg[0].startsWith("Word")) {
                            String word = Msg[0];
                            String attemp = Msg[1];
                            String score = Msg[2];
                            textView = findViewById(R.id.textViewGameWord);
                            textView.setText(word);
                            textView = findViewById(R.id.textViewGameAttemp);
                            textView.setText(attemp);
                            textView = findViewById(R.id.textViewGameScore);
                            textView.setText(score);
                        } else if (Msg.length == 2) {
                            String notify = Msg[0];
                            String score = Msg[1];
                            textView = findViewById(R.id.textViewGameWord);
                            textView.setText(notify);
                            textView = findViewById(R.id.textViewGameAttemp);
                            textView.setText(EMPTY);
                            textView = findViewById(R.id.textViewGameScore);
                            textView.setText(score);
                        }
                    }
                });
            }
        }
    }

    /////////////////////////////////////////////////////////////ClientAction/////////////////////////////////////////////////
    private class ClientInterpreter implements Runnable {

        private final int serverPort;
        private ClientController clientCntrl;
        private ConsoleOutput screenHandler;
        private boolean ThreadStarted = false;


        private ClientInterpreter(int serverPort) {
            this.serverPort = serverPort;
        }


        @Override
        public void run() {
            ThreadStarted = true;
            clientCntrl = new ClientController();
            screenHandler = new ConsoleOutput();

            clientCntrl.connect(serverPort, screenHandler);

            while (ThreadStarted) {

                send1 = findViewById(R.id.gameButtonSend1);
                send2 = findViewById(R.id.gameButtonSend2);
                tryAgain = findViewById(R.id.gameButtonTryAgain);
                exit = findViewById(R.id.gameButtonExit);
                start = findViewById(R.id.startButton);
                editText1 = findViewById(R.id.editTextGame1);
                editText2 = findViewById(R.id.editTextGame2);


                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            clientCntrl.sendMessage(START);
                            start.setVisibility(View.GONE);
                            isStarted = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                send1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if(isStarted) {
                                String message = editText1.getText().toString();
                                message = message.toLowerCase();
                                if (message.length() == 1) {
                                    char guessChar = message.charAt(0);
                                    if (Character.isLetter(guessChar)) {
                                        clientCntrl.sendMessage(message);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Invalid character!", Toast.LENGTH_LONG).show();
                                    }
                                } else if (message.length() == 0) {
                                    Toast.makeText(getApplicationContext(), "Please put one letter...", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error: just put one letter!!", Toast.LENGTH_LONG).show();
                                }
                                editText1.setText(EMPTY);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                send2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if(isStarted) {
                                   String message = editText2.getText().toString();
                                message = message.toLowerCase();
                                if (message.indexOf(SPACE) != -1) {
                                    Toast.makeText(getApplicationContext(), "Error: just put one word!", Toast.LENGTH_LONG).show();
                                } else if (message.length() == 0) {
                                    Toast.makeText(getApplicationContext(), "Please put one word...", Toast.LENGTH_LONG).show();
                                } else {
                                    message = WORD + message;
                                    clientCntrl.sendMessage(message);
                                }

                                editText2.setText(EMPTY);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                tryAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if(isStarted) {
                                clientCntrl.sendMessage(START);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            clientCntrl.sendMessage(EXIT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ThreadStarted = false;
                        isStarted = false;
                        goToFirstPage();
                    }
                });
            }
        }
    }

    private void goToFirstPage(){
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}