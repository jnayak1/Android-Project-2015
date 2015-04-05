package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MapFragment extends Fragment{

    private GamePractice myActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return myActivity.getMapSurfaceView();
    }

    @Override
    public void onAttach(Activity myActivity) {
        super.onAttach(myActivity);
        this.myActivity = (GamePractice) myActivity;
    }
}
