package wxc.pubgtracker;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.Arrays;

public class LeaderboardFragment extends Fragment {
    private static final String TAG = "LeaderboardFragment";
    private static String selectedGameMode = "solo";
    private static String selectedSource = "Season";

    protected PUBGManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leaderboards_fragment, container, false);

        manager = MyProperties.getInstance().pubgManager;

        // Set the game mode spinner
        Spinner spinnerGM = view.findViewById(R.id.leaderboardsSpinner);
        ArrayAdapter<String> adapterGM = new ArrayAdapter(this.getContext(), R.layout.spinner_item, manager.gameModes);
        adapterGM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGM.setAdapter(adapterGM);
        spinnerGM.setOnItemSelectedListener(new LeaderboardSpinnerActivity());

        // Set the source spinner
        Spinner spinnerSource = view.findViewById(R.id.sourceSpinner);
        ArrayList<String> sources = new ArrayList<>(Arrays.asList("Season", "WXC"));
        ArrayAdapter<String> adapterSource = new ArrayAdapter(this.getContext(), R.layout.spinner_item, sources);
        adapterSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSource.setAdapter(adapterSource);
        spinnerSource.setOnItemSelectedListener(new SourceSpinnerActivity());

        return view;
    }

    public static class SourceSpinnerActivity extends LeaderboardFragment implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {

            selectedSource = parent.getItemAtPosition(pos).toString();
            if (selectedSource.equals("Season")) {
                UpdateView(view);
            }
            if (selectedSource.equals("WXC")) {
                UpdateViewWXC(view);
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public static class LeaderboardSpinnerActivity extends LeaderboardFragment implements AdapterView.OnItemSelectedListener {

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

        // Create stats holders for each player
        SeasonModel appleStats = new SeasonModel(),
                    chunkStats = new SeasonModel(),
                    jellyStats = new SeasonModel(),
                    jpStats = new SeasonModel();

        // Enable the gamemode spinner when viewing WXC stats
        Spinner spinnerGM = view.getRootView().findViewById(R.id.leaderboardsSpinner);
        spinnerGM.setEnabled(true);
        TextView spinnerText = (TextView) spinnerGM.getChildAt(0);
        spinnerText.setText(selectedGameMode.substring(0, 1).toUpperCase() + selectedGameMode.substring(1));

        // Define all the text view items to be updated
        TextView killsColumn1 = view.getRootView().findViewById(R.id.killsColumn1);
        TextView killsColumn2 = view.getRootView().findViewById(R.id.killsColumn2);
        TextView killsColumn3 = view.getRootView().findViewById(R.id.killsColumn3);
        TextView killsColumn4 = view.getRootView().findViewById(R.id.killsColumn4);
        ArrayList<TextView> killsColumns = new ArrayList<>(Arrays.asList(killsColumn1, killsColumn2, killsColumn3, killsColumn4));

        TextView kdColumn1 = view.getRootView().findViewById(R.id.kdColumn1);
        TextView kdColumn2 = view.getRootView().findViewById(R.id.kdColumn2);
        TextView kdColumn3 = view.getRootView().findViewById(R.id.kdColumn3);
        TextView kdColumn4 = view.getRootView().findViewById(R.id.kdColumn4);
        ArrayList<TextView> kdColumns = new ArrayList<>(Arrays.asList(kdColumn1, kdColumn2, kdColumn3, kdColumn4));

        TextView avgDamageColumn1 = view.getRootView().findViewById(R.id.damageDealtColumn1);
        TextView avgDamageColumn2 = view.getRootView().findViewById(R.id.damageDealtColumn2);
        TextView avgDamageColumn3 = view.getRootView().findViewById(R.id.damageDealtColumn3);
        TextView avgDamageColumn4 = view.getRootView().findViewById(R.id.damageDealtColumn4);
        ArrayList<TextView> avgDamageColumns = new ArrayList<>(Arrays.asList(avgDamageColumn1, avgDamageColumn2, avgDamageColumn3, avgDamageColumn4));

        TextView longestKillColumn1 = view.getRootView().findViewById(R.id.longestKillColumn1);
        TextView longestKillColumn2 = view.getRootView().findViewById(R.id.longestKillColumn2);
        TextView longestKillColumn3 = view.getRootView().findViewById(R.id.longestKillColumn3);
        TextView longestKillColumn4 = view.getRootView().findViewById(R.id.longestKillColumn4);
        ArrayList<TextView> longestKillColumns = new ArrayList<>(Arrays.asList(longestKillColumn1, longestKillColumn2, longestKillColumn3, longestKillColumn4));

        TextView mostKillsColumn1 = view.getRootView().findViewById(R.id.mostKillsColumn1);
        TextView mostKillsColumn2 = view.getRootView().findViewById(R.id.mostKillsColumn2);
        TextView mostKillsColumn3 = view.getRootView().findViewById(R.id.mostKillsColumn3);
        TextView mostKillsColumn4 = view.getRootView().findViewById(R.id.mostKillsColumn4);
        ArrayList<TextView> mostKillsColumns = new ArrayList<>(Arrays.asList(mostKillsColumn1, mostKillsColumn2, mostKillsColumn3, mostKillsColumn4));

        TextView headshotsColumn1 = view.getRootView().findViewById(R.id.headshotsPercentColumn1);
        TextView headshotsColumn2 = view.getRootView().findViewById(R.id.headshotsPercentColumn2);
        TextView headshotsColumn3 = view.getRootView().findViewById(R.id.headshotsPercentColumn3);
        TextView headshotsColumn4 = view.getRootView().findViewById(R.id.headshotsPercentColumn4);
        ArrayList<TextView> headshotsColumns = new ArrayList<>(Arrays.asList(headshotsColumn1, headshotsColumn2, headshotsColumn3, headshotsColumn4));

        TextView avgSurvivedColumn1 = view.getRootView().findViewById(R.id.avgSurvivedColumn1);
        TextView avgSurvivedColumn2 = view.getRootView().findViewById(R.id.avgSurvivedColumn2);
        TextView avgSurvivedColumn3 = view.getRootView().findViewById(R.id.avgSurvivedColumn3);
        TextView avgSurvivedColumn4 = view.getRootView().findViewById(R.id.avgSurvivedColumn4);
        ArrayList<TextView> avgSurvivedColumns = new ArrayList<>(Arrays.asList(avgSurvivedColumn1, avgSurvivedColumn2, avgSurvivedColumn3, avgSurvivedColumn4));

        TextView roadKillsColumn1 = view.getRootView().findViewById(R.id.roadKillsColumn1);
        TextView roadKillsColumn2 = view.getRootView().findViewById(R.id.roadKillsColumn2);
        TextView roadKillsColumn3 = view.getRootView().findViewById(R.id.roadKillsColumn3);
        TextView roadKillsColumn4 = view.getRootView().findViewById(R.id.roadKillsColumn4);
        ArrayList<TextView> roadKillsColumns = new ArrayList<>(Arrays.asList(roadKillsColumn1, roadKillsColumn2, roadKillsColumn3, roadKillsColumn4));

        TextView avgAssistsColumn1 = view.getRootView().findViewById(R.id.assistsColumn1);
        TextView avgAssistsColumn2 = view.getRootView().findViewById(R.id.assistsColumn2);
        TextView avgAssistsColumn3 = view.getRootView().findViewById(R.id.assistsColumn3);
        TextView avgAssistsColumn4 = view.getRootView().findViewById(R.id.assistsColumn4);
        ArrayList<TextView> avgAssistsColumns = new ArrayList<>(Arrays.asList(avgAssistsColumn1, avgAssistsColumn2, avgAssistsColumn3, avgAssistsColumn4));

        TextView avgDBNOsColumn1 = view.getRootView().findViewById(R.id.avgDBNOsColumn1);
        TextView avgDBNOsColumn2 = view.getRootView().findViewById(R.id.avgDBNOsColumn2);
        TextView avgDBNOsColumn3 = view.getRootView().findViewById(R.id.avgDBNOsColumn3);
        TextView avgDBNOsColumn4 = view.getRootView().findViewById(R.id.avgDBNOsColumn4);
        ArrayList<TextView> avgDBNOsColumns = new ArrayList<>(Arrays.asList(avgDBNOsColumn1, avgDBNOsColumn2, avgDBNOsColumn3, avgDBNOsColumn4));

        TextView teamKillsColumn1 = view.getRootView().findViewById(R.id.teamKillsColumn1);
        TextView teamKillsColumn2 = view.getRootView().findViewById(R.id.teamKillsColumn2);
        TextView teamKillsColumn3 = view.getRootView().findViewById(R.id.teamKillsColumn3);
        TextView teamKillsColumn4 = view.getRootView().findViewById(R.id.teamKillsColumn4);
        ArrayList<TextView> teamKillsColumns = new ArrayList<>(Arrays.asList(teamKillsColumn1, teamKillsColumn2, teamKillsColumn3, teamKillsColumn4));

        TextView winPercentColumn1 = view.getRootView().findViewById(R.id.winsPercentColumn1);
        TextView winPercentColumn2 = view.getRootView().findViewById(R.id.winsPercentColumn2);
        TextView winPercentColumn3 = view.getRootView().findViewById(R.id.winsPercentColumn3);
        TextView winPercentColumn4 = view.getRootView().findViewById(R.id.winsPercentColumn4);
        ArrayList<TextView> winPercentColumns = new ArrayList<>(Arrays.asList(winPercentColumn1, winPercentColumn2, winPercentColumn3, winPercentColumn4));

        TextView top10sPercentColumn1 = view.getRootView().findViewById(R.id.top10sPercentColumn1);
        TextView top10sPercentColumn2 = view.getRootView().findViewById(R.id.top10sPercentColumn2);
        TextView top10sPercentColumn3 = view.getRootView().findViewById(R.id.top10sPercentColumn3);
        TextView top10sPercentColumn4 = view.getRootView().findViewById(R.id.top10sPercentColumn4);
        ArrayList<TextView> top10sPercentColumns = new ArrayList<>(Arrays.asList(top10sPercentColumn1, top10sPercentColumn2, top10sPercentColumn3, top10sPercentColumn4));

        // Store the stats for the selected game mode for each player
        for (int i = 0; i < manager.players.size(); i++ ) {
            for (int j = 0; j < manager.players.get(i).getStats().size(); j++) {
                if (manager.players.get(i).getStats().get(j).getGameMode().equals(selectedGameMode)) {
                    switch (manager.players.get(i).getGamertag()) {
                        case "Applepie2":
                            appleStats = manager.players.get(i).getStats().get(j);
                            break;
                        case "HeyChunk":
                            chunkStats = manager.players.get(i).getStats().get(j);
                            break;
                        case "JellyFilledFun":
                            jellyStats = manager.players.get(i).getStats().get(j);
                            break;
                        case "JP Argyle2":
                            jpStats = manager.players.get(i).getStats().get(j);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        // Create a working array for groups of stats to be compared and one for results
        ArrayList<Integer> playerStats;
        ArrayList<String> playerStatsString;
        ArrayList<Integer> playerPositions;

        // Determine the Kills positions and set the result
        playerStats = new ArrayList<>(Arrays.asList(appleStats.getKills(), chunkStats.getKills(), jellyStats.getKills(), jpStats.getKills()));
        playerStatsString = new ArrayList<>(Arrays.asList(String.valueOf(appleStats.getKills()), String.valueOf(chunkStats.getKills()), String.valueOf(jellyStats.getKills()), String.valueOf(jpStats.getKills())));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(killsColumns, playerPositions, playerStatsString);

        // Determine the K/D positions and set the result
        // NOTE: As the sorting method only accepts integers, the KD values are increased by 100 and truncated.
        Double appleKD = (Double.valueOf(appleStats.getKills()) / Double.valueOf(appleStats.getRoundsPlayed() - appleStats.getWins()));
        Double chunkKD = (Double.valueOf(chunkStats.getKills()) / Double.valueOf(chunkStats.getRoundsPlayed() - chunkStats.getWins()));
        Double jellyKD = (Double.valueOf(jellyStats.getKills()) / Double.valueOf(jellyStats.getRoundsPlayed() - jellyStats.getWins()));
        Double jpKD = (Double.valueOf(jpStats.getKills()) / Double.valueOf(jpStats.getRoundsPlayed() - jpStats.getWins()));
        playerStats = new ArrayList<>(Arrays.asList((int)(appleKD * 100), (int)(chunkKD * 100), (int)(jellyKD * 100), (int)(jpKD * 100)));
        String appleKDString = Double.toString(appleKD).substring(0, Math.min(appleKD.toString().length(), 4));
        String chunkKDString = Double.toString(chunkKD).substring(0, Math.min(chunkKD.toString().length(), 4));
        String jellyKDString = Double.toString(jellyKD).substring(0, Math.min(jellyKD.toString().length(), 4));
        String jpKDString = Double.toString(jpKD).substring(0, Math.min(jpKD.toString().length(), 4));
        playerStatsString = new ArrayList<>(Arrays.asList(appleKDString, chunkKDString, jellyKDString, jpKDString));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(kdColumns, playerPositions, playerStatsString);

        // Determine the Average Damage positions and set the result
        Double appleAvgDamage = (Double.valueOf(appleStats.getDamageDealt()) / Double.valueOf(appleStats.getRoundsPlayed()));
        Double chunkAvgDamage = (Double.valueOf(chunkStats.getDamageDealt()) / Double.valueOf(chunkStats.getRoundsPlayed()));
        Double jellyAvgDamage = (Double.valueOf(jellyStats.getDamageDealt()) / Double.valueOf(jellyStats.getRoundsPlayed()));
        Double jpAvgDamage = (Double.valueOf(jpStats.getDamageDealt()) / Double.valueOf(jpStats.getRoundsPlayed()));
        playerStats = new ArrayList<>(Arrays.asList((int)(appleAvgDamage + 0), (int)(chunkAvgDamage + 0), (int)(jellyAvgDamage + 0), (int)(jpAvgDamage + 0)));
        playerStatsString = new ArrayList<>(Arrays.asList(appleAvgDamage.toString().substring(0, Math.min(appleAvgDamage.toString().length(), 5)),
                                                          chunkAvgDamage.toString().substring(0, Math.min(chunkAvgDamage.toString().length(), 5)),
                                                          jellyAvgDamage.toString().substring(0, Math.min(jellyAvgDamage.toString().length(), 5)),
                                                          jpAvgDamage.toString().substring(0, Math.min(jpAvgDamage.toString().length(), 5))));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(avgDamageColumns, playerPositions, playerStatsString);

        // Determine the Longest Kill positions and set the result
        playerStats = new ArrayList<>(Arrays.asList((int)appleStats.getLongestKill(),
                                                    (int)chunkStats.getLongestKill(),
                                                    (int)jellyStats.getLongestKill(),
                                                    (int)jpStats.getLongestKill()));
        playerStatsString = new ArrayList<>(Arrays.asList(Double.toString(appleStats.getLongestKill()).substring(0, Math.min(Double.toString(appleStats.getLongestKill()).length(), 5)) + " m",
                                                          Double.toString(chunkStats.getLongestKill()).substring(0, Math.min(Double.toString(chunkStats.getLongestKill()).length(), 5)) + " m",
                                                          Double.toString(jellyStats.getLongestKill()).substring(0, Math.min(Double.toString(jellyStats.getLongestKill()).length(), 5)) + " m",
                                                          Double.toString(jpStats.getLongestKill()).substring(0, Math.min(Double.toString(jpStats.getLongestKill()).length(), 5)) + " m"));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(longestKillColumns, playerPositions, playerStatsString);

        // Determine the Most Kills positions and set the result
        playerStats = new ArrayList<>(Arrays.asList(appleStats.getRoundMostKills(), chunkStats.getRoundMostKills(), jellyStats.getRoundMostKills(), jpStats.getRoundMostKills()));
        playerStatsString = new ArrayList<>(Arrays.asList(Integer.toString(appleStats.getRoundMostKills()),
                                                          Integer.toString(chunkStats.getRoundMostKills()),
                                                          Integer.toString(jellyStats.getRoundMostKills()),
                                                          Integer.toString(jpStats.getRoundMostKills())));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(mostKillsColumns, playerPositions, playerStatsString);

        // Determine the Headshot Percent positions and set the result
        Double appleHeadshotPercent = (Double.valueOf(appleStats.getHeadshotKills()) / Double.valueOf(appleStats.getKills())) * 100;
        Double chunkHeadshotPercent = (Double.valueOf(chunkStats.getHeadshotKills()) / Double.valueOf(chunkStats.getKills())) * 100;
        Double jellyHeadshotPercent = (Double.valueOf(jellyStats.getHeadshotKills()) / Double.valueOf(jellyStats.getKills())) * 100;
        Double jpHeadshotPercent = (Double.valueOf(jpStats.getHeadshotKills()) / Double.valueOf(jpStats.getKills())) * 100;
        playerStats = new ArrayList<>(Arrays.asList((int)(appleHeadshotPercent * 100), (int)(chunkHeadshotPercent * 100), (int)(jellyHeadshotPercent * 100), (int)(jpHeadshotPercent * 100)));
        playerStatsString = new ArrayList<>(Arrays.asList(Double.toString(appleHeadshotPercent).substring(0, Math.min(Double.toString(appleHeadshotPercent).length(), 4)) + "%",
                                                          Double.toString(chunkHeadshotPercent).substring(0, Math.min(Double.toString(chunkHeadshotPercent).length(), 4)) + "%",
                                                          Double.toString(jellyHeadshotPercent).substring(0, Math.min(Double.toString(jellyHeadshotPercent).length(), 4)) + "%",
                                                          Double.toString(jpHeadshotPercent).substring(0, Math.min(Double.toString(jpHeadshotPercent).length(), 4)) + "%"));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(headshotsColumns, playerPositions, playerStatsString);

        // Determine the Avg Survival positions and set the result
        Integer appleAvgSurvived = (int)(appleStats.getTotalTimeSurvived() / appleStats.getRoundsPlayed());
        Integer chunkAvgSurvived = (int)(chunkStats.getTotalTimeSurvived() / chunkStats.getRoundsPlayed());
        Integer jellyAvgSurvived = (int)(jellyStats.getTotalTimeSurvived() / jellyStats.getRoundsPlayed());
        Integer jpAvgSurvived = (int)(jpStats.getTotalTimeSurvived() / jpStats.getRoundsPlayed());
        playerStats = new ArrayList<>(Arrays.asList(appleAvgSurvived, chunkAvgSurvived, jellyAvgSurvived, jpAvgSurvived));
        Integer minutes = Math.round(appleAvgSurvived) / 60;
        Integer seconds = Math.round(appleAvgSurvived) % 60;
        String appleAvgSurvivedString = minutes.toString() + ":" + seconds.toString();
        minutes = Math.round(chunkAvgSurvived) / 60;
        seconds = Math.round(chunkAvgSurvived) % 60;
        String chunkAvgSurvivedString = minutes.toString() + ":" + seconds.toString();
        minutes = Math.round(jellyAvgSurvived) / 60;
        seconds = Math.round(jellyAvgSurvived) % 60;
        String jellyAvgSurvivedString = minutes.toString() + ":" + seconds.toString();
        minutes = Math.round(jpAvgSurvived) / 60;
        seconds = Math.round(jpAvgSurvived) % 60;
        String jpAvgSurvivedString = minutes.toString() + ":" + seconds.toString();
        playerStatsString = new ArrayList<>(Arrays.asList(appleAvgSurvivedString, chunkAvgSurvivedString, jellyAvgSurvivedString, jpAvgSurvivedString));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(avgSurvivedColumns, playerPositions, playerStatsString);

        // Determine the Road Kills positions and set the result
        playerStats = new ArrayList<>(Arrays.asList(appleStats.getRoadKills(), chunkStats.getRoadKills(), jellyStats.getRoadKills(), jpStats.getRoadKills()));
        playerStatsString = new ArrayList<>(Arrays.asList(Integer.toString(appleStats.getRoadKills()),
                                                          Integer.toString(chunkStats.getRoadKills()),
                                                          Integer.toString(jellyStats.getRoadKills()),
                                                          Integer.toString(jpStats.getRoadKills())));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(roadKillsColumns, playerPositions, playerStatsString);

        // Determine the Average Assists positions and set the result
        Double appleAvgAssists = Double.valueOf(Double.valueOf(appleStats.getAssists()) / Double.valueOf(appleStats.getRoundsPlayed()));
        Double chunkAvgAssists = Double.valueOf(Double.valueOf(chunkStats.getAssists()) / Double.valueOf(chunkStats.getRoundsPlayed()));
        Double jellyAvgAssists = Double.valueOf(Double.valueOf(jellyStats.getAssists()) / Double.valueOf(jellyStats.getRoundsPlayed()));
        Double jpAvgAssists = Double.valueOf(Double.valueOf(jpStats.getAssists()) / Double.valueOf(jpStats.getRoundsPlayed()));
        playerStats = new ArrayList<>(Arrays.asList((int)(appleAvgAssists * 100), (int)(chunkAvgAssists * 100), (int)(jellyAvgAssists * 100), (int)(jpAvgAssists * 100)));
        playerStatsString = new ArrayList<>(Arrays.asList(Double.toString(appleAvgAssists).substring(0, Math.min(Double.toString(appleAvgAssists).length(), 4)),
                                                          Double.toString(chunkAvgAssists).substring(0, Math.min(Double.toString(chunkAvgAssists).length(), 4)),
                                                          Double.toString(jellyAvgAssists).substring(0, Math.min(Double.toString(jellyAvgAssists).length(), 4)),
                                                          Double.toString(jpAvgAssists).substring(0, Math.min(Double.toString(jpAvgAssists).length(), 4))));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(avgAssistsColumns, playerPositions, playerStatsString);

        // Determine the Average DBNOs positions and set the result
        Double appleAvgDBNOs = Double.valueOf(Double.valueOf(appleStats.getDBNOs()) / Double.valueOf(appleStats.getRoundsPlayed()));
        Double chunkAvgDBNOs = Double.valueOf(Double.valueOf(chunkStats.getDBNOs()) / Double.valueOf(chunkStats.getRoundsPlayed()));
        Double jellyAvgDBNOs = Double.valueOf(Double.valueOf(jellyStats.getDBNOs()) / Double.valueOf(jellyStats.getRoundsPlayed()));
        Double jpAvgDBNOs = Double.valueOf(Double.valueOf(jpStats.getDBNOs()) / Double.valueOf(jpStats.getRoundsPlayed()));
        playerStats = new ArrayList<>(Arrays.asList((int)(appleAvgDBNOs * 100), (int)(chunkAvgDBNOs * 100), (int)(jellyAvgDBNOs * 100), (int)(jpAvgDBNOs * 100)));
        playerStatsString = new ArrayList<>(Arrays.asList(Double.toString(appleAvgDBNOs).substring(0, Math.min(Double.toString(appleAvgDBNOs).length(), 4)),
                                                          Double.toString(chunkAvgDBNOs).substring(0, Math.min(Double.toString(chunkAvgDBNOs).length(), 4)),
                                                          Double.toString(jellyAvgDBNOs).substring(0, Math.min(Double.toString(jellyAvgDBNOs).length(), 4)),
                                                          Double.toString(jpAvgDBNOs).substring(0, Math.min(Double.toString(jpAvgDBNOs).length(), 4))));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(avgDBNOsColumns, playerPositions, playerStatsString);

        // Determine the Team Kills positions and set the result
        playerStats = new ArrayList<>(Arrays.asList(appleStats.getTeamKills(), chunkStats.getTeamKills(), jellyStats.getTeamKills(), jpStats.getTeamKills()));
        playerStatsString = new ArrayList<>(Arrays.asList(Integer.toString(appleStats.getTeamKills()),
                                                          Integer.toString(chunkStats.getTeamKills()),
                                                          Integer.toString(jellyStats.getTeamKills()),
                                                          Integer.toString(jpStats.getTeamKills())));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(teamKillsColumns, playerPositions, playerStatsString);
        
        // Determine the Win Percentage positions and set the result
        Double appleWinPercent = Double.valueOf(Double.valueOf(appleStats.getWins()) / Double.valueOf(appleStats.getRoundsPlayed()) * 100);
        Double chunkWinPercent = Double.valueOf(Double.valueOf(chunkStats.getWins()) / Double.valueOf(chunkStats.getRoundsPlayed()) * 100);
        Double jellyWinPercent = Double.valueOf(Double.valueOf(jellyStats.getWins()) / Double.valueOf(jellyStats.getRoundsPlayed()) * 100);
        Double jpWinPercent = Double.valueOf(Double.valueOf(jpStats.getWins()) / Double.valueOf(jpStats.getRoundsPlayed()) * 100);
        playerStats = new ArrayList<>(Arrays.asList((int)(appleWinPercent * 100), (int)(chunkWinPercent * 100), (int)(jellyWinPercent * 100), (int)(jpWinPercent * 100)));
        playerStatsString = new ArrayList<>(Arrays.asList(Double.toString(appleWinPercent).substring(0, Math.min(Double.toString(appleWinPercent).length(), 4)) + "%",
                                                          Double.toString(chunkWinPercent).substring(0, Math.min(Double.toString(chunkWinPercent).length(), 4))+ "%",
                                                          Double.toString(jellyWinPercent).substring(0, Math.min(Double.toString(jellyWinPercent).length(), 4))+ "%",
                                                          Double.toString(jpWinPercent).substring(0, Math.min(Double.toString(jpWinPercent).length(), 4)) + "%"));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(winPercentColumns, playerPositions, playerStatsString);
        
        // Determine the Top 10 Percentage positions and set the result
        Double appleTop10Percent = Double.valueOf(Double.valueOf(appleStats.getTop10s()) / Double.valueOf(appleStats.getRoundsPlayed()) * 100);
        Double chunkTop10Percent = Double.valueOf(Double.valueOf(chunkStats.getTop10s()) / Double.valueOf(chunkStats.getRoundsPlayed()) * 100);
        Double jellyTop10Percent = Double.valueOf(Double.valueOf(jellyStats.getTop10s()) / Double.valueOf(jellyStats.getRoundsPlayed()) * 100);
        Double jpTop10Percent = Double.valueOf(Double.valueOf(jpStats.getTop10s()) / Double.valueOf(jpStats.getRoundsPlayed()) * 100);
        playerStats = new ArrayList<>(Arrays.asList((int)(appleTop10Percent * 100), (int)(chunkTop10Percent * 100), (int)(jellyTop10Percent * 100), (int)(jpTop10Percent * 100)));
        playerStatsString = new ArrayList<>(Arrays.asList(Double.toString(appleTop10Percent).substring(0, Math.min(Double.toString(appleTop10Percent).length(), 4)) + "%",
                Double.toString(chunkTop10Percent).substring(0, Math.min(Double.toString(chunkTop10Percent).length(), 4))+ "%",
                Double.toString(jellyTop10Percent).substring(0, Math.min(Double.toString(jellyTop10Percent).length(), 4))+ "%",
                Double.toString(jpTop10Percent).substring(0, Math.min(Double.toString(jpTop10Percent).length(), 4)) + "%"));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(top10sPercentColumns, playerPositions, playerStatsString);
    }

    public void UpdateViewWXC (View view) {

        // Make sure the manager is updated
        manager = MyProperties.getInstance().pubgManager;

        // Create stats holders for each player
        MatchModel.Participant appleStats = manager.wxcStats.get(0).getParticipantList().get(0),
                chunkStats = manager.wxcStats.get(1).getParticipantList().get(0),
                jellyStats = manager.wxcStats.get(2).getParticipantList().get(0),
                jpStats = manager.wxcStats.get(3).getParticipantList().get(0);

        // Disable the gamemode spinner when viewing WXC stats
        Spinner spinnerGM = view.getRootView().findViewById(R.id.leaderboardsSpinner);
        spinnerGM.setEnabled(false);
        TextView spinnerText = (TextView) spinnerGM.getChildAt(0);
        spinnerText.setText("");

        // Define all the text view items to be updated
        TextView killsColumn1 = view.getRootView().findViewById(R.id.killsColumn1);
        TextView killsColumn2 = view.getRootView().findViewById(R.id.killsColumn2);
        TextView killsColumn3 = view.getRootView().findViewById(R.id.killsColumn3);
        TextView killsColumn4 = view.getRootView().findViewById(R.id.killsColumn4);
        ArrayList<TextView> killsColumns = new ArrayList<>(Arrays.asList(killsColumn1, killsColumn2, killsColumn3, killsColumn4));

        TextView kdColumn1 = view.getRootView().findViewById(R.id.kdColumn1);
        TextView kdColumn2 = view.getRootView().findViewById(R.id.kdColumn2);
        TextView kdColumn3 = view.getRootView().findViewById(R.id.kdColumn3);
        TextView kdColumn4 = view.getRootView().findViewById(R.id.kdColumn4);
        ArrayList<TextView> kdColumns = new ArrayList<>(Arrays.asList(kdColumn1, kdColumn2, kdColumn3, kdColumn4));

        TextView avgDamageColumn1 = view.getRootView().findViewById(R.id.damageDealtColumn1);
        TextView avgDamageColumn2 = view.getRootView().findViewById(R.id.damageDealtColumn2);
        TextView avgDamageColumn3 = view.getRootView().findViewById(R.id.damageDealtColumn3);
        TextView avgDamageColumn4 = view.getRootView().findViewById(R.id.damageDealtColumn4);
        ArrayList<TextView> avgDamageColumns = new ArrayList<>(Arrays.asList(avgDamageColumn1, avgDamageColumn2, avgDamageColumn3, avgDamageColumn4));

        TextView longestKillColumn1 = view.getRootView().findViewById(R.id.longestKillColumn1);
        TextView longestKillColumn2 = view.getRootView().findViewById(R.id.longestKillColumn2);
        TextView longestKillColumn3 = view.getRootView().findViewById(R.id.longestKillColumn3);
        TextView longestKillColumn4 = view.getRootView().findViewById(R.id.longestKillColumn4);
        ArrayList<TextView> longestKillColumns = new ArrayList<>(Arrays.asList(longestKillColumn1, longestKillColumn2, longestKillColumn3, longestKillColumn4));

        TextView mostKillsColumn1 = view.getRootView().findViewById(R.id.mostKillsColumn1);
        TextView mostKillsColumn2 = view.getRootView().findViewById(R.id.mostKillsColumn2);
        TextView mostKillsColumn3 = view.getRootView().findViewById(R.id.mostKillsColumn3);
        TextView mostKillsColumn4 = view.getRootView().findViewById(R.id.mostKillsColumn4);
        ArrayList<TextView> mostKillsColumns = new ArrayList<>(Arrays.asList(mostKillsColumn1, mostKillsColumn2, mostKillsColumn3, mostKillsColumn4));

        TextView headshotsColumn1 = view.getRootView().findViewById(R.id.headshotsPercentColumn1);
        TextView headshotsColumn2 = view.getRootView().findViewById(R.id.headshotsPercentColumn2);
        TextView headshotsColumn3 = view.getRootView().findViewById(R.id.headshotsPercentColumn3);
        TextView headshotsColumn4 = view.getRootView().findViewById(R.id.headshotsPercentColumn4);
        ArrayList<TextView> headshotsColumns = new ArrayList<>(Arrays.asList(headshotsColumn1, headshotsColumn2, headshotsColumn3, headshotsColumn4));

        TextView avgSurvivedColumn1 = view.getRootView().findViewById(R.id.avgSurvivedColumn1);
        TextView avgSurvivedColumn2 = view.getRootView().findViewById(R.id.avgSurvivedColumn2);
        TextView avgSurvivedColumn3 = view.getRootView().findViewById(R.id.avgSurvivedColumn3);
        TextView avgSurvivedColumn4 = view.getRootView().findViewById(R.id.avgSurvivedColumn4);
        ArrayList<TextView> avgSurvivedColumns = new ArrayList<>(Arrays.asList(avgSurvivedColumn1, avgSurvivedColumn2, avgSurvivedColumn3, avgSurvivedColumn4));

        TextView roadKillsColumn1 = view.getRootView().findViewById(R.id.roadKillsColumn1);
        TextView roadKillsColumn2 = view.getRootView().findViewById(R.id.roadKillsColumn2);
        TextView roadKillsColumn3 = view.getRootView().findViewById(R.id.roadKillsColumn3);
        TextView roadKillsColumn4 = view.getRootView().findViewById(R.id.roadKillsColumn4);
        ArrayList<TextView> roadKillsColumns = new ArrayList<>(Arrays.asList(roadKillsColumn1, roadKillsColumn2, roadKillsColumn3, roadKillsColumn4));

        TextView avgAssistsColumn1 = view.getRootView().findViewById(R.id.assistsColumn1);
        TextView avgAssistsColumn2 = view.getRootView().findViewById(R.id.assistsColumn2);
        TextView avgAssistsColumn3 = view.getRootView().findViewById(R.id.assistsColumn3);
        TextView avgAssistsColumn4 = view.getRootView().findViewById(R.id.assistsColumn4);
        ArrayList<TextView> avgAssistsColumns = new ArrayList<>(Arrays.asList(avgAssistsColumn1, avgAssistsColumn2, avgAssistsColumn3, avgAssistsColumn4));

        TextView avgDBNOsColumn1 = view.getRootView().findViewById(R.id.avgDBNOsColumn1);
        TextView avgDBNOsColumn2 = view.getRootView().findViewById(R.id.avgDBNOsColumn2);
        TextView avgDBNOsColumn3 = view.getRootView().findViewById(R.id.avgDBNOsColumn3);
        TextView avgDBNOsColumn4 = view.getRootView().findViewById(R.id.avgDBNOsColumn4);
        ArrayList<TextView> avgDBNOsColumns = new ArrayList<>(Arrays.asList(avgDBNOsColumn1, avgDBNOsColumn2, avgDBNOsColumn3, avgDBNOsColumn4));

        TextView teamKillsColumn1 = view.getRootView().findViewById(R.id.teamKillsColumn1);
        TextView teamKillsColumn2 = view.getRootView().findViewById(R.id.teamKillsColumn2);
        TextView teamKillsColumn3 = view.getRootView().findViewById(R.id.teamKillsColumn3);
        TextView teamKillsColumn4 = view.getRootView().findViewById(R.id.teamKillsColumn4);
        ArrayList<TextView> teamKillsColumns = new ArrayList<>(Arrays.asList(teamKillsColumn1, teamKillsColumn2, teamKillsColumn3, teamKillsColumn4));

        TextView winPercentColumn1 = view.getRootView().findViewById(R.id.winsPercentColumn1);
        TextView winPercentColumn2 = view.getRootView().findViewById(R.id.winsPercentColumn2);
        TextView winPercentColumn3 = view.getRootView().findViewById(R.id.winsPercentColumn3);
        TextView winPercentColumn4 = view.getRootView().findViewById(R.id.winsPercentColumn4);
        ArrayList<TextView> winPercentColumns = new ArrayList<>(Arrays.asList(winPercentColumn1, winPercentColumn2, winPercentColumn3, winPercentColumn4));

        TextView top10sPercentColumn1 = view.getRootView().findViewById(R.id.top10sPercentColumn1);
        TextView top10sPercentColumn2 = view.getRootView().findViewById(R.id.top10sPercentColumn2);
        TextView top10sPercentColumn3 = view.getRootView().findViewById(R.id.top10sPercentColumn3);
        TextView top10sPercentColumn4 = view.getRootView().findViewById(R.id.top10sPercentColumn4);
        ArrayList<TextView> top10sPercentColumns = new ArrayList<>(Arrays.asList(top10sPercentColumn1, top10sPercentColumn2, top10sPercentColumn3, top10sPercentColumn4));


        // Create a working array for groups of stats to be compared and one for results
        ArrayList<Integer> playerStats;
        ArrayList<String> playerStatsString;
        ArrayList<Integer> playerPositions;

        // Determine the Kills positions and set the result
        playerStats = new ArrayList<>(Arrays.asList(appleStats.getKills(), chunkStats.getKills(), jellyStats.getKills(), jpStats.getKills()));
        playerStatsString = new ArrayList<>(Arrays.asList(String.valueOf(appleStats.getKills()), String.valueOf(chunkStats.getKills()), String.valueOf(jellyStats.getKills()), String.valueOf(jpStats.getKills())));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(killsColumns, playerPositions, playerStatsString);

        // Determine the K/D positions and set the result
        // NOTE: As the sorting method only accepts integers, the KD values are increased by 100 and truncated.
        Double appleKD = (Double.valueOf(appleStats.getKills()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double chunkKD = (Double.valueOf(chunkStats.getKills()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double jellyKD = (Double.valueOf(jellyStats.getKills()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double jpKD = (Double.valueOf(jpStats.getKills()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        playerStats = new ArrayList<>(Arrays.asList((int)(appleKD * 100), (int)(chunkKD * 100), (int)(jellyKD * 100), (int)(jpKD * 100)));
        String appleKDString = Double.toString(appleKD).substring(0, Math.min(appleKD.toString().length(), 4));
        String chunkKDString = Double.toString(chunkKD).substring(0, Math.min(chunkKD.toString().length(), 4));
        String jellyKDString = Double.toString(jellyKD).substring(0, Math.min(jellyKD.toString().length(), 4));
        String jpKDString = Double.toString(jpKD).substring(0, Math.min(jpKD.toString().length(), 4));
        playerStatsString = new ArrayList<>(Arrays.asList(appleKDString, chunkKDString, jellyKDString, jpKDString));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(kdColumns, playerPositions, playerStatsString);

        // Determine the Average Damage positions and set the result
        Double appleAvgDamage = (Double.valueOf(appleStats.getDamageDealt()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double chunkAvgDamage = (Double.valueOf(chunkStats.getDamageDealt()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double jellyAvgDamage = (Double.valueOf(jellyStats.getDamageDealt()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double jpAvgDamage = (Double.valueOf(jpStats.getDamageDealt()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        playerStats = new ArrayList<>(Arrays.asList((int)(appleAvgDamage + 0), (int)(chunkAvgDamage + 0), (int)(jellyAvgDamage + 0), (int)(jpAvgDamage + 0)));
        playerStatsString = new ArrayList<>(Arrays.asList(appleAvgDamage.toString().substring(0, Math.min(appleAvgDamage.toString().length(), 5)),
                chunkAvgDamage.toString().substring(0, Math.min(chunkAvgDamage.toString().length(), 5)),
                jellyAvgDamage.toString().substring(0, Math.min(jellyAvgDamage.toString().length(), 5)),
                jpAvgDamage.toString().substring(0, Math.min(jpAvgDamage.toString().length(), 5))));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(avgDamageColumns, playerPositions, playerStatsString);

        // Determine the Longest Kill positions and set the result
        playerStats = new ArrayList<>(Arrays.asList((int)appleStats.getLongestKill(),
                (int)chunkStats.getLongestKill(),
                (int)jellyStats.getLongestKill(),
                (int)jpStats.getLongestKill()));
        playerStatsString = new ArrayList<>(Arrays.asList(Double.toString(appleStats.getLongestKill()).substring(0, Math.min(Double.toString(appleStats.getLongestKill()).length(), 5)) + " m",
                Double.toString(chunkStats.getLongestKill()).substring(0, Math.min(Double.toString(chunkStats.getLongestKill()).length(), 5)) + " m",
                Double.toString(jellyStats.getLongestKill()).substring(0, Math.min(Double.toString(jellyStats.getLongestKill()).length(), 5)) + " m",
                Double.toString(jpStats.getLongestKill()).substring(0, Math.min(Double.toString(jpStats.getLongestKill()).length(), 5)) + " m"));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(longestKillColumns, playerPositions, playerStatsString);

        // Determine the Most Kills positions and set the result
        playerStats = new ArrayList<>(Arrays.asList(appleStats.getKillPlace(), chunkStats.getKillPlace(), jellyStats.getKillPlace(), jpStats.getKillPlace()));
        playerStatsString = new ArrayList<>(Arrays.asList(Integer.toString(appleStats.getKillPlace()),
                Integer.toString(chunkStats.getKillPlace()),
                Integer.toString(jellyStats.getKillPlace()),
                Integer.toString(jpStats.getKillPlace())));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(mostKillsColumns, playerPositions, playerStatsString);

        // Determine the Headshot Percent positions and set the result
        Double appleHeadshotPercent = (Double.valueOf(appleStats.getHeadshotKills()) / Double.valueOf(appleStats.getKills())) * 100;
        Double chunkHeadshotPercent = (Double.valueOf(chunkStats.getHeadshotKills()) / Double.valueOf(chunkStats.getKills())) * 100;
        Double jellyHeadshotPercent = (Double.valueOf(jellyStats.getHeadshotKills()) / Double.valueOf(jellyStats.getKills())) * 100;
        Double jpHeadshotPercent = (Double.valueOf(jpStats.getHeadshotKills()) / Double.valueOf(jpStats.getKills())) * 100;
        playerStats = new ArrayList<>(Arrays.asList((int)(appleHeadshotPercent * 100), (int)(chunkHeadshotPercent * 100), (int)(jellyHeadshotPercent * 100), (int)(jpHeadshotPercent * 100)));
        playerStatsString = new ArrayList<>(Arrays.asList(Double.toString(appleHeadshotPercent).substring(0, Math.min(Double.toString(appleHeadshotPercent).length(), 4)) + "%",
                Double.toString(chunkHeadshotPercent).substring(0, Math.min(Double.toString(chunkHeadshotPercent).length(), 4)) + "%",
                Double.toString(jellyHeadshotPercent).substring(0, Math.min(Double.toString(jellyHeadshotPercent).length(), 4)) + "%",
                Double.toString(jpHeadshotPercent).substring(0, Math.min(Double.toString(jpHeadshotPercent).length(), 4)) + "%"));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(headshotsColumns, playerPositions, playerStatsString);

        // Determine the Avg Survival positions and set the result
        Integer appleAvgSurvived = (int)(appleStats.getTimeSurvived() / manager.wxcStats.get(0).getDay());
        Integer chunkAvgSurvived = (int)(chunkStats.getTimeSurvived() / manager.wxcStats.get(0).getDay());
        Integer jellyAvgSurvived = (int)(jellyStats.getTimeSurvived() / manager.wxcStats.get(0).getDay());
        Integer jpAvgSurvived = (int)(jpStats.getTimeSurvived() / manager.wxcStats.get(0).getDay());
        playerStats = new ArrayList<>(Arrays.asList(appleAvgSurvived, chunkAvgSurvived, jellyAvgSurvived, jpAvgSurvived));
        Integer minutes = Math.round(appleAvgSurvived) / 60;
        Integer seconds = Math.round(appleAvgSurvived) % 60;
        String appleAvgSurvivedString = minutes.toString() + ":" + seconds.toString();
        minutes = Math.round(chunkAvgSurvived) / 60;
        seconds = Math.round(chunkAvgSurvived) % 60;
        String chunkAvgSurvivedString = minutes.toString() + ":" + seconds.toString();
        minutes = Math.round(jellyAvgSurvived) / 60;
        seconds = Math.round(jellyAvgSurvived) % 60;
        String jellyAvgSurvivedString = minutes.toString() + ":" + seconds.toString();
        minutes = Math.round(jpAvgSurvived) / 60;
        seconds = Math.round(jpAvgSurvived) % 60;
        String jpAvgSurvivedString = minutes.toString() + ":" + seconds.toString();
        playerStatsString = new ArrayList<>(Arrays.asList(appleAvgSurvivedString, chunkAvgSurvivedString, jellyAvgSurvivedString, jpAvgSurvivedString));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(avgSurvivedColumns, playerPositions, playerStatsString);

        // Determine the Road Kills positions and set the result
        playerStats = new ArrayList<>(Arrays.asList(appleStats.getRoadKills(), chunkStats.getRoadKills(), jellyStats.getRoadKills(), jpStats.getRoadKills()));
        playerStatsString = new ArrayList<>(Arrays.asList(Integer.toString(appleStats.getRoadKills()),
                Integer.toString(chunkStats.getRoadKills()),
                Integer.toString(jellyStats.getRoadKills()),
                Integer.toString(jpStats.getRoadKills())));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(roadKillsColumns, playerPositions, playerStatsString);

        // Determine the Average Assists positions and set the result
        Double appleAvgAssists = Double.valueOf(Double.valueOf(appleStats.getAssists()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double chunkAvgAssists = Double.valueOf(Double.valueOf(chunkStats.getAssists()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double jellyAvgAssists = Double.valueOf(Double.valueOf(jellyStats.getAssists()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double jpAvgAssists = Double.valueOf(Double.valueOf(jpStats.getAssists()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        playerStats = new ArrayList<>(Arrays.asList((int)(appleAvgAssists * 100), (int)(chunkAvgAssists * 100), (int)(jellyAvgAssists * 100), (int)(jpAvgAssists * 100)));
        playerStatsString = new ArrayList<>(Arrays.asList(Double.toString(appleAvgAssists).substring(0, Math.min(Double.toString(appleAvgAssists).length(), 4)),
                Double.toString(chunkAvgAssists).substring(0, Math.min(Double.toString(chunkAvgAssists).length(), 4)),
                Double.toString(jellyAvgAssists).substring(0, Math.min(Double.toString(jellyAvgAssists).length(), 4)),
                Double.toString(jpAvgAssists).substring(0, Math.min(Double.toString(jpAvgAssists).length(), 4))));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(avgAssistsColumns, playerPositions, playerStatsString);

        // Determine the Average DBNOs positions and set the result
        Double appleAvgDBNOs = Double.valueOf(Double.valueOf(appleStats.getDBNOs()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double chunkAvgDBNOs = Double.valueOf(Double.valueOf(chunkStats.getDBNOs()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double jellyAvgDBNOs = Double.valueOf(Double.valueOf(jellyStats.getDBNOs()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        Double jpAvgDBNOs = Double.valueOf(Double.valueOf(jpStats.getDBNOs()) / Double.valueOf(manager.wxcStats.get(0).getDay()));
        playerStats = new ArrayList<>(Arrays.asList((int)(appleAvgDBNOs * 100), (int)(chunkAvgDBNOs * 100), (int)(jellyAvgDBNOs * 100), (int)(jpAvgDBNOs * 100)));
        playerStatsString = new ArrayList<>(Arrays.asList(Double.toString(appleAvgDBNOs).substring(0, Math.min(Double.toString(appleAvgDBNOs).length(), 4)),
                Double.toString(chunkAvgDBNOs).substring(0, Math.min(Double.toString(chunkAvgDBNOs).length(), 4)),
                Double.toString(jellyAvgDBNOs).substring(0, Math.min(Double.toString(jellyAvgDBNOs).length(), 4)),
                Double.toString(jpAvgDBNOs).substring(0, Math.min(Double.toString(jpAvgDBNOs).length(), 4))));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(avgDBNOsColumns, playerPositions, playerStatsString);

        // Determine the Team Kills positions and set the result
        playerStats = new ArrayList<>(Arrays.asList(appleStats.getTeamKills(), chunkStats.getTeamKills(), jellyStats.getTeamKills(), jpStats.getTeamKills()));
        playerStatsString = new ArrayList<>(Arrays.asList(Integer.toString(appleStats.getTeamKills()),
                Integer.toString(chunkStats.getTeamKills()),
                Integer.toString(jellyStats.getTeamKills()),
                Integer.toString(jpStats.getTeamKills())));
        playerPositions = SortPlayersByGreatest(playerStats);
        updateRow(teamKillsColumns, playerPositions, playerStatsString);

        // Determine the Win Percentage positions and set the result
        winPercentColumns.get(0).setText("N/A"); winPercentColumns.get(0).setBackgroundResource(R.color.colorPrimary);
        winPercentColumns.get(1).setText("N/A"); winPercentColumns.get(1).setBackgroundResource(R.color.colorPrimary);
        winPercentColumns.get(2).setText("N/A"); winPercentColumns.get(2).setBackgroundResource(R.color.colorPrimary);
        winPercentColumns.get(3).setText("N/A"); winPercentColumns.get(3).setBackgroundResource(R.color.colorPrimary);

        // Determine the Top 10 Percentage positions and set the result
        top10sPercentColumns.get(0).setText("N/A"); top10sPercentColumns.get(0).setBackgroundResource(R.color.colorPrimary);
        top10sPercentColumns.get(1).setText("N/A"); top10sPercentColumns.get(1).setBackgroundResource(R.color.colorPrimary);
        top10sPercentColumns.get(2).setText("N/A"); top10sPercentColumns.get(2).setBackgroundResource(R.color.colorPrimary);
        top10sPercentColumns.get(3).setText("N/A"); top10sPercentColumns.get(3).setBackgroundResource(R.color.colorPrimary);
    }

    private void updateRow(ArrayList<TextView> columns, ArrayList<Integer> playerPositions, ArrayList<String> playerStats) {
        // Update the table to show the positions
        for (Integer i = 0; i < playerPositions.size(); i++){
            columns.get(i).setText(playerStats.get(i));
            switch (playerPositions.get(i)){
                case 1:
                    columns.get(i).setBackgroundResource(R.color.colorGold);
                    break;
                case 2:
                    columns.get(i).setBackgroundResource(R.color.colorSilver);
                    break;
                case 3:
                    columns.get(i).setBackgroundResource(R.color.colorBronze);
                    break;
                default:
                    columns.get(i).setBackgroundResource(R.color.colorPrimary);
                    break;
            }
        }
    }

    private ArrayList<Integer> SortPlayersByGreatest (ArrayList<Integer> playerStats) {

        // Store the highest value for a pass
        Integer highestValue = -1;
        Integer lastHighestValue = -1;
        Integer highestValueIndex = -1;
        Integer lastPosition = 0;
        Boolean tie = false;

        // Set a return array for the positions
        ArrayList<Integer> playerPositions = new ArrayList<>(Arrays.asList(-1,-1,-1,-1));

        // Working array for manipulation
        ArrayList<Integer> workingPlayerStats = new ArrayList<>(playerStats);

        for (int pass = 0; pass < 4; pass++) {
            for (int i = 0; i < workingPlayerStats.size(); i++) {
                if (workingPlayerStats.get(i) >= highestValue) {
                    highestValue = workingPlayerStats.get(i);
                    highestValueIndex = i;
                    if (workingPlayerStats.get(i) == lastHighestValue) {
                        tie = true;
                    }
                }
            }
            if (!tie) { lastPosition++; }
            playerPositions.set(highestValueIndex, lastPosition);
            workingPlayerStats.set(highestValueIndex, -2);
            lastHighestValue = highestValue;
            highestValue = -1;
            tie = false;
        }

        return playerPositions;
    }
}
