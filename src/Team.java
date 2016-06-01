/**
 * Created by alimousa on 5/4/16.
 * <p>
 * Created by jonathanlansang on 5/13/16.
 * <p>
 * Created by jonathanlansang on 5/13/16.
 * <p>
 * Created by jonathanlansang on 5/13/16.
 * <p>
 * Created by jonathanlansang on 5/13/16.
 * <p>
 * Created by jonathanlansang on 5/13/16.
 * <p>
 * Created by jonathanlansang on 5/13/16.
 * <p>
 * Created by jonathanlansang on 5/13/16.
 * <p>
 * Created by jonathanlansang on 5/13/16.
 */
/**
 * Created by jonathanlansang on 5/13/16.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Team implements Comparable<Team> {
    private Player[] players;
    private String teamLink;
    private String name;


    public Team(String link) {
        teamLink = link;
        ArrayList<String> stats = getStats();
        //ArrayList<String> playersList = getPlayerLinks(link);
        name = stats.get(stats.size() - 1);

        ArrayList<String> playersList = getPlayerLinks(link);

        players = new Player[playersList.size()];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(playersList.get(i));
        }
    }

    public Team(ArrayList<String[]> statistics) {
        String[] stats = statistics.get(1);
        name = stats[0];

        players = new Player[statistics.size() - 2];
        for (int i = 2; i < statistics.size(); i++) {
            players[i - 2] = new Player(statistics.get(i));
        }
        if (players.length != 0)
            doSort();
    }

    /**
     * Grabs stats from the team's link on Basketball Reference
     * @return an ArrayList<String> With many stats about the team
     */
    public ArrayList<String> getStats() {
        ArrayList<String> stats = new ArrayList<String>();
        try {
            Document d = Jsoup.connect(teamLink).get();
            Elements elements = d.select("tr[class$=stat_average no_ranker]").select("td");
            for (Element e : elements) {
                String s = Jsoup.parse(e.toString()).text();
                if (!s.equals("") && !s.equals("Team Totals"))
                    stats.add(s);
            }
            Elements elements1 = d.select("h1");
            String x = elements1.text();
            x = x.substring(0, x.indexOf(" Roster and Stats"));
            stats.add(x);
        } catch (IOException i) {
            System.out.println(i.getMessage());
        }
        System.out.println("Getting stats for Team");
        return stats;
    }

    /**
     * Returns an ArrayList<String> of links to player's pages on Basketball Reference
     * @param teamLink A link to the team's page on Basketball Reference
     * @return an ArrayList<String> With links to player's pages
     */
    public ArrayList<String> getPlayerLinks(String teamLink) {
        ArrayList<String> links = new ArrayList<String>();
        try {
            Document d = Jsoup.connect(teamLink).get();
            Elements elements = d.select("table[id$=roster]").select("a");
            for (Element e : elements) {
                if (e.attr("abs:href").indexOf("http://www.basketball-reference.com/players/") == 0)
                    links.add(e.attr("abs:href"));
            }
        } catch (IOException i) {
            System.out.println(i.getMessage());
        }
        System.out.println("Getting Player Links for Team: " + name);
        return links;
    }

    public void doSort() {
        quickSort(players, 0, players.length - 1);
    }

    private void quickSort(Player[] list, int first, int last) {
        int g = first, h = last;
        int pivot = (last + first) / 2;
        Player pivotValue = list[pivot];
        do {
            while (list[g].compareTo(pivotValue) < 0)
                g++;
            while (list[h].compareTo(pivotValue) > 0)
                h--;
            if (g <= h) {
                Player temp = list[g];
                list[g] = list[h];
                list[h] = temp;
                g++;
                h--;
            }
        } while (g < h);
        if (h > first)
            quickSort(list, first, h);
        if (g < last)
            quickSort(list, g, last);
    }

    /**
     * Returns an array of Player objects consisting of players on the team
     *
     * @return an array of Player objects consisting of players on the team
     */
    public Player[] getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public int compareTo(Team b) {
        return this.getName().compareTo(b.getName());
    }

    public String toString() {
        /*String rtn = "";
        String[] statTypes = CSV.getHeader().split(",");
        for (String s : statTypes) {
            rtn += s + "\t\t\t\t";
        }
        rtn += "\n";
        for (Player p : players) {
            rtn += p.toString() + "\n";
        }
        return rtn;*/
        return name;
    }

}
