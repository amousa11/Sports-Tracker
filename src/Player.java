import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alimousa on 5/4/16.
 */
public class Player implements Comparable<Player> {
    private String link;
    private String[] allStats;
    private String name;
    private String age;
    private String team;
    private String league;
    private String position;
    private String games;
    private String gamesStarted;
    private String minutesPlayed;
    private String fieldGoals;
    private String fieldGoalAttempts;
    private String fieldGoalPercentage;
    private String threePointers;
    private String threePointersAttempted;
    private String threePointPercentage;
    private String twoPointers;
    private String twoPointersAttempted;
    private String twoPointPercentage;
    private String effectiveFieldGoalPercentage;
    private String freeThrows;
    private String freeThrowsAttempted;
    private String freeThrowPercentage;
    private String offensiveRebounds;
    private String defensiveRebounds;
    private String totalRebounds;
    private String assists;
    private String steals;
    private String blocks;
    private String turnovers;
    private String personalFouls;
    private String points;

    public Player(String link) {
        this.link = link;
        ArrayList<String> stats = getStats();
        try {
            name = stats.get(0);
            System.out.println("Getting Stats for Player " + name);
            position = stats.get(1);
            games = stats.get(2);
            gamesStarted = stats.get(3);
            minutesPlayed = stats.get(4);
            fieldGoals = stats.get(5);
            fieldGoalAttempts = stats.get(6);
            fieldGoalPercentage = stats.get(7);
            threePointers = stats.get(8);
            threePointersAttempted = stats.get(9);
            threePointPercentage = stats.get(10);
            twoPointers = stats.get(11);
            twoPointersAttempted = stats.get(12);
            twoPointPercentage = stats.get(13);
            effectiveFieldGoalPercentage = stats.get(14);
            freeThrows = stats.get(15);
            freeThrowsAttempted = stats.get(16);
            freeThrowPercentage = stats.get(17);
            offensiveRebounds = stats.get(18);
            defensiveRebounds = stats.get(19);
            totalRebounds = stats.get(20);
            assists = stats.get(21);
            steals = stats.get(22);
            blocks = stats.get(23);
            turnovers = stats.get(24);
            personalFouls = stats.get(25);
            points = stats.get(26);
        } catch (IndexOutOfBoundsException i) {
            i.printStackTrace();
        }
    }

    /**
     * Loads statistics using stats array which is loaded through the CSV in the Team object construction
     *
     * @param stats Array of player statistics
     */
    public Player(String[] stats) {
        try {
            allStats = stats;
            name = stats[0];
            age = stats[1];
            team = stats[2];
            league = stats[3];
            position = stats[4];
            games = stats[5];
            gamesStarted = stats[6];
            minutesPlayed = stats[7];
            fieldGoals = stats[8];
            fieldGoalAttempts = stats[9];
            fieldGoalPercentage = stats[10];
            threePointers = stats[11];
            threePointersAttempted = stats[12];
            threePointPercentage = stats[13];
            twoPointers = stats[14];
            twoPointersAttempted = stats[15];
            twoPointPercentage = stats[16];
            effectiveFieldGoalPercentage = stats[17];
            freeThrows = stats[18];
            freeThrowsAttempted = stats[19];
            freeThrowPercentage = stats[20];
            offensiveRebounds = stats[21];
            defensiveRebounds = stats[22];
            totalRebounds = stats[23];
            assists = stats[24];
            steals = stats[25];
            blocks = stats[26];
            turnovers = stats[27];
            personalFouls = stats[28];
            points = stats[29];
        } catch (Exception e) {
            System.out.println("Player Load Error");
        }
    }

    /**
     * Grabs stats from the player's link on Basketball Reference
     *
     * @return an ArrayList<String> With many stats about the player
     */
    public ArrayList<String> getStats() {
        ArrayList<String> stats = new ArrayList<String>();
        Document d;

        try {
            d = Jsoup.connect(link).get();

            String s = d.select("h1").text();
            stats.add(s);

            Elements elements = d.select("tr[id$=totals.2016]");
            for (Element e : elements.select("td")) {
                for (Element f : e.select("td")) {
                    String text = Jsoup.parse(f.toString()).text();
                    stats.add(text);
                }
            }

        } catch (IOException i) {
            System.out.println(i);
        } catch (IndexOutOfBoundsException i) {
            i.printStackTrace();
        }
        return stats;
    }

    public String getName() {
        return name;
    }

    public String[] getAllStats() {
        return allStats;
    }

    /**
     * Compares players a and b lexicographically
     *
     * @param b
     * @return
     */
    public int compareTo(Player b) {
        return this.name.compareTo(b.getName());
    }

    public String getAge() {
        return age;
    }

    public String getLink() {
        return link;
    }

    public String getTeam() {
        return team;
    }

    public String getLeague() {
        return league;
    }

    public String getPosition() {
        return position;
    }

    public String getGames() {
        return games;
    }

    public String getGamesStarted() {
        return gamesStarted;
    }

    public String getMinutesPlayed() {
        return minutesPlayed;
    }

    public String getFieldGoals() {
        return fieldGoals;
    }

    public String getFieldGoalAttempts() {
        return fieldGoalAttempts;
    }

    public String getFieldGoalPercentage() {
        return fieldGoalPercentage;
    }

    public String getThreePointers() {
        return threePointers;
    }

    public String getThreePointersAttempted() {
        return threePointersAttempted;
    }

    public String getThreePointPercentage() {
        return threePointPercentage;
    }

    public String getTwoPointers() {
        return twoPointers;
    }

    public String getTwoPointersAttempted() {
        return twoPointersAttempted;
    }

    public String getTwoPointPercentage() {
        return twoPointPercentage;
    }

    public String getEffectiveFieldGoalPercentage() {
        return effectiveFieldGoalPercentage;
    }

    public String getFreeThrows() {
        return freeThrows;
    }

    public String getFreeThrowsAttempted() {
        return freeThrowsAttempted;
    }

    public String getFreeThrowPercentage() {
        return freeThrowPercentage;
    }

    public String getOffensiveRebounds() {
        return offensiveRebounds;
    }

    public String getDefensiveRebounds() {
        return defensiveRebounds;
    }

    public String getTotalRebounds() {
        return totalRebounds;
    }

    public String getAssists() {
        return assists;
    }

    public String getSteals() {
        return steals;
    }

    public String getBlocks() {
        return blocks;
    }

    public String getTurnovers() {
        return turnovers;
    }

    public String getPersonalFouls() {
        return personalFouls;
    }

    public String getPoints() {
        return points;
    }

    public String toString() {
        return name;
    }

}

