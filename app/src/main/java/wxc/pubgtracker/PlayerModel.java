package wxc.pubgtracker;

import java.util.ArrayList;

public class PlayerModel {

    private String gamertag;
    private String playerID;
    private ArrayList<MatchModel> matches;
    private ArrayList<SeasonModel> stats;
    private Boolean statsLoaded;

    public PlayerModel() {
        this.gamertag = null;
        this.playerID = null;
        this.matches = new ArrayList<>();
        this.stats = new ArrayList<>();
        this.statsLoaded = false;
    }

    public PlayerModel(String gamertag, String playerID) {
        this.gamertag = gamertag;
        this.playerID = playerID;
        this.matches = new ArrayList<>();
        this.stats = new ArrayList<>();
        this.statsLoaded = false;
    }

    public String getGamertag() {
        return gamertag;
    }

    public void setGamertag(String gamertag) {
        this.gamertag = gamertag;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public ArrayList<MatchModel> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<MatchModel> matches) {
        this.matches = matches;
    }

    public void addMatches(String matchID) {
        this.matches.add(new MatchModel(matchID));
    }

    public ArrayList<SeasonModel> getStats() {
        return stats;
    }

    public void setStats(ArrayList<SeasonModel> stats) {
        this.stats = stats;
    }

    public Boolean getStatsLoaded() { return statsLoaded; }

    public void setStatsLoaded(Boolean statsLoaded) { this.statsLoaded = statsLoaded; }
}
