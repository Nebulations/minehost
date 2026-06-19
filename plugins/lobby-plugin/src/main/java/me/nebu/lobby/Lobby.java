package me.nebu.lobby;

import me.nebu.lobby.commands.ReloadCommand;
import me.nebu.lobby.commands.SpawnCommand;
import me.nebu.lobby.events.GriefPrevention;
import me.nebu.lobby.events.JoinAndQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lobby extends JavaPlugin {

    public static Location SPAWN;

    @Override
    public void onEnable() {
        SPAWN = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, 180, 0);
        registerCommands(
                new ReloadCommand(),
                new SpawnCommand()
        );

        registerEvents(
                new GriefPrevention(),
                new JoinAndQuitListener()
        );
    }

    @Override
    public void onDisable() {}

    private void registerCommands(Command ... cmds) {
        CommandMap map = Bukkit.getCommandMap();
        for (Command cmd : cmds) {
            map.getKnownCommands().remove(cmd.getName());
            map.register("", cmd);
        }
    }

    private void registerEvents(Listener ... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }
}
