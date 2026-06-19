package me.nebu.lobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class Message {

    public static final Component PERMISSION = error("You do not have permission to run this command.");

    public static Component error(String message) {
        return Component.text("[Ruby] ").color(TextColor.color(199, 32, 118)).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(message).color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
    }

    public static Component normal(String message) {
        return Component.text("[Ruby] ").color(TextColor.color(199, 32, 118)).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(message).color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
    }

    public static Component debug(String message) {
        return normal("[DEBUG] " + message);
    }

}
