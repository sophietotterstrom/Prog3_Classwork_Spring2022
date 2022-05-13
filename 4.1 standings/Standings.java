/**
 * @author sophietotterstrom
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Standings {
    public Standings() {

    }

    public Standings(String filename) throws IOException {
        readMatchData(filename);
    }

    public static class Team {
        private String name;
        private int wins;
        private int ties;
        private int losses;
        private int scored;
        private int allowed;
        private int diff;
        private int points;

        Team(String name) {
            this.name = name;
            this.wins = 0;
            this.ties = 0;
            this.losses = 0;
            this.scored = 0;
            this.allowed = 0;
            this.points = 0;
        }

        public String getName() {
            return this.name;
        }

        public int getWins() {
            return this.wins;
        }

        private void incrementWins() {
            this.wins++;
        }

        public int getTies() {
            return this.ties;
        }

        private void incrementTies() {
            this.ties++;
        }

        public int getLosses() {
            return this.losses;
        }

        private void incrementLosses() {
            this.losses++;
        }

        public int getScored() {
            return this.scored;
        }

        private void setScored(int n) {
            this.scored = n;
        }

        public int getAllowed() {
            return this.allowed;
        }

        private void setAllowed(int n) {
            this.allowed = n;
        }

        private int getDiff() {
            return this.scored - this.allowed;
        }

        private int getMatches() {
            return this.wins + this.ties + this.losses;
        }

        public int getPoints() {
            this.points = 3 * this.wins + 1 * this.ties;
            return this.points;
        }
    }

    private Map<String, Team> teamsMap = new HashMap<String, Team>();


    public void readMatchData(String filename) throws IOException {

        try (var file = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = file.readLine()) != null) {
                String[] lineArray = line.split("\\t");
                String teamNameA = lineArray[0];
                String teamNameB = lineArray[2];
                String[] scoresArray = lineArray[1].split("-");
                int scoreA = Integer.valueOf(scoresArray[0]);
                int scoreB = Integer.valueOf(scoresArray[1]);

                addMatchResult(teamNameA, scoreA, scoreB, teamNameB);
            }
        }

    }

    public void addMatchResult(String teamNameA, int goalsA,
                               int goalsB, String teamNameB) {
        Team teamA;
        Team teamB;

        if (teamsMap.containsKey(teamNameA)) {
            // is already a team object
            teamA = teamsMap.get(teamNameA);
        } else {
            // not already in the map
            teamA = new Team(teamNameA);
            teamsMap.put(teamNameA, teamA);
        }
        if (teamsMap.containsKey(teamNameB)) {
            // is already a team object
            teamB = teamsMap.get(teamNameB);
        } else {
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

    private int checkPoints(Team teamA, Team teamB) {
        if (teamA.getPoints() > teamB.getPoints()) {
            return 1;
        } else if (teamA.getPoints() < teamB.getPoints()) {
            return 0;
        }
        // Tie
         return -1;
    }

    private int checkDiff(Team teamA, Team teamB) {
        if (teamA.getDiff() > teamB.getDiff()) {
            return 1;
        } else if (teamA.getDiff() < teamB.getDiff()) {
            return 0;
        }
        return -1;
    }

    private int checkScored(Team teamA, Team teamB) {
        if (teamA.getScored() > teamB.getScored()) {
            return 1;
        } else if (teamA.getScored() < teamB.getScored()) {
            return 0;
        }
        return -1;
    }

    private int checkName(Team teamA, Team teamB) {
        if (teamA.name.compareTo(teamB.name) == -1) {
            return 1;
        }
        return 0;
    }

    private int checkTeam(Team teamA, Team teamB) {
        int check;
        // Compare points
        check = checkPoints(teamA, teamB);
        // Return 1 or 0. -1 is a tie.
        if (check != -1) {
            return check;
        } else {
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

    private ArrayList<Team> sortTeams() {
        // List to store teams sorted by ranking criteria
        ArrayList<Team> sorted = new ArrayList<Team>();

        for (Map.Entry<String, Team> pair : this.teamsMap.entrySet()) {
            // Get a new team from the map to place in the sorted list
            Team newTeam = pair.getValue();

            // Add first team to array
            if (sorted.size() == 0) {
                sorted.add(newTeam);
            } else {
                boolean wasInserted = false;
                for (int i = 0; i < sorted.size(); i++) {
                    // Get the team object from the sorted list at index i
                    Team teamAtIndex = sorted.get(i);
                    // Check if the new team is better than the team in the sorted list at index i
                    if (checkTeam(newTeam, teamAtIndex) == 1) {
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

    public ArrayList<Team> getTeams() {
        // Returns a list of teams sorted by sorting criteria
        ArrayList<Team> teams = sortTeams();
        return teams;
    }

    public void printStandings() {
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
            System.out.print(String.format("%-" + max_length + "s", team.getName()));
            System.out.print(String.format("%4s", team.getMatches()));
            System.out.print(String.format("%4s", team.getWins()));
            System.out.print(String.format("%4s", team.getTies()));
            System.out.print(String.format("%4s", team.getLosses()));
            System.out.print(String.format("%7s", team.getScored() + "-" + team.getAllowed()));
            System.out.println(String.format("%4s", team.getPoints()));
        }
    }
}

