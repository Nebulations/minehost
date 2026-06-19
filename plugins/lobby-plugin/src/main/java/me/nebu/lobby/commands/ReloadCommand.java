package me.nebu.lobby.commands;

import me.nebu.lobby.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super("reload");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.PERMISSION);
            return false;
        }

        player.performCommand("plugman unload Lobby");
        player.performCommand("plugman load lobby-1.0.0");

        return true;
    }
}
