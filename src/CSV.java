import java.io.*;
import java.util.ArrayList;

/**
 * Created by alimousa on 5/18/16.
 * CSV already loaded into file for faster performance
 * If desired user can refresh the files for the updated statistics
 * Each time user updates files, the new CSV files replace the existing files
 */
public class CSV {
    private static final String DELIMITER = ",";
    private static final String NEWLINE = "\n";
    private static final String HEADER = "Name,Age,Team,League,Position,Games,Games Started,Minutes Played,Field Goals," +
            "Field Goal Attempts,Field Goal Percentage,Three Pointers,Three Pointers Attempted,Three Point Percentage," +
            "Two Pointers,Two Pointers Attempted,Two Point Percentage,Effective Field Goal Percentage,Free Throws," +
            "Free Throws Attempted,Free Throw Percentage,Offensive Rebounds,Defensive Rebounds,Total Rebounds," +
            "Assists,Steals,Blocks,Turnovers,Personal Fouls,Points";
    private static final String HEADER_NO_SPACES = "Name,Age,Team,League,Position,Games,GamesStarted,MinutesPlayed,FieldGoals," +
            "FieldGoalAttempts,FieldGoalPercentage,ThreePointers,ThreePointersAttempted,ThreePointPercentage," +
            "TwoPointers,TwoPointersAttempted,TwoPointPercentage,EffectiveFieldGoalPercentage,FreeThrows," +
            "FreeThrowsAttempted,FreeThrowPercentage,OffensiveRebounds,DefensiveRebounds,TotalRebounds," +
            "Assists,Steals,Blocks,Turnovers,PersonalFouls,Points";

    /**
     * Writes all the team's stats and the players on the
     * team's stats to the CSV file with name "TeamName.csv"
     *
     * @param team Team Object to be recorded
     */
    public String write(Team team) {
        String rtn = System.getProperty("file.separator") + System.getProperty("user.dir") + System.getProperty("file.separator")
                + "Teams" + System.getProperty("file.separator") + team.getName() + ".csv";
        Player[] players = team.getPlayers();
        ArrayList<String> teamStats = team.getStats();
        if (teamStats.size() < 1)
            return rtn;

        teamStats.add(0, teamStats.remove(teamStats.size() - 1));
        teamStats.add(1, "");
        teamStats.add(2, team.getName());
        teamStats.add(3, "NBA");
        teamStats.add(4, "");
        teamStats.add(6, "");

        FileWriter f = null;
        try {
            f = new FileWriter(rtn);

            f.append(HEADER);
            f.append(NEWLINE);

            for (String stat : teamStats) {
                f.append(stat);
                f.append(DELIMITER);
            }

            f.append(NEWLINE);

            for (Player p : players) {
                ArrayList<String> stats = p.getStats();
                stats.remove(1);
                for (int i = 0; i <= 29; i++) {
                    try {
                        f.append(stats.get(i));
                        f.append(DELIMITER);
                    } catch (Exception e) {
                        f.append(DELIMITER);
                        System.out.println("CSV Write Error\n");
                    }
                }
                f.append(NEWLINE);
            }

        } catch (Exception e) {
            System.out.println("CSV Write Error\n");
            e.printStackTrace();
        } finally {
            try {
                f.flush();
                f.close();
            } catch (Exception e) {
                System.out.println("Flush/Close CSV Write error");
                e.printStackTrace();
            }
        }
        return rtn;
    }

    public ArrayList<String[]> read(String filename) {
        BufferedReader fileReader = null;
        ArrayList<String[]> returns = new ArrayList<String[]>();
        try {
            int count = 0;
            String line = "";
            fileReader = new BufferedReader(new FileReader(filename));
            String[] stats = null;

            while ((line = fileReader.readLine()) != null) {
                stats = line.split(",");
                returns.add(stats);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return returns;
    }

    public static String getHeader() {
        return HEADER;
    }

    public static String getHeaderNoSpaces() {
        return HEADER_NO_SPACES;
    }
}
