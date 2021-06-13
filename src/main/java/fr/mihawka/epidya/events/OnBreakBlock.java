package fr.mihawka.epidya.events;

import fr.mihawka.epidya.Main;
import fr.mihawka.epidya.storage.ClaimComponent;
import fr.mihawka.epidya.utils.ChunkHash;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBreakBlock implements Listener {

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        String hash = ChunkHash.GetHash(event.getBlock().getChunk());
        if (Main.CLAIMS.containsChunk(hash)) {
            if (!Main.CLAIMS.isOwnerOrMember(hash, event.getPlayer())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Main.CONFIG.getMessage("prevent_block_break"));
            }
        }
    }
}
