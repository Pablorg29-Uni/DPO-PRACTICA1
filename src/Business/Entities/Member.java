package Business.Entities;
/**
 * Representa a un miembro dentro del sistema.
 *
 * Contiene información relevante sobre el miembro, como su nombre, rol, etc.
 */
public class Member {
    private long id;
    private Character character;
    private Items armadura;
    private Items arma;
    private String strategy;
    private float malRebut;
    private String role;
    private boolean isKO;
    private float damageReduction;
    private LastAttack lastAttack;
    private String nameArma;
    private String nameArmadura;

    /**
     * Constructor de la clase Member.
     */
    public Member() {
        this.character = null;
        this.armadura = null;
        this.arma = null;
        this.strategy = null;
        this.role = null;
        this.lastAttack = null;
    }

    /**
     * Constructor de la clase Member con un ID.
     *
     * @param id Identificador del miembro.
     */
    public Member(long id) {
        this();
        this.id = id;
    }

    /**
     * Obtiene el malRebut.
     *
     * @return de malRebut.
     */
    public float getMalRebut() {
        return malRebut;
    }

    /**
     * Establece el malRebut.
     *
     * @param malRebut Nuevo malRebut.
     */
    public void setMalRebut(float malRebut) {
        this.malRebut = malRebut;
    }

    /**
     * Obtiene el personaje asociado al miembro.
     *
     * @return Personaje asociado.
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Establece el personaje asociado al miembro.
     *
     * @param character Nuevo personaje asociado.
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * Obtiene la armadura equipada.
     *
     * @return Armadura equipada.
     */
    public Items getArmadura() {
        return armadura;
    }

    /**
     * Establece la armadura equipada.
     *
     * @param armadura Nueva armadura equipada.
     */
    public void setArmadura(Items armadura) {
        if (armadura != null) {
            this.nameArmadura = armadura.getName();
        }
        this.armadura = armadura;
    }

    /**
     * Obtiene el arma equipada.
     *
     * @return Arma equipada.
     */
    public Items getArma() {
        return arma;
    }

    /**
     * Establece el arma equipada.
     *
     * @param arma Nueva arma equipada.
     */
    public void setArma(Items arma) {
        if (arma != null) {
            this.nameArma = arma.getName();
        }
        this.arma = arma;
    }

    /**
     * Obtiene la estrategia del miembro.
     *
     * @return Estrategia del miembro.
     */
    public String getStrategy() {
        return strategy;
    }

    /**
     * Obtiene el rol del miembro.
     *
     * @return Rol del miembro.
     */
    public String getRole() {
        return role;
    }

    /**
     * Indica si el miembro está KO.
     *
     * @return true si está KO, false en caso contrario.
     */
    public boolean isKO() {
        return isKO;
    }

    /**
     * Establece el estado KO del miembro.
     *
     * @param KO Nuevo estado KO.
     */
    public void setKO(boolean KO) {
        isKO = KO;
    }

    /**
     * Obtiene la reducción de daño del miembro.
     *
     * @return Reducción de daño.
     */
    public float getDamageReduction() {
        return damageReduction;
    }

    /**
     * Establece la reducción de daño del miembro.
     *
     * @param damageReduction Nuevo valor de reducción de daño.
     */
    public void setDamageReduction(float damageReduction) {
        this.damageReduction = damageReduction;
    }

    /**
     * Obtiene el identificador del miembro.
     *
     * @return Identificador del miembro.
     */
    public long getId() {
        return id;
    }

    /**
     * Establece el identificador del miembro.
     *
     * @param id Nuevo identificador.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtiene la última información de ataque del miembro.
     *
     * @return Último ataque registrado.
     */
    public LastAttack getLastAttack() {
        return lastAttack;
    }

    /**
     * Establece la última información de ataque del miembro.
     *
     * @param lastAttack Nueva información del último ataque.
     */
    public void setLastAttack(LastAttack lastAttack) {
        this.lastAttack = lastAttack;
    }

    /**
     * Obtiene el nombre del arma equipada.
     *
     * @return Nombre del arma.
     */
    public String getNameArma() {
        return nameArma;
    }

    /**
     * Obtiene el nombre de la armadura equipada.
     *
     * @return Nombre de la armadura.
     */
    public String getNameArmadura() {
        return nameArmadura;
    }
}