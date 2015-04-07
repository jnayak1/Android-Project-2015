package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class StartScreen extends Activity{

    public final static String EXTRA_MESSAGE = "jdn4ae.cs2110.virginia.edu.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
    }

    public void start(View v){
        Intent intent = new Intent(this, GamePractice.class);
        startActivity(intent);
    }



}
