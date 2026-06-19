package me.nebu.ruby;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.nebu.RubyAPI;
import me.nebu.ruby.commands.JoinCommand;
import me.nebu.ruby.commands.LobbyCommand;
import me.nebu.ruby.servers.LobbyManager;
import org.slf4j.Logger;

@Plugin(id = "ruby", name = "Ruby", version = "1.0.0")
public class Ruby {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public Ruby(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        LobbyManager.configure(1);

        RubyAPI.configure("http://localhost:8080");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent e) {
        CommandManager commandManager = server.getCommandManager();

        commandManager.register(getCommandMeta("lobby"), new LobbyCommand(server));
        commandManager.register(getCommandMeta("join"), new JoinCommand(server));
    }

    private CommandMeta getCommandMeta(String name) {
        return server.getCommandManager().metaBuilder(name).plugin(this).build();
    }
}
