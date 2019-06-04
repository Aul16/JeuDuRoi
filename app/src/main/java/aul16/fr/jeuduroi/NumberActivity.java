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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        //Get bug status for next round
        final Boolean bugStatus = Objects.requireNonNull(getIntent().getExtras()).getBoolean("bugStatus");

        int playersNumListSize = Objects.requireNonNull(getIntent().getExtras()).getInt("playersNumListSize");

        StringBuilder dispNumPlayers = new StringBuilder();

        //Create playersList to put it in next round
        final List<String> playersList;
        playersList = new ArrayList<>();
        for(int i = 0; i < playersNumListSize; i++){
            playersList.add(Objects.requireNonNull(getIntent().getExtras()).getString(String.valueOf(i)));
        }

        final String king = Objects.requireNonNull(getIntent().getExtras()).getString("king");

        //Create bugged playersList to show it but without bugging next round
        final List<String> playersNum;
        playersNum = new ArrayList<>();
        if(playersList.get(playersNumListSize - 1).equals("bugged")){
            //Remove "bugged" for next round
            playersList.remove(playersNumListSize - 1);
            playersNum.addAll(playersList);
            Collections.shuffle(playersNum);
            playersNum.set(1, king);
            Collections.shuffle(playersNum);
        }else {
            playersNum.addAll(playersList);
        }
        Collections.shuffle(playersNum);
        //Show playersNum list to keep playersList not bugged
        for(int i = 0; i < playersNum.size(); i++){
            dispNumPlayers.append((i + 1)).append(": ").append(playersNum.get(i)).append("\n");
        }

        TextView tvPlayersNum = findViewById(R.id.tvPlayersNum);
        tvPlayersNum.setText(dispNumPlayers);

        //Start next round
        Button btnNewRound = findViewById(R.id.btnNewRound);
        btnNewRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewRoundActivity.class);
                //Add the king in the list without bug
                playersList.add(king);
                //Send players to next activity
                for(int i = 0; i < playersList.size(); i++){
                    intent.putExtra(String.valueOf(i), playersList.get(i));
                }
                intent.putExtra("bugStatus", bugStatus);
                intent.putExtra("numPlayer", playersList.size());
                startActivity(intent);
            }
        });

        //Set button menu
        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(NumberActivity.this)
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
}
