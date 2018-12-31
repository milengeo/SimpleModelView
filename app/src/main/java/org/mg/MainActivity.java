package org.mg;


import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mg.R;



@SuppressWarnings("deprecation")



/*
A demo for a simple model-view pattern to survive configuration changes
without need for serialization/deserialization of the activity state
or using an additional framework.
All state related data are kept in a static class which is tied to
the  application life cycle, instead of the activity one.
*/


public class MainActivity extends ActionBarActivity {


    //An inner static class to hold the view data
    private static class Model {
        int mCount;
        int getNextCount() {
            mCount++;
            return mCount;
        }
    }

    //A single static instance of the Model class keeping all view data
    private static Model model = new Model();

    private Handler mHandler;
    private TextView mLogView;
    private ScrollView mLogScroll;






    private void log(String aLine) {
        Log.d("MainActivity", aLine);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String title = getString(R.string.app_name);
        setTitle(title);

        mHandler = new Handler();
        mLogView = (TextView) findViewById(R.id.log_view);
        mLogScroll = (ScrollView) findViewById(R.id.view_log_scroll);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }


    @Override
    public void onResume() {
        super.onResume();
        timerClick();
    }


    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }


    public void say(String aLine) {
        mLogView.append(aLine + "\n");
        mLogScroll.fullScroll(View.FOCUS_DOWN);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    private void timerClick() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doClick();
                timerClick();
            }
        }, 1000);
    }


    private void doClick() {
        say("count: " + model.getNextCount());
    }

}