package network.warzone.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import network.warzone.manager.command.*;
import network.warzone.manager.gui.GuiManager;
import network.warzone.manager.listeners.ChatListener;
import network.warzone.manager.listeners.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

@Getter
public final class Manager extends JavaPlugin {

    private static Manager instance;

    private Gson gson;
    private PlayerManager playerManager;
    private GuiManager guiManager;

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
        getCommand("issue").setExecutor(new IssueCommand());
        getCommand("github").setExecutor(new GithubCommand());
        getCommand("forums").setExecutor(new ForumsCommand());
        getCommand("store").setExecutor(new StoreCommand());
    }

    @Override
    public void onDisable() {
        getPlayerManager().saveAll();
    }

    public static Manager get() {
        return instance;
    }

    private void registerEvents(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener ->
                Bukkit.getPluginManager().registerEvents(listener, this));
    }

    public void createPluginFolder() {
        File file = new File("plugins/" + this.getName() + "");
        if (!file.exists()) file.mkdir();
    }
}
