package network.warzone.manager.command;

import network.warzone.manager.Manager;
import network.warzone.manager.gui.GuiSoundManager;
import network.warzone.manager.gui.GuiTagManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinSoundsCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (Manager.get().getPlayerManager().getPlayerProfile((Player) sender).getJoinSounds().isEmpty()) {
        sender.sendMessage(ChatColor.RED + "You do not have any join sounds to enable! Purchase some online at https://tgmwarzone.tebex.io/");
        return true;
      }
      Player player = (Player) sender;
      player.openInventory(new GuiSoundManager(player).getInventory());
    } else {
      sender.sendMessage("Only players may use this command.");
      return true;
    }

    return true;
  }

}
