package aul16.fr.jeuduroi;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class NewRoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_round);

        final Boolean bugStatus = Objects.requireNonNull(getIntent().getExtras()).getBoolean("bugStatus");

        //Get the number of players
        int numPlayers = Objects.requireNonNull(getIntent().getExtras()).getInt("numPlayer");

        TextView tvKingName = findViewById(R.id.tvKingName);

        //Get the list of players
        List<String> playersList;
        playersList = new ArrayList<>();
        for(int i = 0; i < numPlayers; i++){
            playersList.add(getIntent().getExtras().getString(String.valueOf(i)));
        }

        //Set king
        final String king = setKing(numPlayers, playersList);

        //Display king name
        String textKingName = tvKingName.getText() + "\n" + king;
        tvKingName.setText(textKingName);

        //Set players's number
        final List<String> playersNumList = setPlayersNum(numPlayers, playersList, king, bugStatus);

        //Set NEXT button
        Button btnValidateKingChoice = findViewById(R.id.btnValidateKingChoice);
        btnValidateKingChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), NumberActivity.class);
                for(int i = 0; i < playersNumList.size(); i++){
                    i2.putExtra(String.valueOf(i), playersNumList.get(i));
                }
                i2.putExtra("bugStatus", bugStatus);
                i2.putExtra("playersNumListSize", playersNumList.size());
                i2.putExtra("king", king);
                startActivity(i2);
            }
        });

        //Set button menu
        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(NewRoundActivity.this)
                        .setTitle(R.string.alertMenuTitle)
                        .setMessage(R.string.alertMenu)
                        .setPositiveButton(R.string.alertMenuYes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.alertMenuCancel, null)
                        .show();
            }
        });
    }
    // Define the king
    public String setKing(int _numPlayers, List<String> _playerList){
        int king = new Random().nextInt(_numPlayers);
        return String.valueOf(_playerList.get(king));
    }
    //Set the num of players
    public List<String> setPlayersNum (int _numPlayers, List<String> _playerList, String _king, Boolean _bugStatus){
        List<String> playersNum;
        playersNum = new ArrayList<>();
        for(int i = 0; i < _numPlayers; i++){
            if(!_playerList.get(i).equals(_king)){
                playersNum.add(String.valueOf(_playerList.get(i)));
            }
        }
        Collections.shuffle(playersNum);
        if(_bugStatus){
            int bug = new Random().nextInt(5);
            Log.v("check", String.valueOf(bug));
            if(bug == 2){
                playersNum.add("bugged");
                Log.v("check", String.valueOf(playersNum));
            }
        }
        return playersNum;
    }
    //Disable back button
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        Toast.makeText(getApplicationContext(), "Back press disabled. Click the stop button to leave.", Toast.LENGTH_SHORT).show();
    }
}
