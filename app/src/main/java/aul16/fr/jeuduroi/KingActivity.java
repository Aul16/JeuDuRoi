package aul16.fr.jeuduroi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class KingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_king);

        //Get bug status
        final Boolean bugStatus = Objects.requireNonNull(getIntent().getExtras()).getBoolean("bugStatus");

        Button btnValidateKingChoice = findViewById(R.id.btnValidateKingChoice);

        final TextView tvKingName = findViewById(R.id.tvKingName);

        //Create the player list
        final List<String> playerList;
        playerList = new ArrayList<>();

        // Get players
        final int numPlayer = Objects.requireNonNull(getIntent().getExtras()).getInt("numPlayers");
        for(int i = 0; i < numPlayer; i++){
            String player = Objects.requireNonNull(getIntent().getExtras().getString(String.valueOf(i), String.valueOf(456106541)));
            if(!Objects.equals(player, String.valueOf(456106541))){
                playerList.add(player);
            }
        }
        int numPlayers = playerList.size();

        final String king = setKing(numPlayers, playerList);
        final List<String> playersNumList = setPlayersNum(numPlayers, playerList, king, bugStatus);

        //Display king name
        String textKingName = tvKingName.getText() + "\n" + king;
        tvKingName.setText(textKingName);

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
            //Set if bugged
            if(bug == 2){
                playersNum.add("bugged");
                Log.v("check2", String.valueOf(playersNum));
            }
        }
        return playersNum;
    }
}