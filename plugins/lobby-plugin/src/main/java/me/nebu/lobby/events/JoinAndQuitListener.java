package me.nebu.lobby.events;

import me.nebu.lobby.Lobby;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinAndQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().teleport(Lobby.SPAWN);
        e.joinMessage(Component.empty());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.quitMessage(Component.empty());
    }

}
