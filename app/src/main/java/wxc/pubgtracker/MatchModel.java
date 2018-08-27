package wxc.pubgtracker;

import android.provider.Telephony;

import java.util.ArrayList;
import java.util.Date;

public class MatchModel {

    private String matchID;
    private String gameMode;        // Solo, Duo, Squad
    private String matchDateTime;   // Date and time that the match started
    private Date date;
    private Integer day;            // 1-Sun, 2-Mon, 3-Tue, 4-Wed, 5-Thur, 6-Fri, 7-Sat
    private String mapName;

    private ArrayList<Participant> participantList;  // Holds the participant data for all WXC members

    public MatchModel(){
        participantList = new ArrayList<>();
    }

    public MatchModel(String matchID) {
        this.matchID = matchID;
        participantList = new ArrayList<>();
    }

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getMatchDateTime() {
        return matchDateTime;
    }

    public void setMatchDateTime(String matchDateTime) {
        this.matchDateTime = matchDateTime;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public Integer getDay() { return day; }

    public void setDay(Integer day) { this.day = day; }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public ArrayList<Participant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(ArrayList<Participant> participantList) {
        this.participantList = participantList;
    }

    // Adds an empty participant to the match list
    public void addParticipant() {
        Participant participant = new Participant();
        participant.setKills(-1);
        participantList.add(participant);
    }

    // Adds a new fully populated participant to the match list
    public void addParticipant(String gamertag,
                               String playerId,
                               int DBNOs,
                               int assists,
                               int boosts,
                               double damageDealt,
                               String deathType,
                               int headshotKills,
                               int heals,
                               int killPlace,
                               int killPoints,
                               double killPointsDelta,
                               int killStreak,
                               int kills,
                               double longestKill,
                               int revives,
                               double rideDistance,
                               int roadKills,
                               double swimDistance,
                               int teamKills,
                               double timeSurvived,
                               int vehiclesDestroyed,
                               double walkDistance,
                               int weaponsAcquired,
                               int winPlace,
                               int winPoints,
                               double winPointsDelta) {

        Participant newParticipant = new Participant();
        newParticipant.setGamertag(gamertag);
        newParticipant.setPlayerID(playerId);
        newParticipant.setDBNOs(DBNOs);
        newParticipant.setAssists(assists);
        newParticipant.setBoosts(boosts);
        newParticipant.setDamageDealt(damageDealt);
        newParticipant.setDeathType(deathType);
        newParticipant.setHeadshotKills(headshotKills);
        newParticipant.setHeals(heals);
        newParticipant.setKillPlace(killPlace);
        newParticipant.setKillPoints(killPoints);
        newParticipant.setKillPointsDelta(killPointsDelta);
        newParticipant.setKillStreak(killStreak);
        newParticipant.setKills(kills);
        newParticipant.setLongestKill(longestKill);
        newParticipant.setRevives(revives);
        newParticipant.setRideDistance(rideDistance);
        newParticipant.setRoadKills(roadKills);
        newParticipant.setSwimDistance(swimDistance);
        newParticipant.setTeamKills(teamKills);
        newParticipant.setTimeSurvived(timeSurvived);
        newParticipant.setVehiclesDestroyed(vehiclesDestroyed);
        newParticipant.setWalkDistance(walkDistance);
        newParticipant.setWeaponsAcquired(weaponsAcquired);
        newParticipant.setWinPlace(winPlace);
        newParticipant.setWinPoints(winPoints);
        newParticipant.setWinPointsDelta(winPointsDelta);
        
        participantList.add(newParticipant);
    }

    public class Participant {
        private String gamertag;
        private String playerID;
        private int DBNOs;
        private int assists;
        private int boosts;
        private double damageDealt;
        private String deathType;
        private int headshotKills;
        private int heals;
        private int killPlace;
        private int killPoints;
        private double killPointsDelta;
        private int killStreak;
        private int kills;
        private double longestKill;
        private int revives;
        private double rideDistance;
        private int roadKills;
        private double swimDistance;
        private int teamKills;
        private double timeSurvived;
        private int vehiclesDestroyed;
        private double walkDistance;
        private int weaponsAcquired;
        private int winPlace;
        private int winPoints;
        private double winPointsDelta;

        public Participant() {

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

        public int getDBNOs() {
            return DBNOs;
        }

        public void setDBNOs(int DBNOs) {
            this.DBNOs = DBNOs;
        }

        public int getAssists() {
            return assists;
        }

        public void setAssists(int assists) {
            this.assists = assists;
        }

        public int getBoosts() {
            return boosts;
        }

        public void setBoosts(int boosts) {
            this.boosts = boosts;
        }

        public double getDamageDealt() {
            return damageDealt;
        }

        public void setDamageDealt(double damageDealt) {
            this.damageDealt = damageDealt;
        }

        public String getDeathType() {
            return deathType;
        }

        public void setDeathType(String deathType) {
            this.deathType = deathType;
        }

        public int getHeadshotKills() {
            return headshotKills;
        }

        public void setHeadshotKills(int headshotKills) {
            this.headshotKills = headshotKills;
        }

        public int getHeals() {
            return heals;
        }

        public void setHeals(int heals) {
            this.heals = heals;
        }

        public int getKillPlace() {
            return killPlace;
        }

        public void setKillPlace(int killPlace) {
            this.killPlace = killPlace;
        }

        public int getKillPoints() {
            return killPoints;
        }

        public void setKillPoints(int killPoints) {
            this.killPoints = killPoints;
        }

        public double getKillPointsDelta() {
            return killPointsDelta;
        }

        public void setKillPointsDelta(double killPointsDelta) {
            this.killPointsDelta = killPointsDelta;
        }

        public int getKillStreak() {
            return killStreak;
        }

        public void setKillStreak(int killStreak) {
            this.killStreak = killStreak;
        }

        public int getKills() {
            return kills;
        }

        public void setKills(int kills) {
            this.kills = kills;
        }

        public double getLongestKill() {
            return longestKill;
        }

        public void setLongestKill(double longestKill) {
            this.longestKill = longestKill;
        }

        public int getRevives() {
            return revives;
        }

        public void setRevives(int revives) {
            this.revives = revives;
        }

        public double getRideDistance() {
            return rideDistance;
        }

        public void setRideDistance(double rideDistance) {
            this.rideDistance = rideDistance;
        }

        public int getRoadKills() {
            return roadKills;
        }

        public void setRoadKills(int roadKills) {
            this.roadKills = roadKills;
        }

        public double getSwimDistance() {
            return swimDistance;
        }

        public void setSwimDistance(double swimDistance) {
            this.swimDistance = swimDistance;
        }

        public int getTeamKills() {
            return teamKills;
        }

        public void setTeamKills(int teamKills) {
            this.teamKills = teamKills;
        }

        public double getTimeSurvived() {
            return timeSurvived;
        }

        public void setTimeSurvived(double timeSurvived) {
            this.timeSurvived = timeSurvived;
        }

        public int getVehiclesDestroyed() {
            return vehiclesDestroyed;
        }

        public void setVehiclesDestroyed(int vehiclesDestroyed) {
            this.vehiclesDestroyed = vehiclesDestroyed;
        }

        public double getWalkDistance() {
            return walkDistance;
        }

        public void setWalkDistance(double walkDistance) {
            this.walkDistance = walkDistance;
        }

        public int getWeaponsAcquired() {
            return weaponsAcquired;
        }

        public void setWeaponsAcquired(int weaponsAcquired) {
            this.weaponsAcquired = weaponsAcquired;
        }

        public int getWinPlace() {
            return winPlace;
        }

        public void setWinPlace(int winPlace) {
            this.winPlace = winPlace;
        }

        public int getWinPoints() {
            return winPoints;
        }

        public void setWinPoints(int winPoints) {
            this.winPoints = winPoints;
        }

        public double getWinPointsDelta() {
            return winPointsDelta;
        }

        public void setWinPointsDelta(double winPointsDelta) {
            this.winPointsDelta = winPointsDelta;
        }
    }
}

