package aul16.fr.jeuduroi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Objects;

public class SetPlayersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_players);

        //Get the bug status
        final Boolean bugStatus = Objects.requireNonNull(getIntent().getExtras()).getBoolean("bugStatus");

        final LinearLayout playersLinearLayout = findViewById(R.id.playersLinearLayout);

        final EditText etPlayerExample = findViewById(R.id.player1);
        final ViewGroup.LayoutParams params = etPlayerExample.getLayoutParams();

        final int[] numPlayers = {0};

        //Create EditText to add players
        final EditText etNumPlayers = findViewById(R.id.etNumPlayers);
        Button btnNumPlayers = findViewById(R.id.btnNumPlayerValidate);
        btnNumPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNumPlayers.getText().toString().equals("") && Integer.valueOf(etNumPlayers.getText().toString()) > 2){
                    numPlayers[0] = Integer.valueOf(etNumPlayers.getText().toString());
                    playersLinearLayout.removeAllViews();
                    for(int i = 0; i < numPlayers[0]; i++){
                        EditText et = new EditText(getApplicationContext());
                        et.setLayoutParams(params);
                        et.setId(i);
                        playersLinearLayout.addView(et);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Not a possible number", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Start game with players and bug status
        final Button btnOK = findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KingActivity.class);
                if(numPlayers[0] > 2){
                    intent.putExtra("numPlayers", numPlayers[0]);
                    for(int i = 0; i < numPlayers[0]; i++){
                        EditText etPlayer = findViewById(i);
                        if(!etPlayer.getText().toString().equals("")){
                            intent.putExtra(String.valueOf(i), etPlayer.getText().toString());
                        }
                    }
                    intent.putExtra("bugStatus", bugStatus);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Not enough player", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
