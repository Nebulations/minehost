package me.nebu.lobby.commands;

import me.nebu.lobby.Lobby;
import me.nebu.lobby.Message;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand extends Command {

    public SpawnCommand() {
        super("spawn");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.PERMISSION);
            return false;
        }

        player.teleport(Lobby.SPAWN);
        player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 2);

        return true;
    }
}
