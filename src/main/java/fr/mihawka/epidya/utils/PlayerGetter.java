package fr.mihawka.epidya.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class PlayerGetter {
    public static Player GetPlayerOrOfflinePlayer(String name) {
        Player target = Bukkit.getPlayer(name);
        if (target == null) {
            OfflinePlayer player = Arrays.stream(Bukkit.getOfflinePlayers()).filter(x -> x.getName() == name).findFirst().orElseGet(null);
            if (player == null) return null;
            return player.getPlayer();
        }
        return target;
    }
}
