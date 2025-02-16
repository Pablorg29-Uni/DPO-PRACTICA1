package Business;

import Business.Entities.Items;
import Exceptions.BusinessException;
import Exceptions.PersistenceException;
import Persistence.ItemsJsonDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemsManager {
    private final ItemsJsonDAO itemsJsonDAO;

    public ItemsManager() {
        this.itemsJsonDAO = new ItemsJsonDAO();
    }

    public Items obtenirArmaRandom() throws BusinessException {
        do {
            Random rand = new Random();
            List<Items> allItems;
            try {
                allItems = itemsJsonDAO.getAllItems();
            } catch (PersistenceException e) {
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

    public Items obtenirArmaduraRandom() throws BusinessException {
        do {
            Random rand = new Random();
            List<Items> allItems;
            try {
                allItems = itemsJsonDAO.getAllItems();
            } catch (PersistenceException e) {
                throw new BusinessException(e.getMessage());
            }
            allItems.removeIf(i -> i.getDurability() < 1 || i.getClasse().equals("Armor"));
            if (allItems.isEmpty()) {
                return null;
            } else {
                int ind = rand.nextInt(allItems.size());
                //itemsJsonDAO.setDurability(allItems.get(ind).getId(), allItems.get(ind).getDurability() - 1);
                return allItems.get(ind);
            }
        } while (true);
    }

    public ArrayList<Items> showItems() throws BusinessException {
        try {
            return (ArrayList<Items>) itemsJsonDAO.getAllItems();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void verify() throws BusinessException {
        try {
            itemsJsonDAO.verifyJsonItem();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

}
