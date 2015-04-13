package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class OtherButtonsFragment extends Fragment implements View.OnClickListener {
    Button shootButton;
    Button jumpButton;
    OtherButton otherJumpButton;
    OtherButton otherShootButton;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.other_buttons_layout,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        otherJumpButton = (OtherButton) getActivity();
        jumpButton = (Button) getActivity().findViewById(R.id.jump_button);
        jumpButton.setOnClickListener(this);

        otherShootButton = (OtherButton) getActivity();
        shootButton = (Button) getActivity().findViewById(R.id.shoot_button);
        shootButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_button:
                otherJumpButton.jumpButtonClick();
             break;

            case R.id.left_button:
                otherShootButton.shootButtonClick();
            break;
        }
    }
}
