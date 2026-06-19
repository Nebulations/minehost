package me.nebu.ruby.servers;

import java.util.Random;

public class LobbyManager {

    private static int[] lobbies;

    public static void configure(int count) {
        int[] lobbies = new int[count];

        for (int i = 0; i < lobbies.length; i++) {
            lobbies[i] = i;
        }

        LobbyManager.lobbies = lobbies;
    }

    public static int getRandomLobby() {
        return new Random().nextInt(0, lobbies.length);
    }

}
