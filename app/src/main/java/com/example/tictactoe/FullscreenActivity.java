package com.example.tictactoe;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            /*
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    */
        }
    };
    private View mControlsView;
    
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private class field{
        boolean set;
        boolean player1;

        public field(){
            set = false;
            player1 = false;
        }
    }
    
    
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button replay;
    private Button[] allButtons;
    private ImageView field1;
    private ImageView field2;
    private ImageView field3;
    private ImageView field4;
    private ImageView field5;
    private ImageView field6;
    private ImageView field7;
    private ImageView field8;
    private ImageView field9;
    private ImageView[] allFields;
    private Integer[] fieldset = {2, 3, 4, 5, 6, 7, 8, 9, 10};
    private Integer[][] winningposition = {{0, 1, 2}, {0, 4, 8}, {0, 3, 6}, {1, 4, 7}, {2, 4, 6}, {2, 5, 8}, {3, 4, 5}, {6, 7, 8}};
    private boolean player1Turn = true;
    private boolean end = false;
    private int winner = 3;
    TextView message;
    int numField= 0;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);


        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        //mContentView = findViewById(R.id.fullscreen_content);


        field1 = findViewById(R.id.field1);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);

        field2 = findViewById(R.id.field2);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);


        field3 = findViewById(R.id.field3);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);


        field4 = findViewById(R.id.field4);
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(this);

        field5 = findViewById(R.id.field5);
        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(this);

        field6 = findViewById(R.id.field6);
        button6 = findViewById(R.id.button6);
        button6.setOnClickListener(this);

        field7 = findViewById(R.id.field7);
        button7 = findViewById(R.id.button7);
        button7.setOnClickListener(this);

        field8 = findViewById(R.id.field8);
        button8 = findViewById(R.id.button8);
        button8.setOnClickListener(this);


        field9 = findViewById(R.id.field9);
        button9 = findViewById(R.id.button9);
        button9.setOnClickListener(this);
        allFields = new ImageView[]{field1, field2, field3, field4, field5, field6, field7, field8, field9};
        allButtons = new Button[]{button1, button2, button3, button4, button5, button6, button7, button8, button9};

        replay = findViewById(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        message = findViewById(R.id.message);
        

        // Set up the user interaction to manually show or hide the system UI.
       /*
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Button1 pressed", Toast.LENGTH_SHORT).show();
                if(player1Turn){
                    field1.setImageResource(R.drawable.red);
                }else {
                    field1.setImageResource(R.drawable.yellow);
                }
                field1.setVisibility(View.VISIBLE);
                player1Turn = !player1Turn;
            }
        });
        */

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void check(){
        outer:
        for(Integer[] i : winningposition){
            if(fieldset[i[0]] == fieldset[i[1]] && fieldset[i[1]] == fieldset[i[2]]){
                winner = fieldset[i[0]];
                end = true;
                message.setText("Player " + (winner + 1) + " won!");
                message.setVisibility(View.VISIBLE);
                break outer;
            }
        }
        if(numField == 9 && !end){
            message.setText("Draw!");
            message.setVisibility(View.VISIBLE);
            end = true;
        }
    }

    private void redraw(){
        for(int i = 0; i < fieldset.length; i++){
            if(fieldset[i] == 0){
                ImageView field = allFields[i];
                field.setImageResource(R.drawable.red);
                field.setVisibility(View.VISIBLE);
            }else if(fieldset[i] == 1){
                ImageView field = allFields[i];
                field.setImageResource(R.drawable.yellow);
                field.setVisibility(View.VISIBLE);
            }else{
                allFields[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void reset(){
        fieldset = new Integer[] {2, 3, 4, 5, 6, 7, 8, 9, 10};
        for(ImageView i : allFields){
            i.setVisibility(View.INVISIBLE);
        }
        player1Turn = true;
        message.setText("");
        message.setVisibility(View.INVISIBLE);
        end = false;
        numField = 0;
        redraw();
    }

    @Override
    public void onClick(View view){
        if(!end) {
            for (int i = 0; i < allButtons.length; i++) {
                if (view == allButtons[i] & fieldset[i] >= 2) {
                    fieldset[i] = player1Turn ? 0 : 1;
                    player1Turn = !player1Turn;
                    numField++;
                    break;
                }
            }
            redraw();
            check();
        }
    }

}
