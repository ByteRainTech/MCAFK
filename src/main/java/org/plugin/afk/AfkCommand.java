package org.plugin.afk.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.afk.manager.AfkManager;

public class AfkCommand implements CommandExecutor {
    private final AfkManager mgr;

    public AfkCommand(AfkManager mgr) {
        this.mgr = mgr;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "只有玩家可自开关挂机！");
                return true;
            }
            toggle((Player) sender, (Player) sender);
            return true;
        }

        if (!sender.hasPermission("afk.admin")) {
            sender.sendMessage(ChatColor.RED + "你没有权限开关别人的挂机状态！");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "玩家 " + args[0] + " 不在线！");
            return true;
        }

        toggle(sender, target);
        return true;
    }

    private void toggle(CommandSender sender, Player target) {
        boolean nowAfk = mgr.toggleAfk(target.getUniqueId());

        if (nowAfk) {
            target.sendMessage(ChatColor.GREEN + "你已被设为挂机状态！");
            sender.sendMessage(ChatColor.GREEN + "已将 " + target.getName() + " 设为挂机状态！");
        } else {
            target.sendMessage(ChatColor.GREEN + "你已退出挂机状态！");
            sender.sendMessage(ChatColor.GREEN + "已取消 " + target.getName() + " 的挂机状态！");
        }
    }
}