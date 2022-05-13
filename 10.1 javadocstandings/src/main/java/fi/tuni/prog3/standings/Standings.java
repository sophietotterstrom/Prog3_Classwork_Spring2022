package fi.tuni.prog3.standings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 *  A class for maintaining team statistics and standings. Team standings are determined by the following rules:
 * <ul>
 *     <li>Primary rule: points total. Higher points come first.</li>
 *     <li>Secondary rule: goal difference (scored minus allowed). Higher difference comes first.</li>
 *     <li>Tertiary rule: number of goals scored. Higher number comes first.</li>
 *     <li>Last rule: natural String order of team names.</li>
 * </ul>
 */
public class Standings
{
    /**
     * Constructs an empty Standings object.
     */
    public Standings()
    { }

    /**
     * Constructs a Standings object that is initialized with the game data
     * read from the specified file. The result is identical to first constructing
     * an empty Standing object and then calling
     * {@link  #readMatchData(String) readMatchData(filename)}.
     * @param filename      the name of the game data file to read.
     * @throws IOException  if there is some kind of an IO error (e.g. if the specified file does not exist).
     */
    public Standings(String filename) throws IOException
    {
        readMatchData(filename);
    }

    /**
     * A class for storing statistics of a single team. The class offers only public getter functions. The enclosing class Standings is responsible for setting and updating team statistics.
     */
    public static class Team
    {

        /**
         * Constructs a Team object for storing statistics of the named team.
         * @param name the name of the team whose statistics the new team object stores.
         */
        public Team(String name) {
            this.name = name;
            this.wins = 0;
            this.ties = 0;
            this.losses = 0;
            this.scored = 0;
            this.allowed = 0;
            this.points = 0;
        }

        private String name;
        private int wins;
        private int ties;
        private int losses;
        private int scored;
        private int allowed;
        private int diff;
        private int points;

        /**
         * Returns the name of the team.
         * @return the name of the team.
         */
        public String getName() {
            return this.name;
        }

        /**
         * Returns the number of wins of the team.
         * @return the number of wins of the team.
         */
        public int getWins() {
            return this.wins;
        }

        /**
         * Returns the number of ties of the team.
         * @return the number of ties of the team.
         */
        public int getTies() {
            return this.ties;
        }

        /**
         * Returns the number of losses of the team.
         * @return the number of losses of the team.
         */
        public int getLosses() {
            return this.losses;
        }

        /**
         * Returns the number of goals scored by the team.
         * @return the number of goals scored by the team.
         */
        public int getScored() {
            return this.scored;
        }

        /**
         * Returns the number of goals allowed (conceded) by the team.
         * @return the number of goals allowed (conceded) by the team.
         */
        public int getAllowed() {
            return this.allowed;
        }

        /**
         * Returns the overall number of points of the team.
         * @return the overall number of points of the team.
         */
        public int getPoints() {
            this.points = 3 * this.wins + 1 * this.ties;
            return this.points;
        }

        /**
         * @hidden
         */
        private void incrementWins() {
            this.wins++;
        }
        /**
         * @hidden
         */
        private void incrementTies() {
            this.ties++;
        }

        /**
         * @hidden
         */
        private void incrementLosses() {
            this.losses++;
        }

        /**
         * @hidden
         */
        private void setScored(int n) {
            this.scored = n;
        }

        /**
         * @hidden
         */
        private void setAllowed(int n) {
            this.allowed = n;
        }

        /**
         * @hidden
         */
        private int getDiff() {
            return this.scored - this.allowed;
        }

        /**
         * @hidden
         */
        private int getMatches() {
            return this.wins + this.ties + this.losses;
        }
    }


    private Map<String, Team> teamsMap = new HashMap<String, Team>();

    /**
     * Reads game data from the specified file and updates the team
     * statistics and standings accordingly.
     *
     * <p>The match data file is expected to contain lines of form
     * "teamNameA\tgoalsA-goalsB\tteamNameB". Note that the '\t' are tabulator characters.</p>
     *
     * <p>E.g. the line "Iceland\t3-2\tFinland" would describe a match between
     * Iceland and Finland where Iceland scored 3 and Finland 2 goals.</p>
     *
     * @param filename      the name of the game data file to read.
     * @throws IOException  if there is some kind of an IO error (e.g. if the specified file does not exist).
     */
    public final void readMatchData(String filename) throws IOException
    {

        try (var file = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = file.readLine()) != null)
            {
                String[] lineArray = line.split("\\t");
                String teamNameA = lineArray[0];
                String teamNameB = lineArray[2];
                String[] scoresArray = lineArray[1].split("-");
                int scoreA = Integer.parseInt(scoresArray[0]);
                int scoreB = Integer.parseInt(scoresArray[1]);

                addMatchResult(teamNameA, scoreA, scoreB, teamNameB);
            }
        }

    }

    /**
     * Updates the team statistics and standings according to the match
     * result described by the parameters.
     * @param teamNameA     the name of the first ("home") team.
     * @param goalsA        the number of goals scored by the first team.
     * @param goalsB        the number of goals scored by the second team.
     * @param teamNameB     the name of the second ("away") team.
     */
    public void addMatchResult(String teamNameA, int goalsA,
                               int goalsB, String teamNameB)
    {
        Team teamA;
        Team teamB;

        if (teamsMap.containsKey(teamNameA))
        {
            // is already a team object
            teamA = teamsMap.get(teamNameA);
        } else
        {
            // not already in the map
            teamA = new Team(teamNameA);
            teamsMap.put(teamNameA, teamA);
        }
        if (teamsMap.containsKey(teamNameB))
        {
            // is already a team object
            teamB = teamsMap.get(teamNameB);
        } else
        {
            // not already in the map
            teamB = new Team(teamNameB);
            teamsMap.put(teamNameB, teamB);
        }

        teamA.setScored(teamA.getScored() + goalsA);
        teamB.setScored(teamB.getScored() + goalsB);

        teamA.setAllowed(teamA.getAllowed() + goalsB);
        teamB.setAllowed(teamB.getAllowed() + goalsA);

        if (goalsA > goalsB) {
            teamA.incrementWins();
            teamB.incrementLosses();
        } else if (goalsB > goalsA) {
            teamB.incrementWins();
            teamA.incrementLosses();
        } else {
            teamA.incrementTies();
            teamB.incrementTies();
        }

        teamsMap.replace(teamNameA, teamA);
        teamsMap.replace(teamNameB, teamB);

    }

    /**
     * @hidden
     */
    private int checkPoints(Team teamA, Team teamB)
    {
        if (teamA.getPoints() > teamB.getPoints())
        {
            return 1;
        } else if (teamA.getPoints() < teamB.getPoints())
        {
            return 0;
        }
        // Tie
        return -1;
    }

    /**
     * @hidden
     */
    private int checkDiff(Team teamA, Team teamB)
    {
        if (teamA.getDiff() > teamB.getDiff())
        {
            return 1;
        } else if (teamA.getDiff() < teamB.getDiff())
        {
            return 0;
        }
        return -1;
    }

    /**
     * @hidden
     */
    private int checkScored(Team teamA, Team teamB) {
        if (teamA.getScored() > teamB.getScored()) {
            return 1;
        } else if (teamA.getScored() < teamB.getScored()) {
            return 0;
        }
        return -1;
    }

    /**
     * @hidden
     */
    private int checkName(Team teamA, Team teamB) {
        if (teamA.name.compareTo(teamB.name) == -1) {
            return 1;
        }
        return 0;
    }

    /**
     * @hidden
     */
    private int checkTeam(Team teamA, Team teamB) {
        int check;
        // Compare points
        check = checkPoints(teamA, teamB);
        // Return 1 or 0. -1 is a tie.
        if (check != -1)
        {
            return check;
        }
        else
        {
            check = checkDiff(teamA, teamB);
            if (check != -1) {
                return check;
            } else {
                check = checkScored(teamA, teamB);
            } if (check != -1) {
                return check;
            } else {
                // checkName always returns 1 or 0
                return checkName(teamA, teamB);
            }
        }
    }

    /**
     * @hidden
     */
    private ArrayList<Team> sortTeams() {
        // List to store teams sorted by ranking criteria
        ArrayList<Team> sorted = new ArrayList<Team>();

        for (Map.Entry<String, Team> pair : this.teamsMap.entrySet())
        {
            // Get a new team from the map to place in the sorted list
            Team newTeam = pair.getValue();

            // Add first team to array
            if (sorted.size() == 0)
            {
                sorted.add(newTeam);
            } else
            {
                boolean wasInserted = false;
                for (int i = 0; i < sorted.size(); i++)
                {
                    // Get the team object from the sorted list at index i
                    Team teamAtIndex = sorted.get(i);
                    // Check if the new team is better than the team in the sorted list at index i
                    if (checkTeam(newTeam, teamAtIndex) == 1)
                    {
                        // If new team is better, insert it at index i
                        sorted.add(i, newTeam);
                        wasInserted = true;
                        break;
                    }
                }
                // If the team wasn't inserted into the list, add it to the end.
                if (!wasInserted) {
                    sorted.add(newTeam);
                }
            }
        }
        return sorted;
    }

    /**
     * Returns a list of the teams in the same order as they would appear in a standings table.
     * @return  a list of the teams in the same order as they would appear in a standings table.
     */
    public List<Team> getTeams() {
        // Returns a list of teams sorted by sorting criteria
        ArrayList<Team> teams = sortTeams();
        return (List<Team>) teams;
    }

    /**
     * Prints a formatted standings table to the provided output stream.
     * @param out the output stream to use when printing the standings table.
     */
    public void printStandings(PrintStream out) {
        // Get a sorted list of teams
        ArrayList<Team> teams = sortTeams();

        // Find the length of the longest team name for string padding
        int max_length = 0;
        for (Team t : teams) {
            if (t.getName().length() > max_length) {
                max_length = t.getName().length();
            }
        }

        // Print with specified formatting
        for (Team team : teams) {
            out.printf("%-" + max_length + "s", team.getName());
            out.printf("%4s", team.getMatches());
            out.printf("%4s", team.getWins());
            out.printf("%4s", team.getTies());
            out.printf("%4s", team.getLosses());
            out.printf("%7s", team.getScored() + "-" + team.getAllowed());
            out.printf("%4s%n", team.getPoints());
        }
    }
}
