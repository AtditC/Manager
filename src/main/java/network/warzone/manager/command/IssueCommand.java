package network.warzone.manager.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by MatrixTunnel on 3/22/2018.
 */
public class IssueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;


            sender.spigot().sendMessage(new ComponentBuilder("\nClick ").color(ChatColor.GREEN)
                    .append(new ComponentBuilder("here")
                            .color(ChatColor.AQUA)
                            .underlined(true)
                            .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://warzone.typeform.com/to/lqFP51?token=" + player.getUniqueId().toString().replace("-", ""))).create())
                    .append(new ComponentBuilder(" to fill out a forum and create an issue automatically!\n")
                            .color(ChatColor.GREEN).create())
                    .create());
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players may use this command.");
        return true;
    }

}
