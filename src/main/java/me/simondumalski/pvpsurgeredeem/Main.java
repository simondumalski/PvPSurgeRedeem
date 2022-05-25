package me.simondumalski.pvpsurgeredeem;

import me.simondumalski.pvpsurgeredeem.commands.RedeemCommand;
import me.simondumalski.pvpsurgeredeem.configs.DataConfig;
import me.simondumalski.pvpsurgeredeem.managers.CreatorManager;
import me.simondumalski.pvpsurgeredeem.managers.UsesManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;
    private DataConfig dataConfig = new DataConfig(this);
    private CreatorManager creatorManager = new CreatorManager(this);
    private UsesManager usesManager = new UsesManager(this);

    @Override
    public void onEnable() {

        plugin = this;

        //config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Load creators
        creatorManager.loadCreators();

        //Load creator uses
        dataConfig.initialize();
        usesManager.setUsesMap(dataConfig.loadData());

        //Register commands
        getCommand("redeem").setExecutor(new RedeemCommand(this));

    }

    @Override
    public void onDisable() {

        dataConfig.saveData();

    }

    public CreatorManager getCreatorManager() {
        return creatorManager;
    }

    public UsesManager getUsesManager() {
        return usesManager;
    }

    public void reloadPlugin() {

        reloadConfig();
        creatorManager.loadCreators();

    }

}
