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

    private Map<Player, GuiTagManager> tagGuis = new HashMap<>();
    private Map<Player, GuiSoundManager> soundGuis = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player) || event.getInventory() == null || event.getClickedInventory() == null) return;
        Player player = (Player) event.getWhoClicked();
        GuiTagManager tagGui = tagGuis.get(player);
        if (tagGui != null) {
            if (event.getInventory().equals(tagGui.getInventory())) {
                event.setCancelled(true);
                if (event.getClickedInventory().equals(tagGui.getInventory())) {
                    tagGui.onClick(event.getSlot());
                }
                return;
            }
        }

        GuiSoundManager soundGui = soundGuis.get(player);
        if (soundGui != null) {
            if (event.getInventory().equals(soundGui.getInventory())) {
                event.setCancelled(true);
                if (event.getClickedInventory().equals(soundGui.getInventory())) {
                    soundGui.onClick(event.getSlot(), event.getClick());
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        removeGui((Player) event.getPlayer());
    }

    public void addGui(Player player, GuiTagManager guiTagManager) {
        tagGuis.put(player, guiTagManager);
    }

    public void addGui(Player player, GuiSoundManager guiSoundManager) {
        soundGuis.put(player, guiSoundManager);
    }

    public void removeGui(Player player) {
        tagGuis.remove(player);
        soundGuis.remove(player);
    }

}
