package fr.mihawka.epidya.storage;

import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.Vector;

public class ClaimComponent {

    final int CAPACITY_CHUNK = 16;

    public Vector<UUID> members;

    // First element in members is the owner
    public ClaimComponent(Player owner) {
        //Reserve memory chunk to speed up future push in vector
        members = new Vector<>(CAPACITY_CHUNK);
        members.add(owner.getUniqueId());
    }

    public void addMember(UUID p) {
        members.add(p);
    }
    public void removeMember(UUID p) {
        members.remove(p);
    }

    public boolean isOwnerOrMember(UUID p) {
        return isOwner(p) || isMember(p);
    }
    public boolean isMember(UUID p) {
        return members.contains(p);
    }
    public boolean isOwner(UUID p) {
        return members.get(0).equals(p);
    }
}
