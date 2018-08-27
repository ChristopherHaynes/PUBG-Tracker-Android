package wxc.pubgtracker;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class PUBGManager {

    private static final String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwZTNmMDdjMC0xYjBlLTAxMzYtNjRjMi00ZmM4YmMzYjI1ZjkiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTIyOTM5NTkyLCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImxvY2F0aW9udHJhY2tlciJ9.b6zFXe2lCx4X1cC_k4E69bSvcZYVOq7I39IrqjcWStw";
    private static final String seasonName = "division.bro.official.2018-07";
    public  ArrayList<String> wxcGamertags = new ArrayList<>(Arrays.asList("Applepie2", "HeyChunk", "JellyFilledFun", "JP Argyle2"));
    public  ArrayList<String> wxcHTMLSafeGamertags = new ArrayList<>(Arrays.asList("Applepie2", "HeyChunk", "JellyFilledFun", "JP%20Argyle2"));
    public  ArrayList<String> gameModes = new ArrayList<>(Arrays.asList("Solo", "Duo", "Squad"));

    // Private JSON object for working
    private JSONObject workingObject;

    // Holder for lists of players
    public ArrayList<PlayerModel> players;

    public PUBGManager() {
        // Init vars
        players = new ArrayList<>();
        workingObject = new JSONObject();

        // Get initial data from the API for all players
        refreshPlayerData();
    }

    public void refreshPlayerData() {

        // Call the API to retrieve all player data and corresponding match data
        for (int i = 0; i < wxcHTMLSafeGamertags.size(); i++)
        {
            new getPlayerByName().execute(wxcHTMLSafeGamertags.get(i));
        }
    }

    public class getPlayerByName extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {

            String gamertag = params[0];

            URL url;
            HttpURLConnection conn = null;
            try {
                url = new URL("https://api.pubg.com/shards/xbox-eu/players?filter[playerNames]=" + gamertag);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.setRequestProperty("Authorization","Bearer " + apiKey);
            conn.setRequestProperty("Accept", "application/vnd.api+json");

            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                workingObject = new JSONObject(buffer.toString());
                return workingObject;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return workingObject;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            // Create a new player object and store the ID and gamertag
            PlayerModel newPlayer = new PlayerModel();
            try {
                newPlayer = new PlayerModel( result.getJSONArray("data").getJSONObject(0).getJSONObject("attributes").getString("name"),
                                             result.getJSONArray("data").getJSONObject(0).getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Gather all the matchIDs and set them to the player object
            JSONArray testArray;
            try {
                testArray = result.getJSONArray("data").getJSONObject(0).getJSONObject("relationships").getJSONObject("matches").getJSONArray("data");
                for (int i = 0; i < testArray.length(); i++)
                {
                    newPlayer.addMatches(testArray.getJSONObject(i).getString("id"));
                    new getMatchByID().execute(testArray.getJSONObject(i).getString("id"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Get the player stats now the player has finished being stored
            new getPlayerStatsBySeason().execute(newPlayer.getPlayerID(), seasonName);

            // Mark the player as having successfully loaded stats
            newPlayer.setStatsLoaded(true);

            // Add the new player to the PUBG manager players list
            players.add(newPlayer);
        }
    }

    public class getPlayerStatsBySeason extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {

            String playerID = params[0];
            String seasonID = params[1];

            URL url;
            HttpURLConnection conn = null;
            try {
                url = new URL("https://api.pubg.com/shards/xbox-eu/players/" + playerID + "/seasons/" + seasonID);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.setRequestProperty("Authorization","Bearer " + apiKey);
            conn.setRequestProperty("Accept", "application/vnd.api+json");

            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                workingObject = new JSONObject(buffer.toString());
                return workingObject;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return workingObject;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            // Create an array list of season objects for each game mode (solo, duo, squad)
            ArrayList<SeasonModel> newStats = new ArrayList<>();
            String gameMode;

            // Loop for each game mode
            for (int i = 0; i < 3; i++)
            {
                // Set the string for the name of the game mode being examined
                switch (i) {
                    case 0: gameMode = "solo";
                            break;
                    case 1: gameMode = "duo";
                            break;
                    case 2: gameMode = "squad";
                            break;
                    default: gameMode = "squad";
                            break;
                }

                // Get the JSON Object of the stats for the game mode being examined
                JSONObject testObject = new JSONObject();
                try {
                    testObject = result.getJSONObject("data").getJSONObject("attributes").getJSONObject("gameModeStats").getJSONObject(gameMode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Populate a season model with the stats from the JSON Object
                SeasonModel gameModeStats = new SeasonModel();
                try {
                    gameModeStats.setGameMode(gameMode);
                    gameModeStats.setAssists(testObject.getInt("assists"));
                    gameModeStats.setBoosts(testObject.getInt("boosts"));
                    gameModeStats.setDBNOs(testObject.getInt("dBNOs"));
                    gameModeStats.setDailyKills(testObject.getInt("dailyKills"));
                    gameModeStats.setDamageDealt(testObject.getDouble("damageDealt"));
                    gameModeStats.setHeadshotKills(testObject.getInt("headshotKills"));
                    gameModeStats.setHeals(testObject.getInt("heals"));
                    gameModeStats.setKillPoints(testObject.getDouble("killPoints"));
                    gameModeStats.setKills(testObject.getInt("kills"));
                    gameModeStats.setLongestKill(testObject.getDouble("longestKill"));
                    gameModeStats.setLongestTimeSurvived(testObject.getDouble("longestTimeSurvived"));
                    gameModeStats.setLosses(testObject.getInt("losses"));
                    gameModeStats.setMaxKillStreak(testObject.getInt("maxKillStreaks"));
                    gameModeStats.setRevives(testObject.getInt("revives"));
                    gameModeStats.setRideDistance(testObject.getDouble("rideDistance"));
                    gameModeStats.setRoadKills(testObject.getInt("roadKills"));
                    gameModeStats.setRoundMostKills(testObject.getInt("roundMostKills"));
                    gameModeStats.setRoundsPlayed(testObject.getInt("roundsPlayed"));
                    gameModeStats.setSuicides(testObject.getInt("suicides"));
                    gameModeStats.setTeamKills(testObject.getInt("teamKills"));
                    gameModeStats.setTotalTimeSurvived(testObject.getDouble("timeSurvived"));
                    gameModeStats.setTop10s(testObject.getInt("top10s"));
                    gameModeStats.setVehiclesDestroyed(testObject.getInt("vehicleDestroys"));
                    gameModeStats.setWalkDistance(testObject.getDouble("walkDistance"));
                    gameModeStats.setWeaponsAcquired(testObject.getInt("weaponsAcquired"));
                    gameModeStats.setWeeklyKills(testObject.getInt("weeklyKills"));
                    gameModeStats.setWinPoints(testObject.getDouble("winPoints"));
                    gameModeStats.setWins(testObject.getInt("wins"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Add the current game mode stats to the list of all stats
                newStats.add(gameModeStats);
            }

            // Get the player ID connected to the recorded stats
            String statsPlayerID = null;
            try {
                statsPlayerID = result.getJSONObject("data").getJSONObject("relationships").getJSONObject("player").getJSONObject("data").getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Find the matching player and add the stats to their player object in the PUBG Manager
            for(int i = 0; i < players.size(); i++)
            {
                if (players.get(i).getPlayerID().equals(statsPlayerID)) {
                    players.get(i).setStats(newStats);
                }
            }
        }
    }

    public class getMatchByID extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {

            String matchID = params[0];

            URL url;
            HttpURLConnection conn = null;
            try {
                url = new URL("https://api.pubg.com/shards/xbox-eu/matches/" + matchID);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            conn.setRequestProperty("Accept", "application/vnd.api+json");

            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                workingObject = new JSONObject(buffer.toString());
                return workingObject;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return workingObject;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            // Create a new match object and populate it with the match ID from the returned object
            MatchModel newMatch = new MatchModel();
            try {
                newMatch = new MatchModel(result.getJSONObject("data").getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Store the general match data
            try {
                newMatch.setGameMode(result.getJSONObject("data").getJSONObject("attributes").getString("gameMode"));
                newMatch.setMapName(result.getJSONObject("data").getJSONObject("attributes").getString("mapName"));
                newMatch.setMatchDateTime(result.getJSONObject("data").getJSONObject("attributes").getString("createdAt"));
                newMatch.setDate(ConvertStringToDate(newMatch.getMatchDateTime()));
                newMatch.setDay(GetDayFromDate(newMatch.getDate()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Check the participants in the match and see if they are WXC members
            JSONArray participantArray;
            try {
                participantArray = result.getJSONArray("included");
                for (int i = 0; i < participantArray.length(); i++)
                {
                    if (participantArray.getJSONObject(i).getString("type").equals("participant")) {
                        for (int j = 0; j < wxcGamertags.size(); j++)
                        {
                            if(participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("name").equals(wxcGamertags.get(j)))
                            {
                                // Any valid participants should have objects created and populated
                                newMatch.addParticipant(
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("name"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("playerId"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("DBNOs"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("assists"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("boosts"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getDouble("damageDealt"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("deathType"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("headshotKills"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("heals"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("killPlace"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("killPoints"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getDouble("killPointsDelta"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("killStreaks"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("kills"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getDouble("longestKill"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("revives"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getDouble("rideDistance"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("roadKills"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getDouble("swimDistance"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("teamKills"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getDouble("timeSurvived"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("vehicleDestroys"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getDouble("walkDistance"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("weaponsAcquired"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("winPlace"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getInt("winPoints"),
                                        participantArray.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getDouble("winPointsDelta")
                                        );
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            // Search through the player list and replace any matching objects with the populated match object
            for (int i = 0; i < players.size(); i++)
            {
                for (int j = 0; j < players.get(i).getMatches().size(); j++)
                {
                    if (players.get(i).getMatches().get(j).getMatchID().equals(newMatch.getMatchID()))
                    {
                        players.get(i).getMatches().set(j, newMatch);
                        break;
                    }
                }
            }
        }
    }

    public Date ConvertStringToDate(String dateTime) {

        Date matchDate = null;
        String date = "DATE";

        if (dateTime.length() > 1) {
            date = dateTime.split("T")[0];
            Integer year = Integer.valueOf(date.split("-")[0]) - 1;
            Integer month = Integer.valueOf(date.split("-")[1]) - 1;
            Integer day = Integer.valueOf(date.split("-")[2]);
            matchDate = new Date(year, month, day);
        }

        return matchDate;
    }

    public Integer GetDayFromDate(Date date) {

        Integer day = -1;

        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            day = c.get(Calendar.DAY_OF_WEEK);
        }

        return day;
    }
}

