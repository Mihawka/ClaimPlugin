package fr.mihawka.epidya.storage;

import fr.mihawka.epidya.Main;
import fr.mihawka.epidya.utils.ChunkHash;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ClaimStorageModule {

    //HashMap<Chunk Coord + World hash, ClaimComponent>
    HashMap<String, ClaimComponent> claims;

    public ClaimStorageModule() {
        claims = new HashMap<>();
    }

    public void claimHere(Player p) {
        String hash = ChunkHash.GetHash(p);
        if (containsChunk(hash)) { //Si ce chunk est claim
            p.sendMessage(Main.CONFIG.getMessage("already_claim"));
            return;
        }
        ClaimComponent component = new ClaimComponent(p);
        claims.put(hash, component);
        p.sendMessage(Main.CONFIG.getMessage("success_claim"));
    }
    public void unclaimHere(Player p) {
        String hash = ChunkHash.GetHash(p);
        if (!containsChunk(hash)) { //Si ce chunk est unclaim
            p.sendMessage(Main.CONFIG.getMessage("not_claim"));
            return;
        }
        ClaimComponent component = claims.get(hash);
        if (component.isOwner(p.getUniqueId())) {
            claims.remove(hash);
            p.sendMessage(Main.CONFIG.getMessage("success_unclaim"));
        } else p.sendMessage(Main.CONFIG.getMessage("not_owner"));
    }

    public void addMember(String p, Player sender) {
        String hash = ChunkHash.GetHash(sender);
        if (!containsChunk(hash)) {
            sender.sendMessage(Main.CONFIG.getMessage("not_claim"));
            return;
        }
        ClaimComponent component = claims.get(hash);
        if (!component.isOwner(sender.getUniqueId())) {
            sender.sendMessage(Main.CONFIG.getMessage("not_owner"));
            return;
        }
        Player target = Bukkit.getPlayer(p);
        if (target == null) {
            sender.sendMessage(Main.CONFIG.getMessage("player_not_found").replace("%player%", p));
            return;
        }
        if (component.isMember(target.getUniqueId())) {
            sender.sendMessage(Main.CONFIG.getMessage("already_member").replace("%player%", p));
            return;
        }
        component.addMember(target.getUniqueId());
        sender.sendMessage(Main.CONFIG.getMessage("add_member").replace("%player%", p));
    }
    public void removeMember(String p, Player sender) {
        String hash = ChunkHash.GetHash(sender);
        if (!containsChunk(hash)) {
            sender.sendMessage(Main.CONFIG.getMessage("not_claim"));
            return;
        }
        ClaimComponent component = claims.get(hash);
        if (!component.isOwner(sender.getUniqueId())) {
            sender.sendMessage(Main.CONFIG.getMessage("not_owner"));
            return;
        }
        Player target = Bukkit.getPlayer(p);
        if (target == null) {
            sender.sendMessage(Main.CONFIG.getMessage("player_not_found").replace("%player%", p));
            return;
        }
        if (!component.isMember(target.getUniqueId())) {
            sender.sendMessage(Main.CONFIG.getMessage("not_member").replace("%player%", p));
            return;
        }
        component.removeMember(target.getUniqueId());
        sender.sendMessage(Main.CONFIG.getMessage("remove_member").replace("%player%", p));
    }
    public void listMembers(Player sender) {
        String hash = ChunkHash.GetHash(sender);
        if (!containsChunk(hash)) {
            sender.sendMessage(Main.CONFIG.getMessage("not_claim"));
            return;
        }
        ClaimComponent component = claims.get(hash);
        if (!component.isOwner(sender.getUniqueId())) {
            sender.sendMessage(Main.CONFIG.getMessage("not_owner"));
            return;
        }

        StringBuilder members = new StringBuilder();
        int count = component.members.size();
        for (int i = 1; i < count; i++) {
            members.append(Bukkit.getPlayer(component.members.get(i)).getName());
            if (count -1 != i) {
                members.append(", ");
            }
        }
        String owner = Bukkit.getPlayer(component.members.get(0)).getName();

        List<String> members_list = Main.CONFIG.getMessages("list_members");
        count = members_list.size();
        for (int i = 0; i < count; i++)
            sender.sendMessage(members_list.get(i).replace("%members%", members).replace("%owner%", owner).replace('&', '§'));
    }

    public boolean isOwnerOrMember(String hash, Player p) {
        return claims.get(hash).isOwnerOrMember(p.getUniqueId());
    }
    public boolean isOwnerOrMember(String hash, UUID p) {
        return claims.get(hash).isOwnerOrMember(p);
    }
    public boolean containsChunk(String hash) {
        return claims.containsKey(hash);
    }

}
