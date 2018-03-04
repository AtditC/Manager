package network.warzone.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import network.warzone.manager.command.ManagerCommand;
import network.warzone.manager.command.TagsCommand;
import network.warzone.manager.gui.GuiManager;
import network.warzone.manager.listeners.ChatListener;
import network.warzone.manager.listeners.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Manager extends JavaPlugin {

    @Getter private static Manager instance;

    @Getter private Gson gson;
    @Getter private PlayerManager playerManager;
    @Getter private GuiManager guiManager;

    @Override
    public void onEnable() {
        instance = this;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        createPluginFolder();
        this.playerManager = new PlayerManager();
        this.guiManager = new GuiManager();
        registerEvents(new JoinListener(), new ChatListener(), guiManager);
        getCommand("tags").setExecutor(new TagsCommand());
        getCommand("manager").setExecutor(new ManagerCommand());
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        getPlayerManager().saveAll();
    }

    public void createPluginFolder() {
        File file = new File("plugins/" + this.getName() + "");
        if (!file.exists()) file.mkdir();
    }
}
