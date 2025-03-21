package Persistence.Items;

import Business.Entities.Items;
import Exceptions.PersistenceException;
import edu.salle.url.api.exception.ApiException;

import java.util.List;

public interface ItemsDAO {

    void verifyJsonItem() throws PersistenceException;

    List<Items> getAllItems() throws PersistenceException, ApiException;
}
