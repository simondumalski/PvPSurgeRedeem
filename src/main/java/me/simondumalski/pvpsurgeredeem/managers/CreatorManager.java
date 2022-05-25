package me.simondumalski.pvpsurgeredeem.managers;

import me.simondumalski.pvpsurgeredeem.Main;
import me.simondumalski.pvpsurgeredeem.utils.Console;
import me.simondumalski.pvpsurgeredeem.utils.Creator;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class CreatorManager {

    private Main plugin;
    private List<Creator> creators = new ArrayList<>();

    public CreatorManager(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Returns the list of creators
     * @return List of creators
     */
    public List<Creator> getCreators() {
        return creators;
    }

    /**
     * Creates a Creator with the provided code and list of rewards and adds it to the creators list
     * @param code Code to set as the Creator Code
     * @param rewards List of rewards to set as the creator's rewards
     */
    public void addCreator(String code, List<String> rewards) {
        creators.add(new Creator(code, rewards));
    }

    public void loadCreators() {

        creators = new ArrayList<>();

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("creators");

        if (section.getKeys(false).isEmpty()) {
            MessageManager.logToConsole(Console.NO_CREATORS_TO_LOAD);
            return;
        }

        int creators = 0;

        for (String creatorSection : section.getKeys(false)) {

            String code = creatorSection;
            List<String> rewards = section.getStringList(creatorSection + ".commands");

            addCreator(code, rewards);
            creators++;

        }

        MessageManager.logToConsole(Console.SUCCESSFULLY_LOADED_CREATORS, new String[]{Integer.toString(creators)});

    }

    /**
     * Returns the creator that has the corresponding code
     * @param code Code to use as a search term
     * @return Creator that matches the provided code or null if no Creator was found
     */
    public Creator getCreator(String code) {

        for (Creator creator : creators) {
            if (creator.getCode().equalsIgnoreCase(code)) {
                return creator;
            }
        }

        return null;
    }

}
