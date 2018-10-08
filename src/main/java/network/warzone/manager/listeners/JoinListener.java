package network.warzone.manager.listeners;

import network.warzone.manager.Manager;
import network.warzone.manager.model.PlayerProfile;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Jorge on 2/23/2018.
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Manager.get().getPlayerManager().addPlayerProfile(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Manager.get().getPlayerManager().removePlayerProfile(event.getPlayer());
    }

}
