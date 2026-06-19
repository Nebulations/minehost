package me.nebu.lobby.world;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class LobbyGenerator {

    public static void generate() {
        deleteWorlds();

        WorldCreator creator = new WorldCreator("world");
        creator.generator(new VoidGenerator());

        World world = Bukkit.createWorld(creator);

        if (world == null) {
            throw new IllegalStateException("Failed to generate world for lobby.");
        }

        applyDefaults(world);
    }

    private static void deleteWorlds() {
        Bukkit.getWorlds().forEach(world -> world.getWorldFolder().delete());
    }

    private static void applyDefaults(World world) {
        world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);

        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.DO_INSOMNIA, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DO_ENTITY_DROPS, false);

        world.setGameRule(GameRule.DROWNING_DAMAGE, false);
        world.setGameRule(GameRule.FALL_DAMAGE, false);
        world.setGameRule(GameRule.FIRE_DAMAGE, false);
        world.setGameRule(GameRule.FREEZE_DAMAGE, false);
    }

}
