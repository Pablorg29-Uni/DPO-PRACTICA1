package Business.Entities;

public class Stats {
    private String name;
    private int gamesPlayed;
    private int gamesWon;
    private int koDone;
    private int koRecieved;

    // Constructor vacío
    public Stats() {
        this.name = "";
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.koDone = 0;
        this.koRecieved = 0;
    }

    // Constructor con parámetros
    public Stats(String name, int gamesPlayed, int gamesWon, int koDone, int koRecieved) {
        this.name = name;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.koDone = koDone;
        this.koRecieved = koRecieved;
    }

    // Métodos Getters
    public String getName() {
        return name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getKoDone() {
        return koDone;
    }

    public int getKoRecieved() {
        return koRecieved;
    }

    // Métodos Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setKoDone(int koDone) {
        this.koDone = koDone;
    }

    public void setKoRecieved(int koRecieved) {
        this.koRecieved = koRecieved;
    }
}
