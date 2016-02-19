package comsitesooweech.google.httpssites.whackamole;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ViewGroup soichisLayout;
    MediaPlayer punchSfx;
    MediaPlayer bombSfx;
    int count = 0;
    boolean bombOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soichisLayout = (ViewGroup) findViewById(R.id.soichisLayout);
        ImageButton moleButton = (ImageButton) findViewById(R.id.moleButton);
        punchSfx = MediaPlayer.create(this, R.raw.punch_sfx);
        bombSfx = MediaPlayer.create(this, R.raw.bomb_sfx);
        moleButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveMole();
                    }
                }
        );
    }

    public void countPoints(){
        TextView pointsNumber = (TextView) findViewById(R.id.pointsNumber);
        pointsNumber.setText("" + count);
    }

    public void clickedBomb(View moleButton) {
        bombOn = false;
        count = count - 5;
        countPoints();
        bombSfx.start();
        moleButton.setBackgroundResource(R.drawable.explosion);
    }

    public void moveMole() {
        View moleButton = findViewById(R.id.moleButton);
        moleButton.setBackgroundResource(R.drawable.minion_small);
        //TransitionManager.beginDelayedTransition(soichisLayout);
        if (bombOn) {
            clickedBomb(moleButton);
        } else {
            count++;
            countPoints();
            punchSfx.start();
        }
        Random rand = new Random();
        int bombChance = rand.nextInt(6);
        if (bombChance == 0) {
            moleButton.setBackgroundResource(R.drawable.bomb);
            bombOn = true;
        }
        int spot = rand.nextInt(6) + 1; //1, 2, 3, 4, 5, 6


        RelativeLayout.LayoutParams positionRules = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (spot == 1) {
            positionRules.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            positionRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        } else if (spot == 2) {
            positionRules.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            positionRules.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        } else if (spot == 3) {
            positionRules.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            positionRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        } else if (spot == 4) {
            positionRules.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            positionRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            positionRules.bottomMargin = 50;
        } else if (spot == 5) {
            positionRules.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            positionRules.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            positionRules.bottomMargin = 50;
        } else if (spot == 6) {
            positionRules.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            positionRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            positionRules.bottomMargin = 50;
        }
        moleButton.setLayoutParams(positionRules);
        final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_popup_enter);
        moleButton.startAnimation(animation);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
