package fr.mihawka.epidya.utils;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ChunkHash {
    public static String GetHash(Player p) {
        return String.format("%s, %s", p.getLocation().getChunk().toString(), p.getWorld().getName());
    }
    public static String GetHash(Chunk chunk) {
        return String.format("%s, %s", chunk.toString(), chunk.getWorld().getName());
    }
}
