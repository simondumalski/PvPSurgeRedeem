package me.simondumalski.pvpsurgeredeem.managers;

import me.simondumalski.pvpsurgeredeem.Main;
import me.simondumalski.pvpsurgeredeem.utils.Console;
import me.simondumalski.pvpsurgeredeem.utils.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MessageManager {

    public static void logToConsole(Console consoleMessage) {

        String prefix = Console.PREFIX.getMessage();
        String message = prefix + consoleMessage.getMessage();

        System.out.println(ChatColor.translateAlternateColorCodes('&', message));

    }

    public static void logToConsole(Console consoleMessage, String[] args) {

        String prefix = Console.PREFIX.getMessage();
        String message = consoleMessage.getMessage();

        if (args.length < 1) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', message));
            return;
        }

        for (int i = 0; i < args.length; i++) {

            String argsNumber = "%args" + i + "%";

            if (message.contains(argsNumber)) {
                message = message.replace(argsNumber, args[i]);
            }

        }

        message = prefix + message;
        System.out.println(ChatColor.translateAlternateColorCodes('&', message));

    }

    public static void messagePlayer(Player p, Message configValue) {

        String prefix = Main.plugin.getConfig().getString(Message.PREFIX.getConfigValue());
        String message = Main.plugin.getConfig().getString(configValue.getConfigValue());

        if (message.contains("%prefix%")) {
            message = message.replace("%prefix%", prefix);
        }

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

    }

    public static void messagePlayer(Player p, Message configValue, String[] args) {

        String prefix = Main.plugin.getConfig().getString(Message.PREFIX.getConfigValue());
        String message = Main.plugin.getConfig().getString(configValue.getConfigValue());

        if (message.contains("%prefix%")) {
            message = message.replace("%prefix%", prefix);
        }

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                String argsNumber = "%args" + i + "%";
                if (message.contains(argsNumber)) {
                    message = message.replace(argsNumber, args[i]);
                }
            }
        }

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

    }

    public static void listCreators(Player p, List<String> codes) {

        for (String code : codes) {

            String message = Main.plugin.getConfig().getString(Message.LIST.getConfigValue());

            if (message.contains("%creator%")) {
                message = message.replace("%creator%", code);
            }

            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

        }

    }

    public static void listCreatorsWithUses(Player p, HashMap<String, List<UUID>> usesMap) {

        for (String code : usesMap.keySet()) {

            String message = Main.plugin.getConfig().getString(Message.LIST_ADMIN.getConfigValue());
            int uses = usesMap.get(code).size();

            if (message.contains("%creator%")) {
                message = message.replace("%creator%", code);
            }

            if (message.contains("%uses%")) {
                message = message.replace("%uses%", Integer.toString(uses));
            }

            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

        }

    }

}
