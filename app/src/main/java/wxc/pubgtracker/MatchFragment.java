package wxc.pubgtracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MatchFragment extends Fragment {
    private static final String TAG = "MatchFragment";
    protected static String selectedGamertag = "Applepie2";
    protected static Integer selectedMatch = 0;

    protected PUBGManager manager;

    protected Button previousMatchBtn, nextMatchBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.match_fragment, container, false);

        manager = MyProperties.getInstance().pubgManager;

        // Init the buttons
        previousMatchBtn = view.findViewById(R.id.previousMatchButton);
        nextMatchBtn = view.findViewById(R.id.nextMatchButton);

        // Set the next match button inactive on start
        nextMatchBtn.setEnabled(false);

        previousMatchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Increase the match index by one to get the next oldest match
                selectedMatch++;
                nextMatchBtn.setEnabled(true);
                for (int i = 0; i < manager.players.size(); i++) {
                    if (manager.players.get(i).getGamertag().equals(selectedGamertag)){
                        if (selectedMatch >= manager.players.get(i).getMatches().size() - 1) {
                            selectedMatch = manager.players.get(i).getMatches().size() - 1;
                            previousMatchBtn.setEnabled(false);
                        }
                    }
                }
                UpdateView(v);
            }
        });
        nextMatchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Decrease the match index by one to get the next newest match
                selectedMatch--;
                previousMatchBtn.setEnabled(true);
                if (selectedMatch < 0) { selectedMatch = 0; nextMatchBtn.setEnabled(false); }
                UpdateView(v);
            }
        });

        // Set the gamertag spinner
        Spinner spinnerGT = view.findViewById(R.id.gamertagSpinner2);
        ArrayAdapter<String> adapterGT = new ArrayAdapter(this.getContext(), R.layout.spinner_item, manager.wxcGamertags);
        adapterGT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGT.setAdapter(adapterGT);
        spinnerGT.setOnItemSelectedListener(new GamertagSpinner2Activity());

        return view;
    }

    public static class GamertagSpinner2Activity extends MatchFragment implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {

            selectedGamertag = parent.getItemAtPosition(pos).toString();
            selectedMatch = 0;
            UpdateView(view);
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void UpdateView(View view) {

        // Make sure the manager is updated
        manager = MyProperties.getInstance().pubgManager;

        // Gather all the text views
        TextView matchDetailsValue = view.getRootView().findViewById(R.id.matchDetails);
        TextView dateTimeValue = view.getRootView().findViewById(R.id.dateTimeDetails);

        TextView winPlaceValue = view.getRootView().findViewById(R.id.winPlaceValue);
        TextView killPlaceValue = view.getRootView().findViewById(R.id.killPlaceValue);

        TextView winPointsValue = view.getRootView().findViewById(R.id.winPointsMatchValue);
        TextView killPointsValue = view.getRootView().findViewById(R.id.killPointsMatchValue);

        TextView winPointsChangeValue = view.getRootView().findViewById(R.id.winPointsChangeValue);
        TextView killPointsChangeValue = view.getRootView().findViewById(R.id.killPointsChangeValue);

        TextView killsMatchValue = view.getRootView().findViewById(R.id.killsMatchValue);
        TextView dbnosMatchValue = view.getRootView().findViewById(R.id.dbnosMatchValue);
        TextView assistsMatchValue = view.getRootView().findViewById(R.id.assistsMatchValue);

        TextView damageDealtValue = view.getRootView().findViewById(R.id.damageDealtMatchValue);
        TextView longestKillMatchValue = view.getRootView().findViewById(R.id.longestKillMatchValue);

        TextView healsMatchValue = view.getRootView().findViewById(R.id.healsMatchValue);
        TextView boostsMatchValue = view.getRootView().findViewById(R.id.boostsMatchValue);
        TextView revivesMatchValue = view.getRootView().findViewById(R.id.revivesMatchValue);

        TextView ranMatchValue = view.getRootView().findViewById(R.id.ranMatchValue);
        TextView swamMatchValue = view.getRootView().findViewById(R.id.swamMatchValue);
        TextView rodeMatchValue = view.getRootView().findViewById(R.id.rodeMatchValue);

        // Find the corresponding stats for the player and match ref
        for (int i = 0; i < manager.players.size(); i++ ) {
            if (manager.players.get(i).getGamertag().equals(selectedGamertag)) {
                // Gather the match stats for the player and game mode and current match value
                MatchModel stats = manager.players.get(i).getMatches().get(selectedMatch);

                // Set the match details
                String statsMapName = stats.getMapName(); if (statsMapName == null) { statsMapName = "MAP-NAME"; }
                String statsGameMode = stats.getGameMode(); if (statsGameMode == null) { statsGameMode = "GAME_MODE"; }
                String statsDateTime = stats.getMatchDateTime(); if (statsDateTime == null) { statsDateTime = "X"; }

                matchDetailsValue.setText(GetMatchDetailsString(statsMapName, statsGameMode));
                dateTimeValue.setText(GetDateTimeString(statsDateTime));

                // Find the participant that matches the selected gamertag
                for (int j = 0; j < stats.getParticipantList().size(); j++) {
                    if(stats.getParticipantList().get(j).getGamertag().equals(selectedGamertag)) {

                        // Set the Placing table
                        Integer winPlace = stats.getParticipantList().get(j).getWinPlace();
                        Integer killPlace = stats.getParticipantList().get(j).getKillPlace();
                        winPlaceValue.setText(winPlace.toString());
                        killPlaceValue.setText(killPlace.toString());
                        switch (winPlace){
                            case 1:
                                winPlaceValue.setBackgroundResource(R.color.colorGold);
                                break;
                            case 2:
                                winPlaceValue.setBackgroundResource(R.color.colorSilver);
                                break;
                            case 3:
                                winPlaceValue.setBackgroundResource(R.color.colorBronze);
                                break;
                            default:
                                winPlaceValue.setBackgroundResource(R.color.colorPrimary);
                                break;
                        }
                        switch (killPlace){
                            case 1:
                                killPlaceValue.setBackgroundResource(R.color.colorGold);
                                break;
                            case 2:
                                killPlaceValue.setBackgroundResource(R.color.colorSilver);
                                break;
                            case 3:
                                killPlaceValue.setBackgroundResource(R.color.colorBronze);
                                break;
                            default:
                                killPlaceValue.setBackgroundResource(R.color.colorPrimary);
                                break;
                        }

                        // Set the Points table
                        winPointsValue.setText(Integer.toString(stats.getParticipantList().get(j).getWinPoints()));
                        killPointsValue.setText(Integer.toString(stats.getParticipantList().get(j).getKillPoints()));

                        // Set the Points Change table
                        winPointsChangeValue.setText(Double.toString(stats.getParticipantList().get(j).getWinPointsDelta()).substring(0,
                                Math.min(Double.toString(stats.getParticipantList().get(j).getWinPointsDelta()).length(), 5)));
                        killPointsChangeValue.setText(Double.toString(stats.getParticipantList().get(j).getKillPointsDelta()).substring(0,
                                Math.min(Double.toString(stats.getParticipantList().get(j).getKillPointsDelta()).length(), 5)));

                        // Set the Kills table
                        killsMatchValue.setText(Integer.toString(stats.getParticipantList().get(j).getKills()));
                        dbnosMatchValue.setText(Integer.toString(stats.getParticipantList().get(j).getDBNOs()));
                        assistsMatchValue.setText(Integer.toString(stats.getParticipantList().get(j).getAssists()));

                        // Set the Damage table
                        damageDealtValue.setText(Double.toString(stats.getParticipantList().get(j).getDamageDealt()).substring(0,
                                Math.min(Double.toString(stats.getParticipantList().get(j).getDamageDealt()).length(), 6)));
                        longestKillMatchValue.setText(Double.toString(stats.getParticipantList().get(j).getLongestKill()).substring(0,
                                Math.min(Double.toString(stats.getParticipantList().get(j).getLongestKill()).length(), 5)) + " m");

                        // Set the Heals table
                        healsMatchValue.setText(Integer.toString(stats.getParticipantList().get(j).getHeals()));
                        boostsMatchValue.setText(Integer.toString(stats.getParticipantList().get(j).getBoosts()));
                        revivesMatchValue.setText(Integer.toString(stats.getParticipantList().get(j).getRevives()));

                        // Set the Distance table
                        ranMatchValue.setText(Double.toString(stats.getParticipantList().get(j).getWalkDistance()).substring(0, Math.min(Double.toString(stats.getParticipantList().get(j).getWalkDistance()).length(), 4)) + " m");
                        swamMatchValue.setText(Double.toString(stats.getParticipantList().get(j).getSwimDistance()).substring(0, Math.min(Double.toString(stats.getParticipantList().get(j).getSwimDistance()).length(), 4)) + " m");
                        rodeMatchValue.setText(Double.toString(stats.getParticipantList().get(j).getRideDistance()).substring(0, Math.min(Double.toString(stats.getParticipantList().get(j).getRideDistance()).length(), 4)) + " m");
                    }
                }

            }
        }
    }

    public String GetMatchDetailsString (String rawMapName, String rawGameMode) {

        String matchString, mapName, gameMode, dateTime;

        // Cut the map name down to just the name
        if (rawMapName.length() > 1) {
            mapName = rawMapName.split("_")[0];
        } else {
            mapName = "MAP_NAME";
        }

        // Capitalise the first letter of the game mode
        if (rawGameMode.length() > 1) {
            gameMode = rawGameMode.substring(0, 1).toUpperCase() + rawGameMode.substring(1);
        }else {
            gameMode = "GAME_MODE";
        }

        // Set the match String
        matchString = "Map: " + mapName + "      Mode: " + gameMode;

        return matchString;
    }

    public String GetDateTimeString (String rawDateTime) {

        String dateTime;

        // Split the date and time, remove the formatting characters
        if (rawDateTime.length() > 1) {
            String[] splitDateTime = rawDateTime.split("T");

            // Reorder the date to dd-mm-yyyy
            String reorderedDate = "DATE";
            String[] splitDate = splitDateTime[0].split("-");
            reorderedDate = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];

            // Create the full date time string
            dateTime = "Date: " + reorderedDate + "      Start Time: " + splitDateTime[1].substring(0, splitDateTime[1].length() - 4);
        } else {
            dateTime = "DATE_TIME";
        }

        return dateTime;
    }
}
