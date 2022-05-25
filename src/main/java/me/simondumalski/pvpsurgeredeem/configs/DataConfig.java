package me.simondumalski.pvpsurgeredeem.configs;

import me.simondumalski.pvpsurgeredeem.Main;
import me.simondumalski.pvpsurgeredeem.managers.MessageManager;
import me.simondumalski.pvpsurgeredeem.utils.Console;
import me.simondumalski.pvpsurgeredeem.utils.Message;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DataConfig {

    private File dataFile;
    private YamlConfiguration dataConfig;

    private Main plugin;

    public DataConfig(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Saves the creator codes and the list of players that have used that code
     */
    public void saveData() {

        //Save the data.yml file
        try {

            ConfigurationSection dataSection = dataConfig.getConfigurationSection("data");
            HashMap<String, List<UUID>> usesMap = plugin.getUsesManager().getUsesMap();

            //Go through the uses map and each creator's list of users to its section
            for (String code : usesMap.keySet()) {

                List<UUID> uuids = usesMap.get(code);
                List<String> convertedUUIDs = new ArrayList<>();

                for (UUID uuid : uuids) {
                    convertedUUIDs.add(uuid.toString());
                }

                dataSection.set(code, convertedUUIDs);
            }

            dataConfig.save(dataFile);
            MessageManager.logToConsole(Console.SAVED_DATA_YML);

        } catch (Exception ex) {
            ex.printStackTrace();
            MessageManager.logToConsole(Console.ERROR_SAVING_DATA_YML);
        }

    }

    /**
     * Loads the map of creator codes and the players that have used that code
     * @return Map of creator codes and players
     */
    public HashMap<String, List<UUID>> loadData() {

        ConfigurationSection dataSection = dataConfig.getConfigurationSection("data");
        HashMap<String, List<UUID>> usesMap = new HashMap<>();

        //Check if the data section is empty
        if (dataSection.getKeys(false).isEmpty()) {
            MessageManager.logToConsole(Console.NO_DATA_TO_LOAD);
            return new HashMap<>();
        }

        //For each creator in the data.yml, get their list of users and add them to the uses map
        for (String code : dataSection.getKeys(false)) {

            List<UUID> convertedUUIDs = new ArrayList<>();

            for (String uuidString : dataSection.getStringList(code)) {
                convertedUUIDs.add(UUID.fromString(uuidString));
            }

            usesMap.put(code, convertedUUIDs);

        }

        MessageManager.logToConsole(Console.LOADED_DATA_YML);

        return usesMap;
    }

    public void initialize() {

        try {

            File file = new File(plugin.getDataFolder(), "data.yml");

            if (file.createNewFile()) {
                MessageManager.logToConsole(Console.NO_DATA_YML_FOUND);
            }

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if (!config.isConfigurationSection("data")) {
                config.createSection("data");
            } else {
                config.getConfigurationSection("data");
            }

            config.save(file);

            this.dataFile = file;
            this.dataConfig = config;

        } catch (Exception ex) {
            ex.printStackTrace();
            MessageManager.logToConsole(Console.ERROR_LOADING_DATA_YML);
        }

    }

}
