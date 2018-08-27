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

public class GeneralFragment extends Fragment {
    private static final String TAG = "GeneralFragment";
    protected static String selectedGamertag = "Applepie2";
    protected static String selectedGameMode = "solo";

    protected PUBGManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_fragment, container, false);

        manager = MyProperties.getInstance().pubgManager;

        // Set the gamertag spinner
        Spinner spinnerGT = view.findViewById(R.id.gamertagSpinner);
        ArrayAdapter<String> adapterGT = new ArrayAdapter(this.getContext(), R.layout.spinner_item, manager.wxcGamertags);
        adapterGT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGT.setAdapter(adapterGT);
        spinnerGT.setOnItemSelectedListener(new GamertagSpinnerActivity());

        // Set the game mode spinner
        Spinner spinnerGM = view.findViewById(R.id.gameModeSpinner);
        ArrayAdapter<String> adapterGM = new ArrayAdapter(this.getContext(), R.layout.spinner_item, manager.gameModes);
        adapterGM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGM.setAdapter(adapterGM);
        spinnerGM.setOnItemSelectedListener(new GameModeSpinnerActivity());

        return view;
    }

    public static class GamertagSpinnerActivity extends GeneralFragment implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {

            selectedGamertag = parent.getItemAtPosition(pos).toString();
            UpdateView(view);
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public static class GameModeSpinnerActivity extends GeneralFragment implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {

            selectedGameMode = parent.getItemAtPosition(pos).toString().toLowerCase();
            UpdateView(view);
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void UpdateView (View view) {

        // Make sure the manager is updated
        manager = MyProperties.getInstance().pubgManager;

        // Define all the text view items to be updated
        TextView winPointsValue = view.getRootView().findViewById(R.id.winPointsValue);
        TextView killPointsValue = view.getRootView().findViewById(R.id.killPointsValue);

        TextView winsValue = view.getRootView().findViewById(R.id.winsValue);
        TextView top10sValue = view.getRootView().findViewById(R.id.top10sValue);
        TextView lossesValue = view.getRootView().findViewById(R.id.lossesValue);

        TextView killsValue = view.getRootView().findViewById(R.id.killsValue);
        TextView deathsValue = view.getRootView().findViewById(R.id.deathsValue);
        TextView kdValue = view.getRootView().findViewById(R.id.kdValue);

        TextView headshotsValue = view.getRootView().findViewById(R.id.headshotsValue);
        TextView averageDamageValue = view.getRootView().findViewById(R.id.averageDamageValue);

        TextView maxKillstreak = view.getRootView().findViewById(R.id.maxKillstreakValue);
        TextView mostKills = view.getRootView().findViewById(R.id.mostKillsValue);

        TextView roadKillsValue = view.getRootView().findViewById(R.id.roadKillsValue);
        TextView teamKillsValue = view.getRootView().findViewById(R.id.teamKillsValue);
        TextView longestKillValue = view.getRootView().findViewById(R.id.longestKillValue);

        TextView suicidesValue = view.getRootView().findViewById(R.id.suicidesValue);
        TextView assistsValue = view.getRootView().findViewById(R.id.assistsValue);
        TextView dbnosValue = view.getRootView().findViewById(R.id.dbnosValue);

        TextView averageHealsValue = view.getRootView().findViewById(R.id.avgHealsValue);
        TextView averageBoostsValue = view.getRootView().findViewById(R.id.avgBoostsValue);
        TextView revivesValue = view.getRootView().findViewById(R.id.revivesValue);

        TextView longestSurvivedValue = view.getRootView().findViewById(R.id.longestSurvivedValue);
        TextView avgSurvivedValue = view.getRootView().findViewById(R.id.avgSurvivedValue);

        TextView avgWalkValue = view.getRootView().findViewById(R.id.avgWalkValue);
        TextView avgRideValue = view.getRootView().findViewById(R.id.avgRideValue);

        // Find the corresponding stats for the player and game mode
        for (int i = 0; i < manager.players.size(); i++ ){
            if (manager.players.get(i).getGamertag().equals(selectedGamertag)) {
                for (int j = 0; j < manager.players.get(i).getStats().size(); j++ ){
                    if (manager.players.get(i).getStats().get(j).getGameMode().equals(selectedGameMode)) {

                        // Gather the stats for the player and game mode
                        SeasonModel stats = manager.players.get(i).getStats().get(j);

                        // Define some useful values
                        Integer matchesPlayed = stats.getRoundsPlayed();
                        Double kd = Double.valueOf(stats.getKills()) / Double.valueOf(stats.getRoundsPlayed() - stats.getWins());
                        Double headshotPercent = (Double.valueOf(stats.getHeadshotKills()) / Double.valueOf(stats.getKills())) * 100;
                        Double winsPercent = (Double.valueOf(stats.getWins()) / Double.valueOf(matchesPlayed)) * 100;
                        Double top10sPercent = (Double.valueOf(stats.getTop10s()) / Double.valueOf(matchesPlayed)) * 100;
                        Double averageSurvivedTime = (Double.valueOf(stats.getTotalTimeSurvived()) / Double.valueOf(matchesPlayed));
                        Double averageWalkDistance = (Double.valueOf(stats.getWalkDistance()) / Double.valueOf(matchesPlayed));
                        Double averageRideDistance = (Double.valueOf(stats.getRideDistance()) / Double.valueOf(matchesPlayed));

                        // Set the Points table
                        winPointsValue.setText(Double.toString(stats.getWinPoints()).substring(0, Math.min(Double.toString(stats.getWinPoints()).length(), 6)));
                        killPointsValue.setText(Double.toString(stats.getKillPoints()).substring(0, Math.min(Double.toString(stats.getKillPoints()).length(), 6)));

                        // Set the Match Results table
                        winsValue.setText(Integer.toString(stats.getWins()) + " (" + Double.toString(winsPercent).substring(0, Math.min(Double.toString(winsPercent).length(), 4)) + "%)");
                        top10sValue.setText(Integer.toString(stats.getTop10s()) + " (" + Double.toString(top10sPercent).substring(0, Math.min(Double.toString(top10sPercent).length(), 4)) + "%)");
                        lossesValue.setText(Integer.toString(stats.getLosses()));

                        // Set the Kills table
                        killsValue.setText(Integer.toString(stats.getKills()));
                        deathsValue.setText(Integer.toString(stats.getLosses() - stats.getWins()));
                        kdValue.setText(Double.toString(kd).substring(0, Math.min(Double.toString(kd).length(), 4)));

                        // Set the Damage table
                        headshotsValue.setText(Integer.toString(stats.getHeadshotKills()) + " (" + Double.toString(headshotPercent).substring(0, Math.min(Double.toString(headshotPercent).length(), 5)) + "%)");
                        averageDamageValue.setText(Double.toString(Double.valueOf(stats.getDamageDealt()) / Double.valueOf(matchesPlayed)).substring(0, Math.min((Double.toString(Double.valueOf(stats.getDamageDealt()) / Double.valueOf(matchesPlayed))).length(), 6)));

                        // Set the Kills Record table
                        maxKillstreak.setText(Integer.toString(stats.getMaxKillStreak()));
                        mostKills.setText(Integer.toString(stats.getRoundMostKills()));

                        // Set the Special Kills table
                        roadKillsValue.setText(Integer.toString(stats.getRoadKills()));
                        teamKillsValue.setText(Integer.toString(stats.getTeamKills()));
                        longestKillValue.setText(Double.toString(stats.getLongestKill()).substring(0, Math.min(Double.toString(stats.getLongestKill()).length(), 6)) + " m");

                        // Set the Assists table
                        suicidesValue.setText(Integer.toString(stats.getSuicides()));
                        assistsValue.setText(Integer.toString(stats.getAssists()));
                        dbnosValue.setText(Integer.toString(stats.getDBNOs()));

                        // Set the Heals table
                        averageHealsValue.setText(Double.toString(Double.valueOf(stats.getHeals()) / Double.valueOf(matchesPlayed)).substring(0, Math.min((Double.toString(Double.valueOf(stats.getHeals()) / Double.valueOf(matchesPlayed))).length(), 4)));
                        averageBoostsValue.setText(Double.toString(Double.valueOf(stats.getBoosts()) / Double.valueOf(matchesPlayed)).substring(0, Math.min((Double.toString(Double.valueOf(stats.getBoosts()) / Double.valueOf(matchesPlayed))).length(), 4)));
                        revivesValue.setText(Integer.toString(stats.getRevives()));

                        // Set the Survival Time table
                        Integer minutes = (int) Math.round(stats.getLongestTimeSurvived()) / 60;
                        Integer seconds = (int) Math.round(stats.getLongestTimeSurvived()) % 60;
                        String secondsString = seconds.toString();
                        if (seconds < 10) { secondsString = "0" + secondsString; }
                        longestSurvivedValue.setText(minutes.toString() + ":" + secondsString);
                        minutes = (int) Math.round(averageSurvivedTime) / 60;
                        seconds = (int) Math.round(averageSurvivedTime) % 60;
                        secondsString = seconds.toString();
                        if (seconds < 10) { secondsString = "0" + secondsString; }
                        avgSurvivedValue.setText(minutes.toString() + ":" + secondsString);

                        // Set the Distances table
                        avgWalkValue.setText(Double.toString(averageWalkDistance).substring(0, Math.min(Double.toString(averageWalkDistance).length(), 6)) + " m");
                        avgRideValue.setText(Double.toString(averageRideDistance).substring(0, Math.min(Double.toString(averageRideDistance).length(), 6)) + " m");

                        // Once the stats have been successfully posted to the fragment break from the searching loop
                        break;
                    }
                }
                // Break from the outer loop
                break;
            }
        }
    }
}


