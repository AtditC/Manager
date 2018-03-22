package network.warzone.manager.gui;

import lombok.Getter;
import network.warzone.manager.Manager;
import network.warzone.manager.model.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jorge on 2/23/2018.
 */

@Getter
public class GuiTagManager {

    private Inventory inventory;
    private Player player;
    private PlayerProfile profile;

    private Map<Integer, TagItem> tagItemMap = new HashMap<>();

    public GuiTagManager(Player player) {
        this.player = player;
        this.profile = Manager.get().getPlayerManager().getPlayerProfile(player);
        createInventory();
        refresh();
        Manager.get().getGuiManager().addGui(player, this);
    }

    private void createInventory() {
        this.inventory = Bukkit.createInventory(player, 9*5, ChatColor.UNDERLINE + "Tag Manager (" + player.getName() + ")");
    }

    public void onClick(int slot) {
        if (slot == 4) {
            profile.setDisplayTag("");
            player.playSound(player.getLocation().clone(), Sound.BLOCK_NOTE_PLING, 1f, 1f);
            refresh();
        } else if (tagItemMap.containsKey(slot)) {
            profile.setDisplayTag(tagItemMap.get(slot).getTag());
            player.playSound(player.getLocation().clone(), Sound.BLOCK_NOTE_PLING, 1f, 1f);
            refresh();
        }
    }

    private void refresh() {
        if (this.inventory == null) createInventory();
        this.inventory.clear();
        this.tagItemMap.clear();
        ItemStack disableTagItem = new ItemStack(Material.BARRIER);
        ItemMeta meta = disableTagItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Disable Tag");
        disableTagItem.setItemMeta(meta);
        inventory.setItem(4, disableTagItem);
        for (int i = 0; i < profile.getTags().size(); i++) {
            int slot = 18 + i;
            TagItem tagItem = new TagItem(profile.getTags().get(i));
            tagItemMap.put(slot, tagItem);
            inventory.setItem(slot, tagItem.getItemStack());
        }
    }

    @Getter
    private class TagItem {
        private String tag;
        private ItemStack itemStack;

        public TagItem(String tag) {
            this.tag = tag;
            this.itemStack = new ItemStack(this.tag.equals(profile.getDisplayTag()) ? Material.EMPTY_MAP : Material.PAPER);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', tag));
            meta.setLore(Arrays.asList("",ChatColor.GRAY + "Preview: [" + ChatColor.translateAlternateColorCodes('&', tag) + ChatColor.GRAY + "]"));
            this.itemStack.setItemMeta(meta);
        }

    }

}
