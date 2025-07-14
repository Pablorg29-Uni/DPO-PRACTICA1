package Persistence.Items;

import Business.Entities.Armor;
import Business.Entities.Weapon;
import Exceptions.PersistenceException;
import edu.salle.url.api.exception.ApiException;

import java.util.List;

/**
 * Interfaz para el acceso a datos de ítems.
 * Define el método para obtener todos los ítems.
 */
public interface ItemsDAO {

    List<Weapon> getAllWeapons() throws PersistenceException, ApiException;
    List<Armor> getAllArmors() throws PersistenceException, ApiException;

}