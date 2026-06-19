package me.nebu.lobby.events;

import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class GriefPrevention implements Listener {

    @EventHandler
    public void inventory(InventoryClickEvent e) { e.setCancelled(true); }

    @EventHandler
    public void interact(PlayerInteractEvent e) { e.setCancelled(true); }

    @EventHandler
    public void playerInteract(PlayerInteractAtEntityEvent e) { e.setCancelled(true); }

    @EventHandler
    public void drop(PlayerDropItemEvent e) { e.setCancelled(true); }

    @EventHandler
    public void pickup(PlayerPickItemEvent e) { e.setCancelled(true); }

    @EventHandler
    public void damage(EntityDamageEvent e) { e.setCancelled(true); }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) { e.setCancelled(true); }

    @EventHandler
    public void onBreak(BlockBreakEvent e) { e.setCancelled(true); }

    @EventHandler
    public void hungerDepletion(FoodLevelChangeEvent e) { e.setCancelled(true); }
}
