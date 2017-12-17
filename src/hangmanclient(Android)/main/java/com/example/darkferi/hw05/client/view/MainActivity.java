package com.example.darkferi.hw05.client.view;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.darkferi.hw05.R;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    Button button1;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActivity();
    }
    private void setupActivity(){
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(v);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRules();
            }
        });
    }


    public void startGame(View v) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(v.getContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Intent intent;
            intent = new Intent(this, DisplayActivity.class);
            startActivity(intent);
        }
   }


    private void showRules(){
        Intent intent;
        intent = new Intent(this, RuleActivity.class);
        startActivity(intent);
    }
}




 /*
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        */

            /*ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(v.getContext().CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                clientCntrl.connect(serverPort, screenHandler);
            }*/


// Get the Intent that started this activity and extract the string

        /*Intent intent = getIntent();
        String message = "Hello Everyone...";

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textViewGameWord);
        textView.setText(message);
        */