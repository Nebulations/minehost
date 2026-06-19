package me.nebu.api.servers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;

import me.nebu.api.Database;
import me.nebu.api.states.ServerInfo;
import me.nebu.api.states.ServerState;

public class PowerShellServerInstance implements ServerInstance {

    private final ServerInfo info;
    private Process process;

    public PowerShellServerInstance(ServerInfo info) {
        this.info = info;
    }

    @Override
    public ServerInfo getServerInfo() {
        return info;
    }
    
    @Override
    public void start() {
        String id = info.getId();
        File serverPath = new File("C:/", id);

        if (!serverPath.getParentFile().exists()) {
            serverPath.getParentFile().mkdir();
        }

        if (!serverPath.exists()) {
            serverPath.mkdir();

            // Install a paper jar
            File paperJarPath = new File("C:/", "paper.jar");
            try { Files.copy(paperJarPath.toPath(), new File(serverPath, "paper.jar").toPath());
            } catch (Exception e) { e.printStackTrace(); }

            // Configure server properly
            forceDefaults(serverPath);

        }

        // Hidden process
        ProcessBuilder builder = new ProcessBuilder(
            "java",
            "-Xmx4G",
            "-Xms2G",
            "-jar",
            "paper.jar",
            "--nogui"
        );

        // Visible process (With Command prompt)
        // ProcessBuilder builder = new ProcessBuilder(
        //     "cmd.exe",
        //     "/c",
        //     "start",
        //     "\"Server-" + info.getId() + "\"",
        //     "java",
        //     "-Xmx4G",
        //     "-Xms2G",
        //     "-jar",
        //     "paper.jar",
        //     "--nogui"
        // );

        builder.directory(serverPath);

        try {
            process = builder.start();
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }

        process.onExit().thenRun(() -> {
            ServerManager.stop(info);
        });

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream())
        );

        new Thread(() -> {
            try {
                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println("[SERVER] " + line);

                    if (line.contains(")! For help, type \"help\"")) {
                        info.setState(ServerState.ONLINE);
                        Database.update(info);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void forceDefaults(File serverPath) {
        // Automatically allow eula
        File eulaFile = new File(serverPath, "eula.txt");
        try (FileWriter writer = new FileWriter(eulaFile)) {
            eulaFile.createNewFile();
            writer.write("eula=true");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String yaml = """
                proxies:
                  proxy-protocol: false
                  velocity:
                    enabled: true
                    online-mode: false
                    secret: pasTc7aAEz9S
                """;

        File paperConfig = new File(serverPath, "config/paper-global.yml");
        paperConfig.getParentFile().mkdir();
        try(FileWriter writer = new FileWriter(paperConfig)) {
            writer.write(yaml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File serverProperties = new File(serverPath, "server.properties");
        try(FileWriter writer = new FileWriter(serverProperties)) {
            serverProperties.createNewFile();
            writer.write("online-mode=false");
            writer.write("server-port=" + info.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        sendCommand("stop");
    }

    @Override
    public void sendCommand(String command) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream());

            writer.write(command + '\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
