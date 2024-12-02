package Business;

public class Character {

        private int id;       // Identificador único
        private String name;   // Nombre único
        private int weight;    // Peso del personaje

        public Character(int id, String name, int weight) {
            this.id = id;
            this.name = name;
            this.weight = weight;
        }

        // Getter para 'id'
        public int getId() {
            return id;
        }

        // Getter para 'name'
        public String getName() {
            return name;
        }

        // Getter para 'weight'
        public int getWeight() {
            return weight;
        }

}
