package network.warzone.manager.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jorge on 2/23/2018.
 */
public class GuiManager implements Listener {

    private Map<Player, GuiTagManager> guis = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!guis.containsKey((Player) event.getWhoClicked())) return;
        GuiTagManager gui = guis.get((Player) event.getWhoClicked());
        if (event.getInventory().equals(gui.getInventory())) {
            event.setCancelled(true);
            if (event.getClickedInventory() != null && event.getClickedInventory().equals(gui.getInventory())) {
                gui.onClick(event.getSlot());
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        removeGui((Player) event.getPlayer());
    }

    public void addGui(Player player, GuiTagManager guiTagManager) {
        guis.put(player, guiTagManager);
    }

    public void removeGui(Player player) {
        guis.remove(player);
    }

}
