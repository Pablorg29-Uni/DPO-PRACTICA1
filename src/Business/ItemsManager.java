package Business;

import Business.Entities.Items;
import Exceptions.BusinessException;
import Exceptions.PersistenceException;
import Persistence.API.ConnectorAPIHelper;
import Persistence.Items.ItemsApiDAO;
import Persistence.Items.ItemsDAO;
import Persistence.Items.ItemsJsonDAO;
import Persistence.Team.TeamJsonDAO;
import edu.salle.url.api.exception.ApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gestiona la lógica de los ítems en el juego.
 * Actúa como intermediario entre la capa de persistencia y el resto de la aplicación.
 */
public class ItemsManager {
    private ItemsDAO itemsDAO;

    /**
     * Constructor de ItemsManager. Inicializa el gestor de persistencia de ítems.
     */
    public ItemsManager() {
        this.itemsDAO = new ItemsJsonDAO();
    }

    /**
     * Obtiene un arma aleatoria de la base de datos de ítems.
     *
     * @return Un objeto {@link Items} representando el arma seleccionada aleatoriamente, o null si no hay armas disponibles.
     * @throws BusinessException Si ocurre un error al acceder a la base de datos de ítems.
     */
    public Items obtenirArmaRandom() throws BusinessException {
        do {
            Random rand = new Random();
            List<Items> allItems;
            try {
                allItems = itemsDAO.getAllItems();
            } catch (PersistenceException | ApiException e) {
                throw new BusinessException(e.getMessage());
            }
            allItems.removeIf(i -> i.getDurability() < 1 || i.getClasse().equals("Armor"));
            if (allItems.isEmpty()) {
                return null;
            } else {
                int ind = rand.nextInt(allItems.size());
                return allItems.get(ind);
            }
        } while (true);
    }

    /**
     * Obtiene una armadura aleatoria de la base de datos de ítems.
     *
     * @return Un objeto {@link Items} representando la armadura seleccionada aleatoriamente, o null si no hay armaduras disponibles.
     * @throws BusinessException Si ocurre un error al acceder a la base de datos de ítems.
     */
    public Items obtenirArmaduraRandom() throws BusinessException {
        do {
            Random rand = new Random();
            List<Items> allItems;
            try {
                allItems = itemsDAO.getAllItems();
            } catch (PersistenceException | ApiException e) {
                throw new BusinessException(e.getMessage());
            }
            allItems.removeIf(i -> i.getDurability() < 1 || i.getClasse().equals("Weapon"));
            if (allItems.isEmpty()) {
                return null;
            } else {
                int ind = rand.nextInt(allItems.size());
                return allItems.get(ind);
            }
        } while (true);
    }

    /**
     * Muestra todos los ítems disponibles en la base de datos.
     *
     * @return Una lista de objetos {@link Items} con todos los ítems disponibles.
     * @throws BusinessException Si ocurre un error al recuperar los ítems.
     */
    public ArrayList<Items> showItems() throws BusinessException {
        try {
            return (ArrayList<Items>) itemsDAO.getAllItems();
        } catch (PersistenceException | ApiException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Verifica la integridad del archivo JSON de ítems.
     *
     * @throws BusinessException Si ocurre un error al verificar la base de datos de ítems.
     */
    public void verify() throws BusinessException {
        if (!ItemsJsonDAO.canConnect()) {
            throw new BusinessException("No connection established");
        } else {
            itemsDAO = new ItemsJsonDAO();
        }
    }

    public void setApiHelper(ConnectorAPIHelper apiHelper) {
        this.itemsDAO = new ItemsApiDAO(apiHelper);
    }
}