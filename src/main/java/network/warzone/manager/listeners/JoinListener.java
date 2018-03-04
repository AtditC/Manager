package network.warzone.manager.listeners;

import network.warzone.manager.Manager;
import network.warzone.tgm.TGM;
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
        Manager.getInstance().getPlayerManager().addPlayerProfile(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Manager.getInstance().getPlayerManager().removePlayerProfile(event.getPlayer());
    }

}
