package aul16.fr.jeuduroi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    Switch bugSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bugSwitch = findViewById(R.id.bugSwitch);

        final Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new activity intent
                Intent intent = new Intent(getApplicationContext(), SetPlayersActivity.class);
                //Check bug switch
                if(bugSwitch.isChecked())
                    intent.putExtra("bugStatus", true);
                else
                    intent.putExtra("bugStatus", false);
                //Start activity
                startActivity(intent);
            }
        });
    }
}
