package fr.mihawka.epidya.events;

import fr.mihawka.epidya.Main;
import fr.mihawka.epidya.utils.ChunkHash;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class OnExplosion implements Listener {

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent event) {
        if (event.getEntity() instanceof TNTPrimed) {
            TNTPrimed primed = (TNTPrimed) event.getEntity();
            Location loc = primed.getLocation();
            int radius = (int) Math.ceil(event.getRadius());

            String hash;
            World w = event.getEntity().getWorld();
            for (int i = -radius; i <= radius; i++)
                for (int j = -radius; j <= radius; j++)
                    for (int k = -radius; k <= radius; k++) {
                        Block b = w.getBlockAt(loc.getBlockX() + i, loc.getBlockY() + j, loc.getBlockZ() + k);
                        hash = ChunkHash.GetHash(b.getChunk());
                        if (Main.CLAIMS.containsChunk(hash)) {
                            if (!Main.CLAIMS.isOwnerOrMember(hash, primed.getSource().getUniqueId())) {
                                event.setCancelled(true);
                                primed.getSource().sendMessage(Main.CONFIG.getMessage("prevent_block_explode"));
                                return;
                            }
                        }
                    }
        }
        else if (event.getEntity() instanceof Creeper) {
            Creeper primed = (Creeper) event.getEntity();
            Location loc = primed.getLocation();
            int radius = (int) Math.ceil(event.getRadius());

            String hash;
            World w = event.getEntity().getWorld();
            for (int i = -radius; i <= radius; i++)
                for (int j = -radius; j <= radius; j++)
                    for (int k = -radius; k <= radius; k++) {
                        Block b = w.getBlockAt(loc.getBlockX() + i, loc.getBlockY() + j, loc.getBlockZ() + k);
                        hash = ChunkHash.GetHash(b.getChunk());
                        if (Main.CLAIMS.containsChunk(hash)) {
                            event.setCancelled(true);
                            primed.damage(20);
                            return;
                        }
                    }
        }
    }
}
