package me.nebu.api.servers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.nebu.api.Database;
import me.nebu.api.states.ServerInfo;

@RestController
@RequestMapping("/api/servers")
public class ServerManagement {

    @GetMapping("/list")
    public Map<String, Object> listServers() {
        Map<String, Object> out = new HashMap<>();

        out.put("status", "OK");

        List<Map<String, Object>> formattedInfos = new ArrayList<>();

        List<ServerInfo> servers = Database.getServers();

        if (servers == null) {
            return Map.of(
                    "status", "UNKNOWN_SERVERS"
            );
        }

        servers.forEach(i -> {
            formattedInfos.add(Map.of(
                "id", i.getId(),
                "name", i.getName(),
                "state", i.getState().name()
            ));
        });

        out.put("servers", formattedInfos.toArray());

        return out;
    }

    @GetMapping("/server/{id}")
    public Map<String, Object> fetchServerInfo(@PathVariable("id") String id, @RequestParam(required = false, defaultValue = "false") boolean byName) {
        // Fetch content by ID, not name
        if (id == null || id.isBlank()) {
            return Map.of("status", "INVALID_SERVER");
        }

        ServerInfo info = byName ? Database.getServerFromName(id) : Database.getServerFromId(id);

        if (info == null) {
            return Map.of("status", "UNKNOWN_SERVER");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", info.getId());
        response.put("name", info.getName());
        response.put("state", info.getState().name());
        response.put("address", info.getAddress());
        response.put("port", info.getPort());

        return Map.of(
            "status", "OK",
            "response", response
        );
    }

    @GetMapping("/create/{name}")
    public Map<String, Object> createServer(@PathVariable(required = false) String name) {
        if (name == null || name.isBlank()) {
            return Map.of("status", "INVALID_NAME");
        }

        ServerInfo info = Database.getServerFromName(name);

        if (info != null) {
            return Map.of("status", "INVALID_NAME");
        }

        info = new ServerInfo(UUID.randomUUID().toString().replace("-", ""), name);

        Database.create(info);

        return Map.of(
            "status", "OK",
            "response", Map.of(
                "id", info.getId(),
                "name", info.getName()
            )
        );
    }

    @GetMapping("/start/{id}")
    public Map<String, Object> startServer(@PathVariable(required = false) String id) {
        if (id == null || id.isBlank()) {
            return Map.of("status", "INVALID_SERVER");
        }

        ServerInfo info = Database.getServerFromId(id);

        if (info == null) {
            return Map.of("status", "UNKNOWN_SERVER");
        }

        ServerInfo newInfo = ServerManager.start(info);
        Database.update(newInfo);

        return Map.of(
            "status", "OK",
            "response", Map.of(
                "id", info.getId(),
                "address", info.getAddress(),
                "port", info.getPort()
            )
        );
    }

    @GetMapping("/stop")
    public Map<String, Object> stopServer(@RequestParam(required = false) String id) {
        if (id == null || id.isBlank()) {
            return Map.of("status", "INVALID_SERVER");
        }

        ServerInfo info = Database.getServerFromId(id);

        if (info == null) {
            return Map.of("status", "UNKNOWN_SERVER");
        }

        ServerManager.stop(info);

        return Map.of(
            "status", "OK",
            "response", Map.of(
                "id", info.getId(),
                "state", info.getState().name()
            )
        );
    }

    @GetMapping("/command")
    public Map<String, Object> sendCommand(@RequestParam(required = false) String id, @RequestParam() String command) {
        if (id == null || id.isBlank()) {
            return Map.of("status", "INVALID_SERVER");
        }

        if (command == null || command.isBlank()) {
            return Map.of("status", "INVALID_COMMAND");
        }

        ServerInfo info = Database.getServerFromId(id);

        if (info == null) {
            return Map.of("status", "UNKNOWN_SERVER");
        }

        ServerManager.sendCommand(info, command);

        return Map.of(
            "status", "OK",
            "response", Map.of(
                "id", info.getId(),
                "state", info.getState().name(),
                "command", command
            )
        );
    }

    @GetMapping("/read-console")
    public String readConsole(@RequestParam() String id) {
        return "";
    }

}
