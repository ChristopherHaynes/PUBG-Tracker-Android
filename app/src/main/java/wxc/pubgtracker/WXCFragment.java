package wxc.pubgtracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class WXCFragment extends Fragment {
    private static final String TAG = "WXCFragment";
    protected static ArrayList<MatchModel> wxcPlayerStats;  //0-Apple, 1-Chunk, 2-Jelly, 3-JP

    protected PUBGManager manager;

    protected static String wxcDate = "DD-MM-YYYY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wxc_fragment, container, false);

        manager = MyProperties.getInstance().pubgManager;
        wxcPlayerStats = new ArrayList<>(Arrays.asList(new MatchModel(), new MatchModel(),new MatchModel(),new MatchModel()));

        // Add a single participant to each match item
        for (int i = 0; i < wxcPlayerStats.size(); i++) {
            wxcPlayerStats.get(i).addParticipant();
        }

        // Compile the results from all WXC matches on the current or previous thursday
        CompileWXCStatsFromMatches();

        // Set the date of the wxc
        TextView wxcDateText = view.getRootView().findViewById(R.id.wxcDateText);
        wxcDateText.setText("WXC Date: " + wxcDate);

        // Set the medals
        SetMedals(view);

        return view;
    }

    public void CompileWXCStatsFromMatches() {

        // Make sure the manager is updated
        manager = MyProperties.getInstance().pubgManager;

        for (int i = 0; i < manager.players.size(); i++) {
            for (int j = 0; j < manager.players.get(i).getMatches().size(); j++) {
                // Find all matches for the current player which took place on the last thursday and have all wxc members as participants
                if (manager.players.get(i).getMatches().get(j).getDay() == 5 &&
                        manager.players.get(i).getMatches().get(j).getParticipantList().size() == 4) {

                        // Set the date of this wxc
                        if (wxcDate.equals("DD-MM-YYYY")) {
                            wxcDate = manager.players.get(i).getMatches().get(j).getMatchDateTime();

                            // Split the date and time, remove the formatting characters
                            if (wxcDate.length() > 1) {
                                String[] splitDateTime = wxcDate.split("T");

                                // Reorder the date to dd-mm-yyyy
                                String[] splitDate = splitDateTime[0].split("-");
                                wxcDate = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
                            }
                        }

                        //Create local reference type for the wxc participant
                        MatchModel.Participant wxcPlayer = wxcPlayerStats.get(i).getParticipantList().get(0);
                        Integer playerIndex = 0;
                        for (int k = 0; k < manager.players.get(i).getMatches().get(j).getParticipantList().size(); k++) {
                            if (manager.players.get(i).getMatches().get(j).getParticipantList().get(k).getGamertag().equals(manager.wxcGamertags.get(i))) {
                                playerIndex = k;
                            }
                        }

                        // Sum all the stats across the WXC matches
                        wxcPlayer.setAssists(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getAssists() + wxcPlayer.getAssists());
                        wxcPlayer.setBoosts(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getBoosts() + wxcPlayer.getBoosts());
                        wxcPlayer.setDBNOs(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getDBNOs() + wxcPlayer.getDBNOs());
                        wxcPlayer.setDamageDealt(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getDamageDealt() + wxcPlayer.getDamageDealt());
                        wxcPlayer.setHeadshotKills(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getHeadshotKills() + wxcPlayer.getHeadshotKills());
                        wxcPlayer.setHeals(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getHeals() + wxcPlayer.getHeals());
                        wxcPlayer.setKills(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getKills() + wxcPlayer.getKills());
                        wxcPlayer.setRevives(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getRevives() + wxcPlayer.getRevives());
                        wxcPlayer.setRideDistance(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getRideDistance() + wxcPlayer.getRideDistance());
                        wxcPlayer.setRoadKills(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getRoadKills() + wxcPlayer.getRoadKills());
                        wxcPlayer.setSwimDistance(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getSwimDistance() + wxcPlayer.getSwimDistance());
                        wxcPlayer.setTeamKills(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getTeamKills() + wxcPlayer.getTeamKills());
                        wxcPlayer.setTimeSurvived(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getTimeSurvived() + wxcPlayer.getTimeSurvived());
                        wxcPlayer.setVehiclesDestroyed(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getVehiclesDestroyed() + wxcPlayer.getVehiclesDestroyed());
                        wxcPlayer.setWalkDistance(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getWalkDistance() + wxcPlayer.getWalkDistance());
                        wxcPlayer.setWeaponsAcquired(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getWeaponsAcquired() + wxcPlayer.getWeaponsAcquired());
                        if (wxcPlayer.getKillStreak() <= manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getKillStreak()) {
                            wxcPlayer.setKillStreak(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getKillStreak());
                        }
                        if (wxcPlayer.getLongestKill() <= manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getLongestKill()) {
                            wxcPlayer.setLongestKill(manager.players.get(i).getMatches().get(j).getParticipantList().get(playerIndex).getLongestKill());
                        }
                }

                // Break if another day is discovered when wxc stats, stored for this player, are not null
                if (manager.players.get(i).getMatches().get(j).getDay() != 5 &&
                        wxcPlayerStats.get(i).getParticipantList().get(0).getKills() != -1) {
                    wxcPlayerStats.get(i).getParticipantList().get(0).setKills(wxcPlayerStats.get(i).getParticipantList().get(0).getKills() + 1);
                    break;
                }
            }
        }
    }

    private void SetMedals(View view) {
        // Gather the text views
        TextView assaultGamertagText = view.getRootView().findViewById(R.id.assaultNameValue);
        TextView tacticianGamertagText = view.getRootView().findViewById(R.id.tacticianNameValue);
        TextView supportGamertagText = view.getRootView().findViewById(R.id.supportNameValue);
        TextView sniperGamertagText = view.getRootView().findViewById(R.id.sniperNameValue);

        // Determine the assault medal and set the gamertag
        // Assault = Kills, DamageDealt, Killstreak
        ArrayList<Integer> killsPlayerStats = new ArrayList<>(Arrays.asList(wxcPlayerStats.get(0).getParticipantList().get(0).getKills(),
                wxcPlayerStats.get(1).getParticipantList().get(0).getKills(),
                wxcPlayerStats.get(2).getParticipantList().get(0).getKills(),
                wxcPlayerStats.get(3).getParticipantList().get(0).getKills()));
        ArrayList<Integer> damageDealtPlayerStats = new ArrayList<>(Arrays.asList((int)wxcPlayerStats.get(0).getParticipantList().get(0).getDamageDealt(),
                (int)wxcPlayerStats.get(1).getParticipantList().get(0).getDamageDealt(),
                (int)wxcPlayerStats.get(2).getParticipantList().get(0).getDamageDealt(),
                (int)wxcPlayerStats.get(3).getParticipantList().get(0).getDamageDealt()));
        ArrayList<Integer> killstreakPlayerStats = new ArrayList<>(Arrays.asList(wxcPlayerStats.get(0).getParticipantList().get(0).getKillStreak(),
                wxcPlayerStats.get(1).getParticipantList().get(0).getKillStreak(),
                wxcPlayerStats.get(2).getParticipantList().get(0).getKillStreak(),
                wxcPlayerStats.get(3).getParticipantList().get(0).getKillStreak()));

        ArrayList<Integer> killsPositions = SortPlayersByGreatest(killsPlayerStats);
        ArrayList<Integer> damageDealtPositions = SortPlayersByGreatest(damageDealtPlayerStats);
        ArrayList<Integer> killstreakPositions = SortPlayersByGreatest(killstreakPlayerStats);

        ArrayList<Integer> killsPoints = GetPointsFromPositions(killsPositions);
        ArrayList<Integer> damageDealtPoints = GetPointsFromPositions(damageDealtPositions);
        ArrayList<Integer> killstreakPoints = GetPointsFromPositions(killstreakPositions);

        Integer appleAssaultScore = killsPoints.get(0) + damageDealtPoints.get(0) + killstreakPoints.get(0);
        Integer chunkAssaultScore = killsPoints.get(1) + damageDealtPoints.get(1) + killstreakPoints.get(1);
        Integer jellyAssaultScore = killsPoints.get(2) + damageDealtPoints.get(2) + killstreakPoints.get(2);
        Integer jpAssaultScore = killsPoints.get(3) + damageDealtPoints.get(3) + killstreakPoints.get(3);

        ArrayList<Integer> assaultPositions = SortPlayersByGreatest(new ArrayList<>(Arrays.asList(appleAssaultScore, chunkAssaultScore, jellyAssaultScore, jpAssaultScore)));

        for (int i = 0; i < assaultPositions.size(); i++) {
            if (assaultPositions.get(i).equals(1)) {
                switch (i) {
                    case 0:
                        assaultGamertagText.setText("Applepie2");
                        break;
                    case 1:
                        assaultGamertagText.setText("HeyChunk");
                        break;
                    case 2:
                        assaultGamertagText.setText("JellyFilledFun");
                        break;
                    case 3:
                        assaultGamertagText.setText("JP Argyle2");
                        break;
                    default:
                        assaultGamertagText.setText("ERROR");
                        break;
                }
            }
        }

        // Determine the tactician medal and set the gamertag
        // Tactician = TimeSurvived, DamageDealt, Heals
        ArrayList<Integer> timeSurvivedPlayerStats = new ArrayList<>(Arrays.asList((int)wxcPlayerStats.get(0).getParticipantList().get(0).getTimeSurvived(),
                (int)wxcPlayerStats.get(1).getParticipantList().get(0).getTimeSurvived(),
                (int)wxcPlayerStats.get(2).getParticipantList().get(0).getTimeSurvived(),
                (int)wxcPlayerStats.get(3).getParticipantList().get(0).getTimeSurvived()));
        ArrayList<Integer> healsPlayerStats = new ArrayList<>(Arrays.asList(wxcPlayerStats.get(0).getParticipantList().get(0).getHeals(),
                wxcPlayerStats.get(1).getParticipantList().get(0).getHeals(),
                wxcPlayerStats.get(2).getParticipantList().get(0).getHeals(),
                wxcPlayerStats.get(3).getParticipantList().get(0).getHeals()));

        ArrayList<Integer> timeSurvivedPositions = SortPlayersByGreatest(timeSurvivedPlayerStats);
        ArrayList<Integer> healsPositions = SortPlayersByGreatest(healsPlayerStats);

        ArrayList<Integer> timeSurvivedPoints = GetPointsFromPositions(timeSurvivedPositions);
        ArrayList<Integer> healsPoints = GetPointsFromPositions(healsPositions);

        Integer appleTacticianScore = timeSurvivedPoints.get(0) + damageDealtPoints.get(0) + healsPoints.get(0);
        Integer chunkTacticianScore = timeSurvivedPoints.get(1) + damageDealtPoints.get(1) + healsPoints.get(1);
        Integer jellyTacticianScore = timeSurvivedPoints.get(2) + damageDealtPoints.get(2) + healsPoints.get(2);
        Integer jpTacticianScore = timeSurvivedPoints.get(3) + damageDealtPoints.get(3) + healsPoints.get(3);

        ArrayList<Integer> tacticianPositions = SortPlayersByGreatest(new ArrayList<>(Arrays.asList(appleTacticianScore, chunkTacticianScore, jellyTacticianScore, jpTacticianScore)));

        for (int i = 0; i < tacticianPositions.size(); i++) {
            if (tacticianPositions.get(i).equals(1)) {
                switch (i) {
                    case 0:
                        tacticianGamertagText.setText("Applepie2");
                        break;
                    case 1:
                        tacticianGamertagText.setText("HeyChunk");
                        break;
                    case 2:
                        tacticianGamertagText.setText("JellyFilledFun");
                        break;
                    case 3:
                        tacticianGamertagText.setText("JP Argyle2");
                        break;
                    default:
                        tacticianGamertagText.setText("ERROR");
                        break;
                }
            }
        }

        // Determine the support medal and set the gamertag
        // Support = Assists, Revives, Boosts
        ArrayList<Integer> assistsPlayerStats = new ArrayList<>(Arrays.asList(wxcPlayerStats.get(0).getParticipantList().get(0).getAssists(),
                wxcPlayerStats.get(1).getParticipantList().get(0).getAssists(),
                wxcPlayerStats.get(2).getParticipantList().get(0).getAssists(),
                wxcPlayerStats.get(3).getParticipantList().get(0).getAssists()));
        ArrayList<Integer> revivesPlayerStats = new ArrayList<>(Arrays.asList(wxcPlayerStats.get(0).getParticipantList().get(0).getRevives(),
                wxcPlayerStats.get(1).getParticipantList().get(0).getRevives(),
                wxcPlayerStats.get(2).getParticipantList().get(0).getRevives(),
                wxcPlayerStats.get(3).getParticipantList().get(0).getRevives()));
        ArrayList<Integer> boostsPlayerStats = new ArrayList<>(Arrays.asList(wxcPlayerStats.get(0).getParticipantList().get(0).getWeaponsAcquired(),
                wxcPlayerStats.get(1).getParticipantList().get(0).getWeaponsAcquired(),
                wxcPlayerStats.get(2).getParticipantList().get(0).getWeaponsAcquired(),
                wxcPlayerStats.get(3).getParticipantList().get(0).getWeaponsAcquired()));

        ArrayList<Integer> assistsPositions = SortPlayersByGreatest(assistsPlayerStats);
        ArrayList<Integer> revivesPositions = SortPlayersByGreatest(revivesPlayerStats);
        ArrayList<Integer> boostsPositions = SortPlayersByGreatest(boostsPlayerStats);

        ArrayList<Integer> assistsPoints = GetPointsFromPositions(assistsPositions);
        ArrayList<Integer> revivesPoints = GetPointsFromPositions(revivesPositions);
        ArrayList<Integer> boostsPoints = GetPointsFromPositions(boostsPositions);

        Integer appleSupportScore = assistsPoints.get(0) + revivesPoints.get(0) + boostsPoints.get(0);
        Integer chunkSupportScore = assistsPoints.get(1) + revivesPoints.get(1) + boostsPoints.get(1);
        Integer jellySupportScore = assistsPoints.get(2) + revivesPoints.get(2) + boostsPoints.get(2);
        Integer jpSupportScore = assistsPoints.get(3) + revivesPoints.get(3) + boostsPoints.get(3);

        ArrayList<Integer> supportPositions = SortPlayersByGreatest(new ArrayList<>(Arrays.asList(appleSupportScore, chunkSupportScore, jellySupportScore, jpSupportScore)));

        for (int i = 0; i < supportPositions.size(); i++) {
            if (supportPositions.get(i).equals(1)) {
                switch (i) {
                    case 0:
                        supportGamertagText.setText("Applepie2");
                        break;
                    case 1:
                        supportGamertagText.setText("HeyChunk");
                        break;
                    case 2:
                        supportGamertagText.setText("JellyFilledFun");
                        break;
                    case 3:
                        supportGamertagText.setText("JP Argyle2");
                        break;
                    default:
                        supportGamertagText.setText("ERROR");
                        break;
                }
            }
        }

        // Determine the sniper medal and set the gamertag
        // Sniper = HeadshotKills, LongestKill, DBNO's
        ArrayList<Integer> headshotsPlayerStats = new ArrayList<>(Arrays.asList(wxcPlayerStats.get(0).getParticipantList().get(0).getHeadshotKills(),
                wxcPlayerStats.get(1).getParticipantList().get(0).getHeadshotKills(),
                wxcPlayerStats.get(2).getParticipantList().get(0).getHeadshotKills(),
                wxcPlayerStats.get(3).getParticipantList().get(0).getHeadshotKills()));
        ArrayList<Integer> longestKillPlayerStats = new ArrayList<>(Arrays.asList((int)wxcPlayerStats.get(0).getParticipantList().get(0).getLongestKill(),
                (int)wxcPlayerStats.get(1).getParticipantList().get(0).getLongestKill(),
                (int)wxcPlayerStats.get(2).getParticipantList().get(0).getLongestKill(),
                (int)wxcPlayerStats.get(3).getParticipantList().get(0).getLongestKill()));
        ArrayList<Integer> dbnosPlayerStats = new ArrayList<>(Arrays.asList(wxcPlayerStats.get(0).getParticipantList().get(0).getDBNOs(),
                wxcPlayerStats.get(1).getParticipantList().get(0).getDBNOs(),
                wxcPlayerStats.get(2).getParticipantList().get(0).getDBNOs(),
                wxcPlayerStats.get(3).getParticipantList().get(0).getDBNOs()));

        ArrayList<Integer> headshotsPositions = SortPlayersByGreatest(headshotsPlayerStats);
        ArrayList<Integer> longestKillPositions = SortPlayersByGreatest(longestKillPlayerStats);
        ArrayList<Integer> dbnosPositions = SortPlayersByGreatest(dbnosPlayerStats);

        ArrayList<Integer> headshotsPoints = GetPointsFromPositions(headshotsPositions);
        ArrayList<Integer> longestKillPoints = GetPointsFromPositions(longestKillPositions);
        ArrayList<Integer> dbnosPoints = GetPointsFromPositions(dbnosPositions);

        Integer appleSniperScore = headshotsPoints.get(0) + longestKillPoints.get(0) + dbnosPoints.get(0);
        Integer chunkSniperScore = headshotsPoints.get(1) + longestKillPoints.get(1) + dbnosPoints.get(1);
        Integer jellySniperScore = headshotsPoints.get(2) + longestKillPoints.get(2) + dbnosPoints.get(2);
        Integer jpSniperScore = headshotsPoints.get(3) + longestKillPoints.get(3) + dbnosPoints.get(3);

        ArrayList<Integer> sniperPositions = SortPlayersByGreatest(new ArrayList<>(Arrays.asList(appleSniperScore, chunkSniperScore, jellySniperScore, jpSniperScore)));

        for (int i = 0; i < sniperPositions.size(); i++) {
            if (sniperPositions.get(i).equals(1)) {
                switch (i) {
                    case 0:
                        sniperGamertagText.setText("Applepie2");
                        break;
                    case 1:
                        sniperGamertagText.setText("HeyChunk");
                        break;
                    case 2:
                        sniperGamertagText.setText("JellyFilledFun");
                        break;
                    case 3:
                        sniperGamertagText.setText("JP Argyle2");
                        break;
                    default:
                        supportGamertagText.setText("ERROR");
                        break;
                }
            }
        }
    }

    private ArrayList<Integer> GetPointsFromPositions (ArrayList<Integer> playerPositions) {
        ArrayList<Integer> playerPoints = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        for (int i = 0; i < playerPositions.size(); i++) {
            playerPoints.set(i, 5 - playerPositions.get(i));
        }
        return playerPoints;
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
                    if (workingPlayerStats.get(i).equals(lastHighestValue)) {
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
