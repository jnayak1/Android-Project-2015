package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MoveButtonsFragment extends Fragment implements View.OnTouchListener {
    Button upButton;
    static boolean upIsPushed;
    Button rightButton;
    static boolean rightIsPushed;
    Button leftButton;
    static boolean leftIsPushed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.move_buttons_layout,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        upButton = (Button) getActivity().findViewById(R.id.up_button);
        upButton.setOnTouchListener(this);

        rightButton = (Button) getActivity().findViewById(R.id.right_button);
        rightButton.setOnTouchListener(this);

        leftButton = (Button) getActivity().findViewById(R.id.left_button);
        leftButton.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {

        switch(v.getId()) {
            case R.id.right_button:
                int actionRight = e.getAction();
                if (actionRight == MotionEvent.ACTION_DOWN){
                    rightIsPushed = true;
                }
                if(actionRight == MotionEvent.ACTION_UP){
                    rightIsPushed = false;
                }
            return true;

            case R.id.left_button:
                int actionLeft = e.getAction();
                if (actionLeft == MotionEvent.ACTION_DOWN){
                    leftIsPushed = true;
                }
                if(actionLeft == MotionEvent.ACTION_UP){
                    leftIsPushed = false;
                }
                return true;

            case R.id.up_button:
                int actionUp = e.getAction();
                if(actionUp == MotionEvent.ACTION_DOWN){
                    upIsPushed = true;
                }
                if(actionUp == MotionEvent.ACTION_UP){
                    upIsPushed = false;
                }
            return true;
        }
        return true;
    }
}
