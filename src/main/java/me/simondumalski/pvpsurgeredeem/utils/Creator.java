package me.simondumalski.pvpsurgeredeem.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Creator {

    private String code;
    private List<String> rewards;

    public Creator(String code, List<String> rewards) {
        this.code = code;
        this.rewards = rewards;
    }

    public String getCode() {
        return code;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public void giveRewards(Player player) {

        ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();

        if (rewards.isEmpty()) {
            System.out.println("rewards is empty");
            return;
        }

        for (String command : rewards) {

            if (command.contains("%player%")) {
                command = command.replace("%player%", player.getName());
            }

            Bukkit.dispatchCommand(consoleSender, command);

        }

    }

}
