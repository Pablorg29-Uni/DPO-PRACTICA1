package Business.Entities;

import com.google.gson.annotations.Expose;

/**
 * Representa a un miembro dentro del sistema.
 *
 * Contiene información relevante sobre el miembro, como su nombre, rol, equipamiento y estado de combate.
 */
public class Member {
    @Expose
    private long id;
    private Character character;
    private Items armadura;
    private Items arma;
    @Expose
    private String strategy;
    @Expose(serialize = false, deserialize = false)
    private float malRebut;
    private String role;
    @Expose(serialize = false, deserialize = false)
    private boolean isKO;
    @Expose(serialize = false, deserialize = false)
    private float damageReduction;
    private LastAttack lastAttack;
    private String nameArma;
    private String nameArmadura;

    /**
     * Constructor de la clase Member.
     * Inicializa los atributos con valores por defecto.
     */
    public Member() {
        this.character = null;
        this.armadura = null;
        this.arma = null;
        this.role = null;
        this.lastAttack = null;
    }

    /**
     * Constructor de la clase Member con un ID y estrategia.
     *
     * @param id Identificador único del miembro.
     * @param strategy Estrategia asignada al miembro.
     */
    public Member(long id, String strategy) {
        this();
        this.id = id;
        this.strategy = strategy;
    }

    /**
     * Obtiene el valor de malRebut.
     *
     * @return Valor actual de malRebut.
     */
    public float getMalRebut() {
        return malRebut;
    }

    /**
     * Establece el valor de malRebut.
     *
     * @param malRebut Nuevo valor de malRebut.
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
     * Obtiene la armadura equipada por el miembro.
     *
     * @return Armadura equipada.
     */
    public Items getArmadura() {
        return armadura;
    }

    /**
     * Establece la armadura equipada por el miembro.
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
     * Obtiene el arma equipada por el miembro.
     *
     * @return Arma equipada.
     */
    public Items getArma() {
        return arma;
    }

    /**
     * Establece el arma equipada por el miembro.
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
     * Obtiene la estrategia asignada al miembro.
     *
     * @return Estrategia actual.
     */
    public String getStrategy() {
        return strategy;
    }

    /**
     * Establece la estrategia asignada al miembro.
     *
     * @param strategy Nueva estrategia.
     */
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    /**
     * Obtiene el rol del miembro dentro del sistema.
     *
     * @return Rol actual.
     */
    public String getRole() {
        return role;
    }

    /**
     * Indica si el miembro está en estado KO (noqueado).
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
     * Obtiene la reducción de daño que tiene el miembro.
     *
     * @return Valor de reducción de daño.
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
     * Obtiene el identificador único del miembro.
     *
     * @return Identificador del miembro.
     */
    public long getId() {
        return id;
    }

    /**
     * Establece el identificador único del miembro.
     *
     * @param id Nuevo identificador.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtiene la información del último ataque realizado por el miembro.
     *
     * @return Último ataque registrado.
     */
    public LastAttack getLastAttack() {
        return lastAttack;
    }

    /**
     * Establece la información del último ataque realizado por el miembro.
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