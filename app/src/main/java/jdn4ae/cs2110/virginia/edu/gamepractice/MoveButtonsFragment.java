package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MoveButtonsFragment extends Fragment implements View.OnClickListener {
    Button upButton;
//    Button downButton;
    Button rightButton;
    Button leftButton;
    MoveButton moveUpButton;
//    MoveButton moveDownButton;
    MoveButton moveRightButton;
    MoveButton moveLeftButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.move_buttons_layout,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        moveUpButton = (MoveButton) getActivity();
        upButton = (Button) getActivity().findViewById(R.id.up_button);
        upButton.setOnClickListener(this);

        moveRightButton = (MoveButton) getActivity();
        rightButton = (Button) getActivity().findViewById(R.id.right_button);
        rightButton.setOnClickListener(this);

        moveLeftButton = (MoveButton) getActivity();
        leftButton = (Button) getActivity().findViewById(R.id.left_button);
        leftButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.right_button:
                moveUpButton.rightButtonClick();
            break;

            case R.id.left_button:
                moveLeftButton.leftButtonClick();
            break;

            case R.id.up_button:
                moveUpButton.upButtonClick();
            break;
        }
    }
}
