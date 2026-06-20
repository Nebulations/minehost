package me.nebu.ruby.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import me.nebu.RubyAPI;
import me.nebu.persistence.ServerInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.net.InetSocketAddress;

public class JoinCommand implements SimpleCommand {

    private final ProxyServer server;

    public JoinCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player player)) {
            invocation.source().sendMessage(Component.text("You do not have permission to run this command.", NamedTextColor.RED));
            return;
        }

        String[] args = invocation.arguments();

        if (args.length == 0) {
            player.sendMessage(Component.text("Usage: /join <server>", NamedTextColor.RED));
            return;
        }

        String serverName = args[0];

        if (serverName.length() < 3 || serverName.length() > 16) {
            player.sendMessage(Component.text("Server name must be between 3 and 16 characters."));
            return;
        }

        RegisteredServer server = this.server.getServer(serverName).orElseGet(() -> {
            ServerInfo info = RubyAPI.getServerFromName(serverName);

            System.out.println("Fetched: " + info);

            if (info == null) return null;

            System.out.print("Connecting to ");
            System.out.println(info.getAddress() + ":" + info.getPort());

            if (info.getAddress() != null && info.getPort() != -1) {
                return this.server.registerServer(new com.velocitypowered.api.proxy.server.ServerInfo(
                        info.getName(),
                        new InetSocketAddress(info.getAddress(), 25565))
                );
            }

            return null;
        });

        if (server == null) {
            player.sendMessage(Component.text("This server does not exist.", NamedTextColor.RED));
            return;
        }

        player.createConnectionRequest(server).connect()
                .thenAccept(result -> {
                    if (!result.isSuccessful()) {
                        player.sendMessage(Component.text("An error occurred.", NamedTextColor.RED));
                    }
                }).exceptionally(throwable -> {
                    player.sendMessage(Component.text("An unexpected error occurred. Please try again later.", NamedTextColor.RED));
                    return null;
                });
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return true;
    }
}
