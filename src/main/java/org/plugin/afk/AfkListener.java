package org.plugin.afk.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.plugin.afk.manager.AfkManager;

public class AfkListener implements Listener {
    private final AfkManager mgr;

    public AfkListener(AfkManager mgr) {
        this.mgr = mgr;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        mgr.handleMove(e);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (mgr.isAfk(p.getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (mgr.isAfk(p.getUniqueId())) {
            e.setFormat(ChatColor.GRAY + "[挂机中] " + e.getFormat());
        }
    }
}