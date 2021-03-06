package network.warzone.manager.listeners;

import net.md_5.bungee.api.ChatColor;
import network.warzone.manager.Manager;
import network.warzone.manager.model.PlayerProfile;
import network.warzone.tgm.TGM;
import network.warzone.tgm.modules.StatsModule;
import network.warzone.tgm.modules.team.MatchTeam;
import network.warzone.tgm.modules.team.TeamManagerModule;
import network.warzone.tgm.user.PlayerContext;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Jorge on 2/23/2018.
 */
public class ChatListener implements Listener{

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        PlayerContext playerContext = TGM.get().getPlayerManager().getPlayerContext(event.getPlayer());
        MatchTeam matchTeam = TGM.get().getModule(TeamManagerModule.class).getTeam(event.getPlayer());
        String prefix = playerContext.getUserProfile().getPrefix() != null ? ChatColor.translateAlternateColorCodes('&', playerContext.getUserProfile().getPrefix().trim()) + " " : "";
        PlayerProfile profile = Manager.get().getPlayerManager().getPlayerProfile(event.getPlayer());
        String tag = profile != null && !profile.getDisplayTag().isEmpty() ? ChatColor.GRAY + " [" + ChatColor.translateAlternateColorCodes('&', profile.getDisplayTag()) + ChatColor.GRAY + "]" : "";
        event.setFormat((TGM.get().getModule(StatsModule.class).isStatsDisabled() ? "" : playerContext.getLevelString() + " ") +
                prefix + matchTeam.getColor() + event.getPlayer().getName() + tag + ChatColor.WHITE + ": " + event.getMessage().replaceAll("%", "%%"));
    }

}
