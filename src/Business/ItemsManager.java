package Business;

import Business.Entities.Items;
import Persistence.ItemsJsonDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemsManager {
    private final ItemsJsonDAO itemsJsonDAO;

    public ItemsManager() {
        this.itemsJsonDAO = new ItemsJsonDAO();
    }

    public Items obtenirArmaRandom() {
        do {
            Random rand = new Random();
            List<Items> allItems = itemsJsonDAO.getAllItems();
            for (Items i : allItems) {
                if (i.getDurability() < 1 || i.getClasse().equals("Weapon"))
                    allItems.remove(i);
            }
            if (allItems.isEmpty()) {
                return null;
            } else {
                int ind = rand.nextInt(allItems.size());
                itemsJsonDAO.setDurability(allItems.get(ind).getId(), allItems.get(ind).getDurability() - 1);
                return allItems.get(ind);
            }
        } while (true);
    }

    public Items obtenirArmaduraRandom() {
        do {
            Random rand = new Random();
            List<Items> allItems = itemsJsonDAO.getAllItems();
            for (Items i : allItems) {
                if (i.getDurability() < 1 || i.getClasse().equals("Armor"))
                    allItems.remove(i);
            }
            if (allItems.isEmpty()) {
                return null;
            } else {
                int ind = rand.nextInt(allItems.size());
                itemsJsonDAO.setDurability(allItems.get(ind).getId(), allItems.get(ind).getDurability() - 1);
                return allItems.get(ind);
            }
        } while (true);
    }

    public ArrayList<Items> showItems() {
        return (ArrayList<Items>) itemsJsonDAO.getAllItems();
    }

    public Items showOneItem(long id) {
        return itemsJsonDAO.getItem(id);
    }
}
