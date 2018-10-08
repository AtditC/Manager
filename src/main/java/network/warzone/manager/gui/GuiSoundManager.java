package network.warzone.manager.gui;

import lombok.Getter;
import network.warzone.manager.Manager;
import network.warzone.manager.model.PlayerProfile;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jorge on 2/23/2018.
 */

@Getter
public class GuiSoundManager {

    private Inventory inventory;
    private Player player;
    private PlayerProfile profile;

    private Map<Integer, SoundItem> soundItemMap = new HashMap<>();

    public GuiSoundManager(Player player) {
        this.player = player;
        this.profile = Manager.get().getPlayerManager().getPlayerProfile(player);
        createInventory();
        refresh();
        Manager.get().getGuiManager().addGui(player, this);
    }

    private void createInventory() {
        this.inventory = Bukkit.createInventory(player, 9*5, ChatColor.UNDERLINE + "Sound Manager (" + player.getName() + ")");
    }

    public void onClick(int slot, ClickType clickType) {
        SoundItem soundItem;
        if (slot == 4) {
            profile.setSound(null);
            player.playSound(player.getLocation().clone(), Sound.BLOCK_NOTE_PLING, 1f, 1f);
            refresh();
        } else if ((soundItem = soundItemMap.get(slot)) != null) {
            if (clickType == ClickType.RIGHT) {
                player.playSound(player.getEyeLocation(), soundItem.sound, 2.0f, 1.0f);
            } else {
                profile.setSound(soundItem.sound);
                player.playSound(player.getLocation().clone(), Sound.BLOCK_NOTE_PLING, 1f, 1f);
                refresh();
            }
        }
    }

    private void refresh() {
        if (this.inventory == null) createInventory();
        this.inventory.clear();
        this.soundItemMap.clear();
        ItemStack disableSoundItem = new ItemStack(Material.BARRIER);
        ItemMeta meta = disableSoundItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Disable Join Sound");
        disableSoundItem.setItemMeta(meta);
        inventory.setItem(4, disableSoundItem);
        int i = 0;
        for (; i < profile.getJoinSounds().size(); i++) {
            int slot = i + 18;
            SoundItem soundItem = new SoundItem(profile.getJoinSounds().get(i));
            soundItemMap.put(slot, soundItem);
            inventory.setItem(slot, soundItem.getItemStack());
        }
        for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) {
            if (perm.getPermission().startsWith("warzone.sounds.")) {
                String soundName = perm.getPermission().replace("warzone.sounds.", "").toUpperCase();
                try {
                    int slot = i + 18;
                    Sound sound = Sound.valueOf(soundName);
                    SoundItem soundItem = new SoundItem(sound);
                    soundItemMap.put(slot, soundItem);
                    inventory.setItem(slot, soundItem.getItemStack());
                    i++;
                    if (i >= 54) break;
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
    }

    @Getter
    private class SoundItem {
        private Sound sound;
        private ItemStack itemStack;

        public SoundItem(Sound sound) {
            this.sound = sound;
            this.itemStack = new ItemStack(this.sound == profile.getJoinSound() ? Material.GREEN_RECORD : Material.RECORD_8);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(WordUtils.capitalize(sound.name().toLowerCase().replace("_", " ")));
            meta.setLore(Arrays.asList("", ChatColor.WHITE + "Right click to hear the sound."));
            this.itemStack.setItemMeta(meta);
        }

    }

}
