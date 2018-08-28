package wxc.pubgtracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadingFragment extends Fragment {
    private static final String TAG = "LoadingFragment";

    protected PUBGManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loading_fragment, container, false);

        manager = MyProperties.getInstance().pubgManager;

        TextView loading1 = view.findViewById(R.id.loading1);
        TextView loading2 = view.findViewById(R.id.loading2);
        TextView loading3 = view.findViewById(R.id.loading3);
        TextView loading4 = view.findViewById(R.id.loading4);

        if (manager.players.size() == 4) {
            if (manager.players.get(0).getStatsLoaded().equals(true)) {
                loading1.setBackgroundResource(R.color.colorWhite);
            }
            if (manager.players.get(1).getStatsLoaded().equals(true)) {
                loading2.setBackgroundResource(R.color.colorWhite);
            }
            if (manager.players.get(2).getStatsLoaded().equals(true)) {
                loading3.setBackgroundResource(R.color.colorWhite);
            }
            if (manager.players.get(3).getStatsLoaded().equals(true)) {
                loading4.setBackgroundResource(R.color.colorWhite);
            }
        }
        return view;
    }
}


