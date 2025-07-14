    package Business.Entities;

    import Business.Entities.Weapon;
    import Business.Entities.Armor;
    import Business.Entities.AttackStrategy;
    import Business.Entities.BalancedStrategy;
    import Business.Entities.OffensiveStrategy;
    import Business.Entities.DefensiveStrategy;
    import Business.Entities.SniperStrategy;

    import com.google.gson.annotations.Expose;
    import com.google.gson.annotations.SerializedName;

    /**
     * Representa a un miembro dentro del sistema.
     * <p>
     * Contiene información relevante sobre el miembro, como su nombre, rol, equipamiento y estado de combate.
     */
    public class Member {
        @Expose
        private long id;
        private Character character;
        private Items items;
        @Expose
        @SerializedName("strategy")
        private String strategyName;
        @Expose(serialize = false, deserialize = false)
        private AttackStrategy attackStrategy;
        @Expose(serialize = false, deserialize = false)
        private float malRebut;
        private String role;
        @Expose(serialize = false, deserialize = false)
        private boolean isKO;
        @Expose(serialize = false, deserialize = false)
        private float damageReduction;
        private LastAttack lastAttack;

        /**
         * Constructor de la clase Member.
         * Inicializa los atributos con valores por defecto.
         */
        public Member() {
            this.character = null;
            this.items = null;
            this.role = null;
            this.lastAttack = null;
            this.strategyName = "balanced";
            setStrategyByName(this.strategyName);
        }

        /**
         * Constructor de la clase Member con un ID y estrategia.
         *
         * @param id       Identificador único del miembro.
         * @param strategyName Estrategia asignada al miembro.
         */
        public Member(long id, String strategyName) {
            this();
            this.id = id;
            this.strategyName = strategyName;
            setStrategyByName(strategyName);
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
    public Armor getArmadura() {
        return items.getArmor();
    }

    /**
     * Establece la armadura equipada por el miembro.
     *
     * @param armadura Nueva armadura equipada.
     */
    public void setArmadura(Armor armadura) {
        this.items.setArmor(armadura);
    }

    /**
     * Obtiene el arma equipada por el miembro.
     *
     * @return Arma equipada.
     */
    public Weapon getArma() {
        return items.getWeapon();
    }

    public float getAttackDmg () {
        if (getArma() != null) {
            return getArma().getAttackDmg(this);
        } else {
            float part1 = (getCharacter().getWeight() * (1 - getMalRebut()) / 10);
            return Math.max(part1 + 18, 0);
        }
    }

    public float getFinalDmg (float attack) {
        if (getArma() != null) {
            return getArmadura().getFinalDmg(this, attack);
        } else {
            float part1 = (200 * (1 - getMalRebut())) / getCharacter().getWeight();
            return (attack - (part1 * 1.4f) / 100);
        }
    }

    /**
     * Establece el arma equipada por el miembro.
     *
     * @param arma Nueva arma equipada.
     */
    public void setArma(Weapon arma) {
        this.items.setWeapon(arma);
    }

    /**
     * Obtiene la estrategia asignada al miembro.
     *
     * @return Estrategia actual.
     */
    public AttackStrategy getStrategy() {
        return attackStrategy;
    }

    /**
     * Establece la estrategia asignada al miembro.
     *
     * @param strategy Nueva estrategia.
     */
    public void setStrategy(AttackStrategy strategy) {
        this.attackStrategy = strategy;
    }

    public void setStrategyByName(String strategyName) {
        this.strategyName = strategyName;
        switch (strategyName.toLowerCase()) {
            case "balanced":
                this.attackStrategy = new BalancedStrategy();
                break;
            case "offensive":
                this.attackStrategy = new OffensiveStrategy();
                break;
            case "defensive":
                this.attackStrategy = new DefensiveStrategy();
                break;
            case "sniper":
                this.attackStrategy = new SniperStrategy();
                break;
            default:
                this.attackStrategy = new BalancedStrategy();
        }
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


    public void setItems(Items items) {
        this.items = items;
    }

    public Items getItems() {
        return items;
    }

    public String getStrategyName() {
        if (attackStrategy instanceof BalancedStrategy) return "balanced";
        if (attackStrategy instanceof OffensiveStrategy) return "offensive";
        if (attackStrategy instanceof DefensiveStrategy) return "defensive";
        if (attackStrategy instanceof SniperStrategy) return "sniper";
        return "balanced";
    }

    public void postDeserialize() {
        setStrategyByName(this.strategyName);
    }


}