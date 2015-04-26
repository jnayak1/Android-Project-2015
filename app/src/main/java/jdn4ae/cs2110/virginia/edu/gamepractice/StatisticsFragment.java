package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by pnayak1 on 4/26/15.
 */
public class StatisticsFragment extends Fragment {

    private static TextView killsTextView;
    private static TextView timerTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics_layout,container,false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        killsTextView = (TextView) getActivity().findViewById(R.id.killsTextView);
        timerTextView = (TextView) getActivity().findViewById(R.id.timerTextView);
    }

    public static TextView getKillsTextView(){
        return killsTextView;
    }

    public static TextView getTimerTextView(){
       return timerTextView;
    }

}
