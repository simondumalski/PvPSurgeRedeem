package me.simondumalski.pvpsurgeredeem.commands;

import me.simondumalski.pvpsurgeredeem.Main;
import me.simondumalski.pvpsurgeredeem.managers.MessageManager;
import me.simondumalski.pvpsurgeredeem.utils.Creator;
import me.simondumalski.pvpsurgeredeem.utils.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class RedeemCommand implements TabExecutor {

    private Main plugin;

    public RedeemCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            //Check if the player has permission
            if (!p.hasPermission("redeem.base")) {
                MessageManager.messagePlayer(p, Message.INSUFFICIENT_PERMISSIONS);
                return true;
            }

            //Check if the player is sending any arguments
            if (args.length < 1) {
                MessageManager.messagePlayer(p, Message.INVALID_USAGE);
                return true;
            }

            //  /redeem reload
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadPlugin();
                MessageManager.messagePlayer(p, Message.RELOAD);
                return true;
            }

            //  /redeem list
            if (args[0].equalsIgnoreCase("list")) {

                List<Creator> creators = plugin.getCreatorManager().getCreators();
                List<String> codes = new ArrayList<>();

                //Add the creator codes to a list
                for (Creator creator : creators) {
                    codes.add(creator.getCode());
                }

                MessageManager.listCreators(p, codes);

                return true;
            }

            //  /redeem alist
            if (args[0].equalsIgnoreCase("alist")) {

                if (!p.hasPermission("redeem.admin")) {
                    MessageManager.messagePlayer(p, Message.INSUFFICIENT_PERMISSIONS);
                    return true;
                }

                HashMap<String, List<UUID>> usesMap = plugin.getUsesManager().getUsesMap();
                MessageManager.listCreatorsWithUses(p, usesMap);

                return true;
            }

            //Check if the player has already redeemed a creator
            if (plugin.getUsesManager().hasUsed(p)) {
                MessageManager.messagePlayer(p, Message.ALREADY_REDEEMED);
                return true;
            }

            for (Creator creator : plugin.getCreatorManager().getCreators()) {

                String code = creator.getCode();

                if (!args[0].equalsIgnoreCase(code)) {
                    continue;
                }

                creator.giveRewards(p);
                plugin.getUsesManager().addUse(code, p);
                MessageManager.messagePlayer(p, Message.REDEEMED, new String[]{code});
                return true;

            }

            MessageManager.messagePlayer(p, Message.INVALID_CREATOR);

        } else {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            return Collections.singletonList("list");
        }

        return null;
    }

}
