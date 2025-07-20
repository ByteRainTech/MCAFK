package org.plugin.afk;

import org.plugin.afk.command.AfkCommand;
import org.plugin.afk.listener.AfkListener;
import org.plugin.afk.manager.AfkManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AfkPlugin extends JavaPlugin {
    private AfkManager afkManager;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        afkManager = new AfkManager(this);
        getCommand("afk").setExecutor(new AfkCommand(afkManager));
        getServer().getPluginManager().registerEvents(new AfkListener(afkManager), this);
        getLogger().info("AFK 插件已加载！");
    }

    public AfkManager getAfkManager() {
        return afkManager;
    }
}