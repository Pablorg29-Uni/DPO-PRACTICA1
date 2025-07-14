package Business;

import Business.Entities.Armor;
import Business.Entities.Item;
import Business.Entities.Items;
import Business.Entities.Weapon;
import Exceptions.BusinessException;
import Exceptions.PersistenceException;
import Persistence.API.ConnectorAPIHelper;
import Persistence.Items.ItemsApiDAO;
import Persistence.Items.ItemsDAO;
import Persistence.Items.ItemsJsonDAO;
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
     * Obtiene un arma aleatoria de la base de datos de ítems.
     *
     * @return Un objeto {@link Items} representando el arma seleccionada aleatoriamente, o null si no hay armas disponibles.
     * @throws BusinessException Si ocurre un error al acceder a la base de datos de ítems.
     */
    public Weapon obtenirArmaRandom() throws BusinessException {
        do {
            Random rand = new Random();
            List<Weapon> allItems;
            try {
                allItems = itemsDAO.getAllWeapons();
            } catch (PersistenceException | ApiException e) {
                throw new BusinessException(e.getMessage());
            }
            allItems.removeIf(i -> i.getDurability() < 1);
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
    public Armor obtenirArmaduraRandom() throws BusinessException {
        do {
            Random rand = new Random();
            List<Armor> allItems;
            try {
                allItems = itemsDAO.getAllArmors();
            } catch (PersistenceException | ApiException e) {
                throw new BusinessException(e.getMessage());
            }
            allItems.removeIf(i -> i.getDurability() < 1);
            if (allItems.isEmpty()) {
                return null;
            } else {
                int ind = rand.nextInt(allItems.size());
                return allItems.get(ind);
            }
        } while (true);
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

    public List<Weapon> showWeapons() throws BusinessException {
        try {
            return itemsDAO.getAllWeapons();
        } catch (PersistenceException | ApiException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public List<Armor> showArmors() throws BusinessException {
        try {
            return itemsDAO.getAllArmors();
        } catch (PersistenceException | ApiException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Establece el helper para conectar con la API externa de ítems.
     *
     * @param apiHelper Instancia de {@link ConnectorAPIHelper} utilizada para establecer la conexión con la API.
     */
    public void setApiHelper(ConnectorAPIHelper apiHelper) {
        this.itemsDAO = new ItemsApiDAO(apiHelper);
    }

}