package Business.Entities;

public class Stats {
    private String name;
    private int games_played;
    private int games_won;
    private int KO_done;
    private int KO_received;

    /**
     * Constructor de la clase Stats.
     *
     * @param name Nombre del jugador o entidad.
     * @param gamesPlayed Número de juegos jugados.
     * @param gamesWon Número de juegos ganados.
     * @param koDone Número de KOs realizados.
     * @param koRecieved Número de KOs recibidos.
     */
    public Stats(String name, int gamesPlayed, int gamesWon, int koDone, int koRecieved) {
        this.name = name;
        this.games_played = gamesPlayed;
        this.games_won = gamesWon;
        this.KO_done = koDone;
        this.KO_received = koRecieved;
    }

    /**
     * Obtiene el nombre del jugador o entidad.
     *
     * @return Nombre del jugador o entidad.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el número de juegos jugados.
     *
     * @return Número de juegos jugados.
     */
    public int getGames_played() {
        return games_played;
    }

    /**
     * Obtiene el número de juegos ganados.
     *
     * @return Número de juegos ganados.
     */
    public int getGames_won() {
        return games_won;
    }

    /**
     * Obtiene el número de KOs realizados.
     *
     * @return Número de KOs realizados.
     */
    public int getKO_done() {
        return KO_done;
    }

    /**
     * Obtiene el número de KOs recibidos.
     *
     * @return Número de KOs recibidos.
     */
    public int getKO_received() {
        return KO_received;
    }

    /**
     * Establece el nombre del jugador o entidad.
     *
     * @param name Nuevo nombre del jugador o entidad.
     */
    public void setName(String name) {
        this.name = name;
    }
}

