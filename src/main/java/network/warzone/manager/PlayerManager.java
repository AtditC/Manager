package network.warzone.manager;

import com.google.gson.reflect.TypeToken;
import network.warzone.manager.model.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Jorge on 2/23/2018.
 */

public class PlayerManager {

    private Map<Player, PlayerProfile> profiles = new HashMap<>();

    private File usersDir = new File("plugins/" + Manager.getInstance().getName() + "/users");

    public PlayerManager() {
        if (!usersDir.exists() || !usersDir.isDirectory()) {
            usersDir.mkdir();
        }
        Bukkit.getOnlinePlayers().forEach(player -> addPlayerProfile(player));
    }

    public void addPlayerProfile(Player player) {
        PlayerProfile profile = getProfileFromStorage(player.getUniqueId());
        if (profile == null) {
            profile = new PlayerProfile(player.getUniqueId(), player.getName(), "", new ArrayList<>());
            if (profile.shouldSave()) saveProfileToStorage(profile);
        }
        profiles.put(player, profile);
    }

    public void removePlayerProfile(Player player) {
        if (profiles.containsKey(player)) {
            PlayerProfile profile = getPlayerProfile(player);
            if (profile.shouldSave()) saveProfileToStorage(profile);
            profiles.remove(player);
        }
    }

    public PlayerProfile getPlayerProfile(Player player) {
        return profiles.get(player);
    }

    private PlayerProfile getProfileFromStorage(UUID uuid) {
        try {
            File file = new File("plugins/" + Manager.getInstance().getName() + "/users/" + uuid.toString() + ".json");
            if (!file.exists()) return null;
            FileReader reader = new FileReader(file);
            PlayerProfile profile = Manager.getInstance().getGson().fromJson(reader, PlayerProfile.class);
            reader.close();
            return profile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveProfileToStorage(PlayerProfile profile) {
        try (Writer writer = new FileWriter("plugins/" + Manager.getInstance().getName() + "/users/" + profile.getUuid().toString() + ".json")) {
            Manager.getInstance().getGson().toJson(profile, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addTagToOfflineUser(UUID uuid, String tag) {
        PlayerProfile profile = getProfileFromStorage(uuid);
        if (profile == null) {
            profile = new PlayerProfile(uuid, "", "", new ArrayList<>());
        }
        return profile.addTag(tag);
    }

    public boolean removeTagFromOfflineUser(UUID uuid, String tag) {
        PlayerProfile profile = getProfileFromStorage(uuid);
        if (profile != null) return false;
        return profile.removeTag(tag);
    }

    public void saveAll() {
        for (PlayerProfile profile : profiles.values()) {
            if (profile.shouldSave()) saveProfileToStorage(profile);
        }
    }

}
