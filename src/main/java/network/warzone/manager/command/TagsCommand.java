package network.warzone.manager.command;

import network.warzone.manager.Manager;
import network.warzone.manager.gui.GuiTagManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Jorge on 2/23/2018.
 */
public class TagsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (Manager.getInstance().getPlayerManager().getPlayerProfile((Player) sender).getTags().isEmpty()) {
                sender.sendMessage(ChatColor.RED + "You do not have any tags to enable! Purchase some online at https://warzone.store/");
                return true;
            }
            Player player = (Player) sender;
            player.openInventory(new GuiTagManager(player).getInventory());
        } else {
            sender.sendMessage("Only players may use this command.");
            return true;
        }

        return true;
    }
}
