package Business.Entities;

public class Stats {
    private String name;
    private int games_played;
    private int games_won;
    private int KO_done;
    private int KO_received;

    public Stats(String name, int gamesPlayed, int gamesWon, int koDone, int koRecieved) {
        this.name = name;
        this.games_played = gamesPlayed;
        this.games_won = gamesWon;
        this.KO_done = koDone;
        this.KO_received = koRecieved;
    }

    public String getName() {
        return name;
    }

    public int getGames_played() {
        return games_played;
    }

    public int getGames_won() {
        return games_won;
    }

    public int getKO_done() {
        return KO_done;
    }

    public int getKO_received() {
        return KO_received;
    }

    public void setName(String name) {
        this.name = name;
    }

}
