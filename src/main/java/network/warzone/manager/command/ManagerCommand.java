package network.warzone.manager.command;

import network.warzone.manager.Manager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Jorge on 2/24/2018.
 */
public class ManagerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Insufficient permissions.");
            return true;
        }
        if (args.length < 4) {
            sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <tags/sounds> <add/remove> <player> <tag/sound>");
            return true;
        }

        if (args[0].equalsIgnoreCase("tags") || args[0].equalsIgnoreCase("tag")) {
            if (args[1].equalsIgnoreCase("add")) {
                String tag = args[3];
                if (args[2].length() > 16) {
                    UUID uuid;
                    try {
                         uuid = UUID.fromString(args[2]);
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(ChatColor.RED + "Bad UUID.");
                        return true;
                    }
                    Player player;
                    if ((player = Bukkit.getPlayer(uuid)) != null) {
                        if (Manager.get().getPlayerManager().getPlayerProfile(player).addTag(tag)) {
                            sender.sendMessage("Added tag to " + player.getName());
                            player.sendMessage(ChatColor.GREEN + "You've received the " + ChatColor.GRAY + "[" + ChatColor.translateAlternateColorCodes('&', tag) + ChatColor.GRAY + "]" + ChatColor.GREEN + " tag!");
                            player.sendMessage(ChatColor.GREEN + "You can enable it in /tags");
                        } else {
                            sender.sendMessage(ChatColor.RED + "User already had the specified tag.");
                        }
                        return true;
                    } else {
                        if (Manager.get().getPlayerManager().addTagToOfflineUser(uuid, tag)) {
                            sender.sendMessage("Added tag to " + uuid.toString());
                        } else {
                            sender.sendMessage(ChatColor.RED + "User already had the specified tag.");
                        }
                        return true;
                    }
                } else {
                    Player player;
                    if ((player = Bukkit.getPlayer(args[2])) != null) {
                        if (Manager.get().getPlayerManager().getPlayerProfile(player).addTag(tag)) {
                            sender.sendMessage("Added tag to " + player.getName());
                            player.sendMessage(ChatColor.GREEN + "You've received the " + ChatColor.GRAY + "[" + ChatColor.translateAlternateColorCodes('&', tag) + ChatColor.GRAY + "]" + ChatColor.GREEN + " tag!");
                            player.sendMessage(ChatColor.GREEN + "You can enable it in /tags");
                        } else {
                            sender.sendMessage(ChatColor.RED + "User already had the specified tag.");
                        }
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "Player not online.");
                        return true;
                    }
                }

            } else if (args[1].equalsIgnoreCase("remove")) {
                String tag = args[3];
                if (args[2].length() > 16) {
                    UUID uuid;
                    try {
                        uuid = UUID.fromString(args[2]);
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(ChatColor.RED + "Bad UUID.");
                        return true;
                    }
                    Player player;
                    if ((player = Bukkit.getPlayer(uuid)) != null) {
                        if (Manager.get().getPlayerManager().getPlayerProfile(player).removeTag(tag)) {
                            sender.sendMessage("Removed tag from " + player.getName());
                        } else {
                            sender.sendMessage(ChatColor.RED + "User did not have the specified tag.");
                        }
                        return true;
                    } else {
                        if (Manager.get().getPlayerManager().removeTagFromOfflineUser(uuid, tag)) {
                            sender.sendMessage("Removed tag from " + uuid.toString());
                        } else {
                            sender.sendMessage(ChatColor.RED + "Could not remove tag from offline user.");
                        }
                        return true;
                    }
                } else {
                    Player player;
                    if ((player = Bukkit.getPlayer(args[2])) != null) {
                        if (Manager.get().getPlayerManager().getPlayerProfile(player).removeTag(tag)) {
                            sender.sendMessage("Removed tag from " + player.getName());
                        } else {
                            sender.sendMessage(ChatColor.RED + "User did not have the specified tag.");
                        }
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "Player not online.");
                        return true;
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("sounds") || args[0].equalsIgnoreCase("sound")) {
            if (args[1].equalsIgnoreCase("add")) {
                String soundName = args[3];
                Sound sound;
                try {
                    sound = Sound.valueOf(soundName);
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatColor.RED + "Sound doesn't exist.");
                    return true;
                }
                if (args[2].length() > 16) {
                    UUID uuid;
                    try {
                        uuid = UUID.fromString(args[2]);
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(ChatColor.RED + "Bad UUID.");
                        return true;
                    }
                    Player player;
                    if ((player = Bukkit.getPlayer(uuid)) != null) {
                        if (Manager.get().getPlayerManager().getPlayerProfile(player).addSound(sound)) {
                            sender.sendMessage("Added join sound to " + player.getName());
                            player.sendMessage(ChatColor.GREEN + "You've received the " + sound.name().toLowerCase().replace("_", " ") + " join sound.");
                            player.sendMessage(ChatColor.GREEN + "You can enable it in /joinsounds");
                        } else {
                            sender.sendMessage(ChatColor.RED + "User already had the specified join sound.");
                        }
                        return true;
                    } else {
                        if (Manager.get().getPlayerManager().addSoundToOfflineUser(uuid, sound)) {
                            sender.sendMessage("Added join sound to " + uuid.toString());
                        } else {
                            sender.sendMessage(ChatColor.RED + "User already had the specified join sound.");
                        }
                    }
                } else {
                    Player player;
                    if ((player = Bukkit.getPlayer(args[2])) != null) {
                        if (Manager.get().getPlayerManager().getPlayerProfile(player).addSound(sound)) {
                            sender.sendMessage("Added join sound to " + player.getName());
                            player.sendMessage(ChatColor.GREEN + "You've received the " + sound.name().toLowerCase().replace("_", " ") + " join sound.");
                            player.sendMessage(ChatColor.GREEN + "You can enable it in /joinsounds");
                        } else {
                            sender.sendMessage(ChatColor.RED + "User already had the specified join sound.");
                        }
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "Player not online.");
                        return true;
                    }
                }
            } else if (args[1].equalsIgnoreCase("remove")) {
                String soundName = args[3];
                Sound sound;
                try {
                    sound = Sound.valueOf(soundName);
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatColor.RED + "Bad UUID.");
                    return true;
                }
                if (args[2].length() > 16) {
                    UUID uuid;
                    try {
                        uuid = UUID.fromString(args[2]);
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(ChatColor.RED + "Bad UUID.");
                        return true;
                    }
                    Player player;
                    if ((player = Bukkit.getPlayer(uuid)) != null) {
                        if (Manager.get().getPlayerManager().getPlayerProfile(player).removeSound(sound)) {
                            sender.sendMessage("Removed join sound from " + player.getName());
                        } else {
                            sender.sendMessage(ChatColor.RED + "User didn't have the specified join sound.");
                        }
                        return true;
                    } else {
                        if (Manager.get().getPlayerManager().removeSoundFromOfflineUser(uuid, sound)) {
                            sender.sendMessage("Removed join sound from " + uuid.toString());
                        } else {
                            sender.sendMessage(ChatColor.RED + "User didn't have the specified join sound.");
                        }
                    }
                } else {
                    Player player;
                    if ((player = Bukkit.getPlayer(args[2])) != null) {
                        if (Manager.get().getPlayerManager().getPlayerProfile(player).removeSound(sound)) {
                            sender.sendMessage("Removed join sound from " + player.getName());
                        } else {
                            sender.sendMessage(ChatColor.RED + "User didn't have the specified join sound.");
                        }
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "Player not online.");
                        return true;
                    }
                }
            }
        }
        return true;
    }

}
