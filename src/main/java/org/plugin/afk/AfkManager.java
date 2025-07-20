package org.plugin.afk.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AfkManager {
    private final Set<UUID> afkPlayers = new HashSet<>();
    private final Scoreboard scoreboard;

    public AfkManager(org.plugin.afk.AfkPlugin plugin) {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam("AFK");
        if (team == null) {
            team = scoreboard.registerNewTeam("AFK");
        }
        team.setPrefix(ChatColor.GRAY + "[挂机中] ");
    }

    public boolean toggleAfk(UUID uuid) {
        if (afkPlayers.contains(uuid)) {
            afkPlayers.remove(uuid);
            setAfkTag(uuid, false);
            return false;
        } else {
            afkPlayers.add(uuid);
            setAfkTag(uuid, true);
            return true;
        }
    }

    public boolean isAfk(UUID uuid) {
        return afkPlayers.contains(uuid);
    }

    private void setAfkTag(UUID uuid, boolean add) {
        Player p = Bukkit.getPlayer(uuid);
        if (p == null) return;
        Team team = scoreboard.getTeam("AFK");
        if (add) {
            team.addEntry(p.getName());
        } else {
            team.removeEntry(p.getName());
        }
    }

    public void handleMove(PlayerMoveEvent e) {
        if (isAfk(e.getPlayer().getUniqueId())) {
            // 如果移动了方块坐标，则取消
            if (e.getFrom().getBlockX() != e.getTo().getBlockX()
                    || e.getFrom().getBlockY() != e.getTo().getBlockY()
                    || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                e.setCancelled(true);
            }
        }
    }
}