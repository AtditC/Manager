package network.warzone.manager.listeners;

import network.warzone.manager.Manager;
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
