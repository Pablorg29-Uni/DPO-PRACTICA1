package Business.Entities;

public class Stats {
    private String name;
    private int games_played;
    private int games_won;
    private int KO_done;
    private int KO_received;

    // Constructor con parámetros
    public Stats(String name, int gamesPlayed, int gamesWon, int koDone, int koRecieved) {
        this.name = name;
        this.games_played = gamesPlayed;
        this.games_won = gamesWon;
        this.KO_done = koDone;
        this.KO_received = koRecieved;
    }

    // Métodos Getters
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

    // Métodos Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setGames_played(int games_played) {
        this.games_played = games_played;
    }

    public void setGames_won(int games_won) {
        this.games_won = games_won;
    }

    public void setKO_done(int KO_done) {
        this.KO_done = KO_done;
    }

    public void setKO_received(int KO_received) {
        this.KO_received = KO_received;
    }
}
