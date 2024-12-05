package Business;

import Business.Entities.Items;
import Persistence.ItemsJsonDAO;

import java.util.ArrayList;

public class ItemsManager {
    private final ItemsJsonDAO itemsJsonDAO;

    public ItemsManager() {
        this.itemsJsonDAO = new ItemsJsonDAO();
    }

    public ArrayList<Items> showItems() {
        return (ArrayList<Items>) itemsJsonDAO.getAllItems();
    }

    public Items showOneItem(int id) {
        return itemsJsonDAO.getItem(id);
    }
}
