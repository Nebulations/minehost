package me.nebu.ruby.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class LobbyCommand implements SimpleCommand {

    private final ProxyServer server;

    public LobbyCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player player)) {
            invocation.source().sendMessage(Component.text("You do not have permission to run this command.", NamedTextColor.RED));
            return;
        }

        RegisteredServer server = this.server.getServer("lobby-dev").orElse(null);

        if (server == null) {
            player.sendMessage(Component.text("An error occurred.", NamedTextColor.RED));
            return;
        }

        player.sendMessage(Component.text("Sending you to the lobby"));
        player.createConnectionRequest(server).connect();
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return true;
    }
}
