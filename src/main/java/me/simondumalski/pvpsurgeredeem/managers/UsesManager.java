package me.simondumalski.pvpsurgeredeem.managers;

import me.simondumalski.pvpsurgeredeem.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UsesManager {

    private Main plugin;
    private HashMap<String, List<UUID>> usesMap = new HashMap<>();

    public UsesManager(Main plugin) {
        this.plugin = plugin;
    }

    public void setUsesMap(HashMap<String, List<UUID>> usesMap) {
        this.usesMap = usesMap;
    }

    public void addUse(String code, Player p) {

        //If the code has never been used before
        if (!usesMap.containsKey(code)) {
            List<UUID> temp = new ArrayList<>();
            temp.add(p.getUniqueId());
            usesMap.put(code, temp);
            return;
        }

        usesMap.get(code).add(p.getUniqueId());

    }

    public boolean hasUsed(Player p) {

        for (List<UUID> list : usesMap.values()) {
            if (list.contains(p.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    public HashMap<String, List<UUID>> getUsesMap() {
        return usesMap;
    }

}
